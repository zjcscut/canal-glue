package cn.throwx.canal.gule.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/28 22:56
 */
public enum ReflectionUtils {

    /**
     * 单例
     */
    X;

    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    private static final Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new ConcurrentHashMap<>(32);

    private Field[] getDeclaredFields(Class<?> clazz) {
        AssertUtils.X.notNull(clazz, "Class must not be null");
        Field[] result = DECLARED_FIELDS_CACHE.get(clazz);
        if (Objects.isNull(result)) {
            try {
                result = clazz.getDeclaredFields();
                DECLARED_FIELDS_CACHE.put(clazz, result.length == 0 ? EMPTY_FIELD_ARRAY : result);
            } catch (Throwable e) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", e);
            }
        }
        return result;
    }

    public void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fieldCallback) {
        doWithFields(clazz, fieldCallback, null);
    }

    public void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fieldCallback, ReflectionUtils.FieldFilter fieldFilter) {
        Class<?> targetClass = clazz;
        do {
            Field[] fields = getDeclaredFields(targetClass);
            int len = fields.length;
            for (int index = 0; index < len; ++index) {
                Field field = fields[index];
                if (null == fieldFilter || fieldFilter.matches(field)) {
                    try {
                        fieldCallback.doWith(field);
                    } catch (IllegalAccessException var10) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + var10);
                    }
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }

    @SuppressWarnings("deprecation")
    public void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    @FunctionalInterface
    public interface FieldCallback {

        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface FieldFilter {

        boolean matches(Field field);
    }
}
