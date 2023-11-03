package no.sivertsensoftware.userregistration.proxy.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import no.sivertsensoftware.userregistration.config.OPADataRequest;
import no.sivertsensoftware.userregistration.config.OPADataResponse;

import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(value = "opaAuthorization", url = "${app.opa.authz.url}")
@FeignClient(value = "opaAuthorization", url = "http://localhost:8181/v1/data/dnb/userregistration/auth")
public interface OpaClient {

    @PostMapping("dnb/userregistration/auth")
    OPADataResponse authorizedToAccessAPI(@RequestBody OPADataRequest opaDataRequest);
    
}
