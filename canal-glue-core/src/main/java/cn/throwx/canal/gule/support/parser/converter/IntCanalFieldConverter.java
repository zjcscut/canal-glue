package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:29
 */
public class IntCanalFieldConverter extends BaseCanalFieldConverter<Integer> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Integer> X = new IntCanalFieldConverter();

    private IntCanalFieldConverter() {
        super(JDBCType.INTEGER, Integer.class);
    }

    @Override
    protected Integer convertInternal(String source) {
        return Integer.valueOf(source);
    }
}
