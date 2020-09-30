package cn.throwx.canal.gule.util;


import java.util.Objects;

/**
 * @author throwable
 * @version v1
 * @description 断言工具
 * @since 2020/9/28 0:43
 */
public enum AssertUtils {

    /**
     * 单例
     */
    X;

    public void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
