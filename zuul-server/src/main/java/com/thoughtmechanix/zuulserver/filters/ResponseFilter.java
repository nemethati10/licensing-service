package com.thoughtmechanix.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.sleuth.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Inject the correlation ID back into the HTTP response headers
 * being passed back to the caller of the service.
 */
@Component
public class ResponseFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private Tracer tracer;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().addHeader("tmx-correlation-id", tracer.getCurrentSpan().traceIdString());

        return null;
    }
}
