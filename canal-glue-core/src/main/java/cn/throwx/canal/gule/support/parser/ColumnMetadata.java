package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.support.parser.converter.BaseCanalFieldConverter;
import lombok.Data;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 0:54
 */
@Data
public class ColumnMetadata {

    private String columnName;

    private BaseCanalFieldConverter<?> converter;
}
