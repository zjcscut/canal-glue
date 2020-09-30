package cn.throwx.canal.gule.support.parser.converter;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 18:00
 */
public interface BinLogFieldConverter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);
}
