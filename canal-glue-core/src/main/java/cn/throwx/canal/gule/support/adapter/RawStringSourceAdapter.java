package cn.throwx.canal.gule.support.adapter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 17:31
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
class RawStringSourceAdapter implements SourceAdapter<String, String> {

    @Override
    public String adapt(String source) {
        return source;
    }
}
