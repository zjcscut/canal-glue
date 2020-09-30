package cn.throwx.canal.gule.support.parser;

import java.util.List;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/26 22:35
 */
public interface ParseResultInterceptorManager {

    <T> void registerParseResultInterceptor(BaseParseResultInterceptor<T> parseResultInterceptor);

    <T> List<BaseParseResultInterceptor<T>> getParseResultInterceptors(Class<T> klass);
}
