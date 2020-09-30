package cn.throwx.canal.gule.support.parser;

import java.util.Map;
import java.util.function.Function;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:51
 */
public abstract class BaseCommonEntryFunction<T> implements Function<Map<String, String>, T> {

    @Override
    public T apply(Map<String, String> entry) {
        throw new UnsupportedOperationException();
    }
}
