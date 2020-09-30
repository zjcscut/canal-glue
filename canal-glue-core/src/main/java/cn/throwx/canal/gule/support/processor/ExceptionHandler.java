package cn.throwx.canal.gule.support.processor;

import cn.throwx.canal.gule.model.CanalBinLogEvent;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/23 23:13
 */
@FunctionalInterface
public interface ExceptionHandler {

    void onError(CanalBinLogEvent event, Throwable throwable);

    ExceptionHandler NO_OP = (event, throwable) -> {
    };
}
