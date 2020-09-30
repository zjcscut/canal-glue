package cn.throwx.canal.gule.support.processor;

import cn.throwx.canal.gule.common.BinLogEventType;
import cn.throwx.canal.gule.common.OperationType;
import cn.throwx.canal.gule.model.CanalBinLogEvent;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.support.BaseParameterizedTypeReferenceSupport;
import cn.throwx.canal.gule.support.parser.*;
import cn.throwx.canal.gule.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:54
 */
public abstract class BaseCanalBinlogEventProcessor<T> extends BaseParameterizedTypeReferenceSupport<T> {

    private BasePrimaryKeyTupleFunction primaryKeyFunction;

    private BaseCommonEntryFunction<T> commonEntryFunction;

    private ExceptionHandler exceptionHandler;

    private List<BaseParseResultInterceptor<T>> parseResultInterceptors;

    private CanalBinLogEventParser canalBinLogEventParser;

    protected final Logger logger = LoggerFactory.getLogger(getChildKlass());

    private final AtomicBoolean init = new AtomicBoolean(false);

    protected BaseCanalBinlogEventProcessor() {
        super();
    }

    public final void init(CanalBinLogEventParser canalBinLogEventParser,
                           ModelTableMetadataManager modelTableMetadataManager,
                           CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory,
                           ParseResultInterceptorManager parseResultInterceptorManager) {
        AssertUtils.X.notNull(canalBinLogEventParser, "CanalBinLogEventParser");
        AssertUtils.X.notNull(modelTableMetadataManager, "ModelTableMetadataManager");
        AssertUtils.X.notNull(canalBinlogEventProcessorFactory, "CanalBinlogEventProcessorFactory");
        AssertUtils.X.notNull(parseResultInterceptorManager, "ParseResultInterceptorManager");
        // 确定只需要初始化一次
        if (init.compareAndSet(false, true)) {
            this.canalBinLogEventParser = canalBinLogEventParser;
            Class<T> modelKlass = getKlass();
            ModelTableMetadata modelTableMetadata = modelTableMetadataManager.load(modelKlass);
            ModelTable modelTable = modelTableMetadata.getModelTable();
            this.primaryKeyFunction = Optional.ofNullable(primaryKeyFunction()).orElse(BigIntPrimaryKeyTupleFunction.X);
            this.commonEntryFunction = Optional.ofNullable(commonEntryFunction())
                    .orElse(ReflectionBinLogEntryFunction.of(modelKlass, modelTableMetadata));
            this.exceptionHandler = Optional.ofNullable(exceptionHandler()).orElse(ExceptionHandler.NO_OP);
            this.parseResultInterceptors = Optional.ofNullable(parseResultInterceptors())
                    .orElse(parseResultInterceptorManager.getParseResultInterceptors(modelKlass));
            // 自注册
            canalBinlogEventProcessorFactory.register(modelTable, this);
            logger.info("初始化binlog处理器成功,数据库:{},表:{} -> {}", modelTable.database(), modelTable.table(),
                    getChildKlass().getName());
        }
    }

    public final void process(CanalBinLogEvent event) {
        AssertUtils.X.isTrue(init.get(), String.format("Processor %s Not Init!", getChildKlass().getSimpleName()));
        ModelTable modelTable = ModelTable.of(event.getDatabase(), event.getTable());
        try {
            onParse(modelTable);
            List<CanalBinLogResult<T>> resultList
                    = canalBinLogEventParser.parse(event, getKlass(), primaryKeyFunction, commonEntryFunction);
            Optional.ofNullable(resultList).ifPresent(list -> list.forEach(result -> {
                // insert事件
                if (BinLogEventType.INSERT == result.getBinLogEventType() && OperationType.DML == result.getOperationType()) {
                    onBeforeInsertProcess(modelTable, result.getBeforeData(), result.getAfterData());
                    processInsertInternal(result);
                    onAfterInsertProcess(modelTable, result.getBeforeData(), result.getAfterData());
                }
                // update事件
                if (BinLogEventType.UPDATE == result.getBinLogEventType() && OperationType.DML == result.getOperationType()) {
                    onBeforeUpdateProcess(modelTable, result.getBeforeData(), result.getAfterData());
                    processUpdateInternal(result);
                    onAfterUpdateProcess(modelTable, result.getBeforeData(), result.getAfterData());
                }
                // delete事件
                if (BinLogEventType.DELETE == result.getBinLogEventType() && OperationType.DML == result.getOperationType()) {
                    onBeforeDeleteProcess(modelTable, result.getBeforeData(), result.getAfterData());
                    processDeleteInternal(result);
                    onAfterDeleteProcess(modelTable, result.getBeforeData(), result.getAfterData());
                }
                // DDL事件
                if (OperationType.DDL == result.getOperationType()) {
                    onBeforeDDLProcess(modelTable, result.getBeforeData(), result.getAfterData(), result.getSql());
                    processDDLInternal(result);
                    onAfterDDLProcess(modelTable, result.getBeforeData(), result.getAfterData(), result.getSql());
                }
            }));
            onParseFinish(modelTable);
        } catch (Exception e) {
            exceptionHandler.onError(event, e);
        } finally {
            onParseCompletion(modelTable);
        }
    }

    private void onParse(ModelTable modelTable) {
        Optional.ofNullable(parseResultInterceptors).ifPresent(items -> items.forEach(item -> item.onParse(modelTable)));
    }

    private void onParseFinish(ModelTable modelTable) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onParseFinish(modelTable)));
    }

    private void onBeforeInsertProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onBeforeInsertProcess(modelTable, before, after)));
    }

    private void onAfterInsertProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onAfterInsertProcess(modelTable, before, after)));
    }

    private void onBeforeUpdateProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onBeforeUpdateProcess(modelTable, before, after)));
    }

    private void onAfterUpdateProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onAfterUpdateProcess(modelTable, before, after)));
    }

    private void onBeforeDeleteProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onBeforeDeleteProcess(modelTable, before, after)));
    }

    private void onAfterDeleteProcess(ModelTable modelTable, T before, T after) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onAfterDeleteProcess(modelTable, before, after)));
    }

    private void onBeforeDDLProcess(ModelTable modelTable, T before, T after, String sql) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onBeforeDDLProcess(modelTable, before, after, sql)));
    }

    private void onAfterDDLProcess(ModelTable modelTable, T before, T after, String sql) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onAfterDDLProcess(modelTable, before, after, sql)));
    }

    private void onParseCompletion(ModelTable modelTable) {
        Optional.ofNullable(parseResultInterceptors)
                .ifPresent(items -> items.forEach(item -> item.onParseCompletion(modelTable)));
    }

    protected BasePrimaryKeyTupleFunction primaryKeyFunction() {
        return null;
    }

    protected BaseCommonEntryFunction<T> commonEntryFunction() {
        return null;
    }

    protected ExceptionHandler exceptionHandler() {
        return null;
    }

    protected List<BaseParseResultInterceptor<T>> parseResultInterceptors() {
        return null;
    }

    /**
     * 内部处理insert事件
     *
     * @param result binlog实体
     */
    protected void processInsertInternal(CanalBinLogResult<T> result) {
    }

    /**
     * 内部处理update事件
     *
     * @param result binlog实体
     */
    protected void processUpdateInternal(CanalBinLogResult<T> result) {
    }


    /**
     * 内部处理delete事件
     *
     * @param result binlog实体
     */
    protected void processDeleteInternal(CanalBinLogResult<T> result) {
    }

    /**
     * 内部处理DDL事件
     *
     * @param result binlog实体
     */
    protected void processDDLInternal(CanalBinLogResult<T> result) {
    }
}
