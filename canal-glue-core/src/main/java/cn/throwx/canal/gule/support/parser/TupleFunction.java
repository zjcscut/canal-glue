package cn.throwx.canal.gule.support.parser;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:32
 */
@FunctionalInterface
public interface TupleFunction<BEFORE, AFTER, KEY, R> {

    R apply(BEFORE before, AFTER after, KEY key);
}
