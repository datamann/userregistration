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
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.SneakyThrows;
import no.sivertsensoftware.userregistration.proxy.rest.OpaClient;

@Component
public class OPAAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final ObjectMapper objectMapper1 = new ObjectMapper();

    @Autowired
    private OpaClient opaClient;

    @Autowired
    private ObjectMapper objectMapper;

/**
 * Check this GITHub project:
 * https://github.com/anarsultanov/examples/blob/master/spring-security-opa-authz/src/main/java/dev/sultanov/springsecurity/opaauthz/config/OpaClient.java
 * https://github.com/lamoboos223/Spring-Boot-OPA-Example/blob/main/src/main/java/com/example/demo/ExampleService.java
 */

    @Override
    @SneakyThrows
    public AuthorizationDecision check (Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

        var httpServletRequest = requestAuthorizationContext.getRequest();

        String[] path = httpServletRequest.getRequestURI().replaceAll("^/|/$", "").split("/");

        ContentCachingHttpServletRequest contentCachingHttpServletRequest = WebUtils.getNativeRequest(httpServletRequest, ContentCachingHttpServletRequest.class);

        Map<String, Object> input = new HashMap<>();

        input.put("user", authentication.get().getPrincipal());
        input.put("method", httpServletRequest.getMethod());
        input.put("path", path);
        input.put("payload", objectMapper.readTree(contentCachingHttpServletRequest.getInputStream()));

        OPADataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OPADataRequest(input));

        ObjectNode requestNode = objectMapper1.createObjectNode();
        requestNode.set("input", objectMapper1.valueToTree(opaDataResponse.getResult().getEval()));
        System.out.println("-----------***---------- Test of JSON breakdown: " + requestNode.toPrettyString());

        
        // TODO: Remove!
        AuthorizationDecision des = new AuthorizationDecision(opaDataResponse.getResult().getAllow());
        System.out.println("Rule Allow: " + des);
        // Result:
        // Decision: AuthorizationDecision [granted=true]
        
        return des;
    }
    
}
