package no.sivertsensoftware.userregistration.config;

import lombok.Data;

@Data
public class OPADataResponse {

    private OPAResult result;

    @Data
    public static class OPAResult{
        private Boolean allow;
    }
    
}
