package cn.throwx.canal.gule.support.parser;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 0:55
 */
public interface ModelTableMetadataManager {

    ModelTableMetadata load(Class<?> klass);
}
