package cn.throwx.canal.gule.support.parser.converter;

import java.math.BigDecimal;
import java.sql.JDBCType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:25
 */
public class DecimalCanalFieldConverter extends BaseCanalFieldConverter<BigDecimal> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<BigDecimal> X = new DecimalCanalFieldConverter();

    private DecimalCanalFieldConverter() {
        super(JDBCType.DECIMAL, BigDecimal.class);
    }

    @Override
    protected BigDecimal convertInternal(String source) {
        return new BigDecimal(source);
    }
}
