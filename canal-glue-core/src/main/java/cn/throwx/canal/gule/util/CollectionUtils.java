package cn.throwx.canal.gule.util;

import java.util.Collection;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/28 23:06
 */
public enum CollectionUtils {

    /**
     * 单例
     */
    X;

    public boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
