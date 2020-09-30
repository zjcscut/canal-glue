package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 18:11
 */
public class BigIntCanalFieldConverter extends BaseCanalFieldConverter<Long> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Long> X = new BigIntCanalFieldConverter();

    private BigIntCanalFieldConverter() {
        super(JDBCType.BIGINT, Long.class);
    }

    @Override
    protected Long convertInternal(String source) {
        return Long.valueOf(source);
    }
}
