package no.sivertsensoftware.userregistration.config;

import java.util.Map;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class OPADataResponse {

    private OPAResult result;

    @Data
    public static class OPAResult{
        private Boolean allow;
        Map<String, String> eval;
    }
}
