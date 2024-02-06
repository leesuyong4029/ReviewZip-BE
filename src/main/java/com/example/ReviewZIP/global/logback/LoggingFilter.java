package com.example.ReviewZIP.global.logback;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    protected static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("traceId", UUID.randomUUID().toString());
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(new RequestWrapper(request), new ResponseWrapper(response), filterChain);
        }
        MDC.clear();
    }

    protected void doFilterWrapped(RequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logRequest(request);
            filterChain.doFilter(request, response);
        } finally {
            logResponse(response);
            response.copyBodyToResponse();
        }
    }

    private static void logRequest(RequestWrapper request) throws IOException {
        log.info("{} [{}] {}",
                LogColor.GREEN + "[Request]" + LogColor.RESET,
                LogColor.BLUE + request.getMethod() + LogColor.RESET,
                LogColor.BLUE + request.getRequestURI() + LogColor.RESET
        );
    }

    private static void logResponse(ContentCachingResponseWrapper response) throws IOException {
        log.info("{}", LogColor.PURPLE + "[Response]" + LogColor.RESET);
    }

}
