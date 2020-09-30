package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 18:11
 */
public class NullCanalFieldConverter extends BaseCanalFieldConverter<Object> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Object> X = new NullCanalFieldConverter();

    private NullCanalFieldConverter() {
        super(JDBCType.NULL, Void.class);
    }

    @Override
    protected Object convertInternal(String source) {
        return null;
    }
}
