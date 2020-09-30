package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.support.parser.converter.BigIntCanalFieldConverter;

import java.util.Map;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:35
 */
public class BigIntPrimaryKeyTupleFunction extends BasePrimaryKeyTupleFunction {

    /**
     * 单例
     */
    public static final BasePrimaryKeyTupleFunction X = new BigIntPrimaryKeyTupleFunction();

    private BigIntPrimaryKeyTupleFunction() {
    }

    @Override
    public Long apply(Map<String, String> before, Map<String, String> after, String primaryKey) {
        String temp;
        if (null != after && null != (temp = after.get(primaryKey))) {
            return BigIntCanalFieldConverter.X.convert(temp);
        }
        return null;
    }
}
