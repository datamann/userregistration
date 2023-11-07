package no.sivertsensoftware.userregistration.proxy.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.sivertsensoftware.userregistration.config.OPADataRequest;
import no.sivertsensoftware.userregistration.config.OPADataResponse;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "opaAuthorization", url = "${app.opa.authz.url}")

@RestController
public interface OpaClient {

    @PostMapping("dnb/userregistration/auth")
    OPADataResponse authorizedToAccessAPI(@RequestBody OPADataRequest opaDataRequest);
    
}
