package no.sivertsensoftware.userregistration.config;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
//@Component
public class OPADataResponse {

    private OPAResult result;

    // public OPADataResponse(OPAResult result) {
    //     this.result = result;
    // }

    @Data
    //@Component
    public static class OPAResult{
        private Boolean allow;
        //private String allow;
    }
    
}
