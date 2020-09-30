package cn.throwx.canal.gule.support.parser;

import cn.throwx.canal.gule.annotation.CanalField;
import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.NamingPolicy;
import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.support.parser.converter.BaseCanalFieldConverter;
import cn.throwx.canal.gule.support.parser.converter.CanalFieldConvertInput;
import cn.throwx.canal.gule.support.parser.converter.CanalFieldConvertResult;
import cn.throwx.canal.gule.support.parser.converter.CanalFieldConverterFactory;
import cn.throwx.canal.gule.util.AssertUtils;
import cn.throwx.canal.gule.util.ReflectionUtils;
import cn.throwx.canal.gule.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 0:56
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class InMemoryModelTableMetadataManager implements ModelTableMetadataManager {

    private final ConcurrentMap<Class<?>, ModelTableMetadata> cache = new ConcurrentHashMap<>(16);

    private final CanalFieldConverterFactory canalFieldConverterFactory;

    @Override
    public ModelTableMetadata load(Class<?> klass) {
        return cache.computeIfAbsent(klass, clazz -> {
            AssertUtils.X.isTrue(klass.isAnnotationPresent(CanalModel.class), String.format("[%s]没有使用@CanalModel注解", klass.getName()));
            CanalModel canalModel = klass.getAnnotation(CanalModel.class);
            NamingPolicy namingPolicy = canalModel.fieldNamingPolicy();
            ModelTableMetadata metadata = new ModelTableMetadata();
            metadata.setModelTable(ModelTable.of(canalModel.database(), canalModel.table()));
            Map<String, ColumnMetadata> fieldColumnMapping = new HashMap<>(8);
            ReflectionUtils.X.doWithFields(klass, field -> {
                CanalField canalField;
                JDBCType sqlType = null;
                Class<? extends BaseCanalFieldConverter<?>> converterKlass = null;
                String columnName = null;
                if (field.isAnnotationPresent(CanalField.class)) {
                    canalField = field.getAnnotation(CanalField.class);
                    sqlType = canalField.sqlType();
                    converterKlass = canalField.converterKlass();
                    if (StringUtils.X.isNotEmpty(canalField.columnName())) {
                        columnName = canalField.columnName();
                    }
                }
                String fieldName = field.getName();
                if (null == columnName) {
                    columnName = namingPolicy.convert(fieldName);
                }
                CanalFieldConvertInput input = CanalFieldConvertInput.builder()
                        .fieldKlass(field.getType())
                        .sqlType(sqlType)
                        .converterKlass(converterKlass)
                        .build();
                CanalFieldConvertResult result = canalFieldConverterFactory.load(input);
                ColumnMetadata columnMetadata = new ColumnMetadata();
                columnMetadata.setColumnName(columnName);
                columnMetadata.setConverter(result.getConverter());
                fieldColumnMapping.put(fieldName, columnMetadata);
            });
            metadata.setFieldColumnMapping(fieldColumnMapping);
            return metadata;
        });
    }
}
