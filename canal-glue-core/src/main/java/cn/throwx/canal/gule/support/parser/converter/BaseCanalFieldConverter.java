package cn.throwx.canal.gule.support.parser.converter;


import cn.throwx.canal.gule.util.StringUtils;

import java.sql.SQLType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 18:08
 */
public abstract class BaseCanalFieldConverter<T> implements BinLogFieldConverter<String, T> {

    private final SQLType sqlType;
    private final Class<?> klass;

    protected BaseCanalFieldConverter(SQLType sqlType, Class<?> klass) {
        this.sqlType = sqlType;
        this.klass = klass;
    }

    @Override
    public T convert(String source) {
        if (StringUtils.X.isEmpty(source)) {
            return null;
        }
        return convertInternal(source);
    }

    /**
     * 内部转换方法
     *
     * @param source 源字符串
     * @return T
     */
    protected abstract T convertInternal(String source);

    /**
     * 返回SQL类型
     *
     * @return SQLType
     */
    public SQLType sqlType() {
        return sqlType;
    }

    /**
     * 返回类型
     *
     * @return Class<?>
     */
    public Class<?> typeKlass() {
        return klass;
    }
}
