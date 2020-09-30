package cn.throwx.canal.gule.support.parser.converter;

import cn.throwx.canal.gule.support.parser.converter.BaseCanalFieldConverter;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.sql.SQLType;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:55
 */
@SuppressWarnings("rawtypes")
@Builder
@Data
public class CanalFieldConvertInput {

    private Class<?> fieldKlass;
    private Class<? extends BaseCanalFieldConverter> converterKlass;
    private SQLType sqlType;

    @Tolerate
    public CanalFieldConvertInput() {

    }
}
