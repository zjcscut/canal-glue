package cn.throwx.canal.gule.support.parser;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/26 22:39
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class InMemoryParseResultInterceptorManager implements ParseResultInterceptorManager {

    private final ConcurrentMap<Class<?>, List<BaseParseResultInterceptor<?>>> cache = new ConcurrentHashMap<>(16);

    private final ModelTableMetadataManager modelTableMetadataManager;

    @Override
    public <T> void registerParseResultInterceptor(BaseParseResultInterceptor<T> parseResultInterceptor) {
        synchronized (cache) {
            Class<T> klass = parseResultInterceptor.getKlass();
            ModelTableMetadata modelTableMetadata = modelTableMetadataManager.load(klass);
            Optional.ofNullable(modelTableMetadata).ifPresent(ignore -> {
                cache.putIfAbsent(parseResultInterceptor.getKlass(), new LinkedList<>());
                cache.get(klass).add(parseResultInterceptor);
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<BaseParseResultInterceptor<T>> getParseResultInterceptors(Class<T> klass) {
        return (List<BaseParseResultInterceptor<T>>) (List<?>) cache.getOrDefault(klass, Collections.emptyList());
    }
}
