package cn.throwx.canal.gule.support.parser.converter;

import lombok.Builder;
import lombok.Getter;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/22 0:55
 */
@Builder
@Getter
public class CanalFieldConvertResult {

    private final BaseCanalFieldConverter<?> converter;
}
