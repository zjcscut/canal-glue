package cn.throwx.canal.gule.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1
 * @description binlog事件类型
 * @since 2020/9/20 17:19
 */
@RequiredArgsConstructor
@Getter
public enum BinLogEventType {

    QUERY("QUERY", "查询"),

    INSERT("INSERT", "新增"),

    UPDATE("UPDATE", "更新"),

    DELETE("DELETE", "删除"),

    ALTER("ALTER", "列修改操作"),

    CREATE("CREATE", "表创建"),

    UNKNOWN("UNKNOWN", "未知"),

    ;

    private final String type;
    private final String description;

    public static BinLogEventType fromType(String type) {
        for (BinLogEventType binLogType : BinLogEventType.values()) {
            if (binLogType.getType().equals(type)) {
                return binLogType;
            }
        }
        return BinLogEventType.UNKNOWN;
    }
}
