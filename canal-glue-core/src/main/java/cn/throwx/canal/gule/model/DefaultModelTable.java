package cn.throwx.canal.gule.model;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 12:29
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
public class DefaultModelTable implements ModelTable {

    private final String database;
    private final String table;

    @Override
    public String database() {
        return database;
    }

    @Override
    public String table() {
        return table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultModelTable that = (DefaultModelTable) o;
        return Objects.equals(database, that.database) &&
                Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(database, table);
    }

    @Override
    public String toString() {
        return database() + "." + table();
    }
}
