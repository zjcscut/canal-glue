package cn.throwx.canal.gule.support.adapter;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/20 17:27
 */
public enum SourceAdapterFacade {

    /**
     * 单例
     */
    X;

    private static final SourceAdapter<String, String> I_S_A = RawStringSourceAdapter.of();

    @SuppressWarnings("unchecked")
    public <T> T adapt(Class<T> klass, String source) {
        if (klass.isAssignableFrom(String.class)) {
            return (T) I_S_A.adapt(source);
        }
        return FastJsonSourceAdapter.of(klass).adapt(source);
    }
}
