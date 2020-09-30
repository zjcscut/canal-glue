package cn.throwx.canal.gule.support.processor;

import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.util.AssertUtils;
import cn.throwx.canal.gule.util.CollectionUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/24 1:00
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class InMemoryCanalBinlogEventProcessorFactory implements CanalBinlogEventProcessorFactory {

    private final ConcurrentMap<ModelTable, List<BaseCanalBinlogEventProcessor<?>>> cache = new ConcurrentHashMap<>(16);

    @Override
    public void register(ModelTable modelTable, BaseCanalBinlogEventProcessor<?> processor) {
        synchronized (cache) {
            cache.putIfAbsent(modelTable, new LinkedList<>());
            cache.get(modelTable).add(processor);
        }
    }

    @Override
    public List<BaseCanalBinlogEventProcessor<?>> get(ModelTable modelTable) {
        List<BaseCanalBinlogEventProcessor<?>> processors = cache.get(modelTable);
        AssertUtils.X.isTrue(CollectionUtils.X.isNotEmpty(processors), String.format("Processor Not Found For %s", modelTable));
        return processors;
    }
}
