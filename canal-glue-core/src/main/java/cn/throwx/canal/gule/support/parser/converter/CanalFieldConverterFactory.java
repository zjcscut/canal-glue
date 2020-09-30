package cn.throwx.canal.gule.support.parser.converter;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:54
 */
public interface CanalFieldConverterFactory {

    default void registerConverter(BaseCanalFieldConverter<?> converter) {
        registerConverter(converter, true);
    }

    void registerConverter(BaseCanalFieldConverter<?> converter, boolean replace);

    CanalFieldConvertResult load(CanalFieldConvertInput input);
}
