package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:23
 */
public class VarcharCanalFieldConverter extends BaseCanalFieldConverter<String> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<String> X = new VarcharCanalFieldConverter();

    private VarcharCanalFieldConverter() {
        super(JDBCType.VARCHAR, String.class);
    }

    @Override
    protected String convertInternal(String source) {
        return source;
    }
}
