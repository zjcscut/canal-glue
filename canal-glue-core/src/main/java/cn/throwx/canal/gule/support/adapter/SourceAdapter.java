package cn.throwx.canal.gule.support.adapter;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 17:26
 */
public interface SourceAdapter<SOURCE, SINK> {

    SINK adapt(SOURCE source);
}
