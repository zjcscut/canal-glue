package cn.throwx.canal.gule.support.parser;

import java.util.Map;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:48
 */
public abstract class BasePrimaryKeyTupleFunction implements TupleFunction<Map<String, String>, Map<String, String>, String, Long> {

    @Override
    public Long apply(Map<String, String> before, Map<String, String> after, String primaryKey) {
        throw new UnsupportedOperationException();
    }
}
