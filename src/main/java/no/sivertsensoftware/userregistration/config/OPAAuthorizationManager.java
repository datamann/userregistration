package no.sivertsensoftware.userregistration.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.web.util.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import lombok.SneakyThrows;
import no.sivertsensoftware.userregistration.proxy.rest.OpaClient;
import no.sivertsensoftware.userregistration.service.OpaauthorizationService;

@Configuration
public class OPAAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>{

    private static final Logger logger = LogManager.getLogger(OPAAuthorizationManager.class);

    private final OpaClient opaClient;
    private final ObjectMapper objectMapper;
    private final OpaauthorizationService authorizationService;

    public OPAAuthorizationManager( OpaClient opaClient, ObjectMapper objectMapper, OpaauthorizationService authorizationService)
    {
        this.opaClient = opaClient;
        this.objectMapper = objectMapper;
        this.authorizationService = authorizationService;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    @SneakyThrows
    public AuthorizationDecision check (Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

        var httpServletRequest = requestAuthorizationContext.getRequest();
        var getUserPrincipal = requestAuthorizationContext.getRequest().getUserPrincipal();

        String[] path = httpServletRequest.getRequestURI().replaceAll("^/|/$", "").split("/");

        ContentCachingHttpServletRequest contentCachingHttpServletRequest = WebUtils.getNativeRequest(httpServletRequest, ContentCachingHttpServletRequest.class);

        Map<String, Object> input = new HashMap<>();

        input.put("user", authentication.get().getPrincipal());
        input.put("method", httpServletRequest.getMethod());
        input.put("path", path);
        input.put("payload", objectMapper.readTree(contentCachingHttpServletRequest.getInputStream()));

        OPADataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OPADataRequest(input));
        logger.info("Users authorizations from OPA is: " + opaDataResponse.toString());

        String allow = opaDataResponse.getResult().getEval().get("allow");
        if (allow != null) {
            authorizationService.setAllow(allow);
        } else {
            authorizationService.setAllow("false");
        }

        String not_denied = opaDataResponse.getResult().getEval().get("not_denied");
        if (not_denied != null){
            authorizationService.setNotDenied(not_denied);
        } else {
            authorizationService.setNotDenied("false");
        }

        String user_read_permission = opaDataResponse.getResult().getEval().get("user_read_permission");
        if (user_read_permission != null || user_read_permission != "null"){
            authorizationService.setReadPermission(user_read_permission);
        } else {
             authorizationService.setReadPermission("false");
        }

        String user_write_permission = opaDataResponse.getResult().getEval().get("user_write_permission");
        if (user_write_permission != null || user_write_permission != "null"){
            authorizationService.setWritePermission(user_write_permission);
            logger.info("----- OPAAuthorizationManager ----- User have write permisson: " + user_write_permission);
        } else {
             authorizationService.setWritePermission("false");
             logger.info("----- OPAAuthorizationManager ----- User have write permisson: false");
        }

        String user_converted_to_read_only = opaDataResponse.getResult().getEval().get("user_converted_to_read_only");
        if (user_converted_to_read_only != null  || user_converted_to_read_only != "null") {
            authorizationService.setReadOnlyPermission(user_converted_to_read_only);
            logger.info("----- OPAAuthorizationManager ----- User is converted to read only: " + user_converted_to_read_only);
        } else {
            logger.info("----- OPAAuthorizationManager ----- User is converted to read only: false");
            authorizationService.setReadOnlyPermission("false");
        }

        if (!( authorizationService.getReadPermission() == null || authorizationService.getReadOnlyPermission() == null )){
            if ( authorizationService.getReadPermission() == "true" || authorizationService.getReadOnlyPermission() == "true" ){
                logger.info("----- OPAAuthorizationManager ----- User does NOT have write permission!");
                authorizationService.setUserHasWritePermission("false");
            }
        } else if ( authorizationService.getWritePermission() == "true" ){
            logger.info("----- OPAAuthorizationManager ----- User have write permission!");
            authorizationService.setUserHasWritePermission("true");
        } else {
            logger.info("----- OPAAuthorizationManager ----- User does NOT have write permission!");
            authorizationService.setUserHasWritePermission("false");
        }

        return new AuthorizationDecision(opaDataResponse.getResult().getAllow());
    }
}
