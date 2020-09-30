package cn.throwx.canal.gule.util;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/28 22:32
 */
public enum StringUtils {

    /**
     * 单例
     */
    X;

    public boolean isBlank(CharSequence content) {
        int len;
        if (content != null && (len = content.length()) != 0) {
            for (int i = 0; i < len; ++i) {
                if (!Character.isWhitespace(content.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNotBlank(CharSequence content) {
        return !isBlank(content);
    }

    public boolean isEmpty(CharSequence content) {
        return null == content || 0 == content.length();
    }

    public boolean isNotEmpty(CharSequence content) {
        return !isEmpty(content);
    }
}
