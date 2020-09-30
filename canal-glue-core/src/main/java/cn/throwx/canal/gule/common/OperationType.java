package cn.throwx.canal.gule.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 17:20
 */
@RequiredArgsConstructor
@Getter
public enum OperationType {

    /**
     * DML
     */
    DML("dml", "DML语句"),

    /**
     * DDL
     */
    DDL("ddl", "DDL语句"),
    ;

    private final String type;
    private final String description;
}
