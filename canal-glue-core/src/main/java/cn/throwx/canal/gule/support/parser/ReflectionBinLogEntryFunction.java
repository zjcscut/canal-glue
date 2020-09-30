package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.util.AssertUtils;
import cn.throwx.canal.gule.util.ClassUtils;
import cn.throwx.canal.gule.util.ReflectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:38
 */
@Slf4j
@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class ReflectionBinLogEntryFunction<T> extends BaseCommonEntryFunction<T> {

    private final Class<T> klass;
    private final ModelTableMetadata modelTableMetadata;

    @Override
    public T apply(Map<String, String> data) {
        Constructor<T> constructor = ClassUtils.X.getConstructorIfAvailable(klass);
        if (Objects.nonNull(constructor)) {
            try {
                T instance = constructor.newInstance();
                Map<String, ColumnMetadata> fieldColumnMapping = modelTableMetadata.getFieldColumnMapping();
                ReflectionUtils.X.doWithFields(klass, field -> {
                    String fieldName = field.getName();
                    ColumnMetadata columnMetadata = fieldColumnMapping.get(fieldName);
                    // 理论上这里不会为NULL,谨慎起见避免低端NPE做个断言
                    AssertUtils.X.notNull(columnMetadata, String.format("[%s.%s]属性获取列属性元数据失败", klass.getSimpleName(), fieldName));
                    String value = data.get(columnMetadata.getColumnName());
                    if (null != value) {
                        Object convertValue = columnMetadata.getConverter().convert(value);
                        // 抑制修饰符和值设置
                        ReflectionUtils.X.makeAccessible(field);
                        field.set(instance, convertValue);
                    }
                });
                return instance;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        throw new IllegalArgumentException(String.format("基于类型[%s]实例化和反射赋值失败,请确定是否提供了默认的构造函数", klass.getName()));
    }
}
