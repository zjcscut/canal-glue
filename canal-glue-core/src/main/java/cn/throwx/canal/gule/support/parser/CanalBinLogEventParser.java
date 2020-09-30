package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.model.CanalBinLogEvent;
import cn.throwx.canal.gule.model.CanalBinLogResult;

import java.util.List;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:29
 */
public interface CanalBinLogEventParser {

    /**
     * 解析binlog事件
     *
     * @param event               事件
     * @param klass               目标类型
     * @param primaryKeyFunction  主键映射方法
     * @param commonEntryFunction 其他属性映射方法
     * @return CanalBinLogResult
     */
    <T> List<CanalBinLogResult<T>> parse(CanalBinLogEvent event,
                                         Class<T> klass,
                                         BasePrimaryKeyTupleFunction primaryKeyFunction,
                                         BaseCommonEntryFunction<T> commonEntryFunction);
}
