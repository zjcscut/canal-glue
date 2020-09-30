package cn.throwx.canal.gule.support.adapter;

import cn.throwx.canal.gule.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 17:28
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
class FastJsonSourceAdapter<T> implements SourceAdapter<String, T> {

    private final Class<T> klass;

    @Override
    public T adapt(String source) {
        if (StringUtils.X.isEmpty(source)) {
            return null;
        }
        return JSON.parseObject(source, klass);
    }
}
