package cn.throwx.canal.gule.support.parser.converter;

import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:31
 */
public class TinyIntCanalFieldConverter extends BaseCanalFieldConverter<Integer> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Integer> X = new TinyIntCanalFieldConverter();

    private TinyIntCanalFieldConverter() {
        super(JDBCType.TINYINT, Integer.class);
    }

    @Override
    protected Integer convertInternal(String source) {
        return IntCanalFieldConverter.X.convert(source);
    }
}
