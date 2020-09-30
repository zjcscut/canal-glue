package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:34
 */
public class SqlDateCanalFieldConverter1 extends BaseCanalFieldConverter<java.sql.Date> {

    public static final BaseCanalFieldConverter<java.sql.Date> X = new SqlDateCanalFieldConverter1();

    private SqlDateCanalFieldConverter1() {
        super(JDBCType.DATE, java.sql.Date.class);
    }

    @Override
    protected java.sql.Date convertInternal(String source) {
        return java.sql.Date.valueOf(SqlDateCanalFieldConverter0.X.convert(source));
    }
}
