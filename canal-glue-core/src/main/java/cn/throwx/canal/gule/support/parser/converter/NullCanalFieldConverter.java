package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 18:11
 */
public class NullCanalFieldConverter extends BaseCanalFieldConverter<Void> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Void> X = new NullCanalFieldConverter();

    private NullCanalFieldConverter() {
        super(JDBCType.NULL, Void.class);
    }

    @Override
    protected Void convertInternal(String source) {
        return null;
    }
}
