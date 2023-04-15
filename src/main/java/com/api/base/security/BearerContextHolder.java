package com.api.base.security;

/**
 * @author BacDV
 *
 */
public abstract class BearerContextHolder {

    private BearerContextHolder() {
    }

    private static final ThreadLocal<BearerContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static BearerContext getContext() {
        BearerContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public static void setContext(BearerContext context) {
        if (context != null) {
            contextHolder.set(context);
        }
    }

    public static BearerContext createEmptyContext() {
        return new BearerContext();
    }
}
