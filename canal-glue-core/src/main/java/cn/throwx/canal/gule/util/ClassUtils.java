package cn.throwx.canal.gule.util;

import java.lang.reflect.Constructor;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/28 23:04
 */
public enum ClassUtils {

    /**
     * 单例
     */
    X;

    public <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
        AssertUtils.X.notNull(clazz, "Class must not be null");
        try {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException var3) {
            return null;
        }
    }
}
