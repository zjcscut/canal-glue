package cn.throwx.canal.gule.model;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 12:29
 */
public interface ModelTable {

    String database();

    String table();

    static ModelTable of(String database, String table) {
        return DefaultModelTable.of(database, table);
    }
}
