package no.sivertsensoftware.userregistration.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * Need to cache the HttpServletRequest payload, because we cant read it more than once.
 *
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingHttpServletRequest contentCachingRequestWrapper = new ContentCachingHttpServletRequest(request);
        filterChain.doFilter(contentCachingRequestWrapper, response);
    }

    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    //     ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
    //     filterChain.doFilter(contentCachingRequestWrapper, response);
    // }
}
