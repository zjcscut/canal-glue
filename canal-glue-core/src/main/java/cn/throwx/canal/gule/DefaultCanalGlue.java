package cn.throwx.canal.gule;

import cn.throwx.canal.gule.model.CanalBinLogEvent;
import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.support.adapter.SourceAdapterFacade;
import cn.throwx.canal.gule.support.processor.CanalBinlogEventProcessorFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 0:54
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class DefaultCanalGlue implements CanalGlue {

    private final CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory;

    @Override
    public void process(String content) {
        CanalBinLogEvent event = SourceAdapterFacade.X.adapt(CanalBinLogEvent.class, content);
        ModelTable modelTable = ModelTable.of(event.getDatabase(), event.getTable());
        canalBinlogEventProcessorFactory.get(modelTable).forEach(processor -> processor.process(event));
    }
}
