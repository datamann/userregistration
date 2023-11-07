package no.sivertsensoftware.userregistration.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import no.sivertsensoftware.userregistration.proxy.rest.OpaClient;

@Component
public class OPAAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OpaClient opaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

        var httpServletRequest = requestAuthorizationContext.getRequest();

        String[] path = httpServletRequest.getRequestURI().replaceAll("^/|/$", "").split("/");

        ContentCachingHttpServletRequest contentCachingHttpServletRequest = WebUtils.getNativeRequest(httpServletRequest, ContentCachingHttpServletRequest.class);

        Map<String, Object> input = new HashMap<>();

        input.put("user", authentication.get().getPrincipal());
        input.put("method", httpServletRequest.getMethod());
        input.put("path", path);
        input.put("payload", objectMapper.readTree(contentCachingHttpServletRequest.getInputStream()));

        OPADataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OPADataRequest(input));

        return new AuthorizationDecision(opaDataResponse.getResult().getAllow());
    }
    
}
