package cn.throwx.canal.gule.support.processor;

import cn.throwx.canal.gule.model.ModelTable;

import java.util.List;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 0:59
 */
public interface CanalBinlogEventProcessorFactory {

    void register(ModelTable modelTable, BaseCanalBinlogEventProcessor<?> processor);

    List<BaseCanalBinlogEventProcessor<?>> get(ModelTable modelTable);
}
