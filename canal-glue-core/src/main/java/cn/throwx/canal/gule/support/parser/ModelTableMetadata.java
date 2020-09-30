package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.model.ModelTable;
import lombok.Data;

import java.util.Map;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 0:53
 */
@Data
public class ModelTableMetadata {

    private ModelTable modelTable;

    /**
     * fieldName -> ColumnMetadata
     */
    private Map<String, ColumnMetadata> fieldColumnMapping;
}
