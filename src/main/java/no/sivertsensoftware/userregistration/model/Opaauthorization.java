package no.sivertsensoftware.userregistration.model;

import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@Data
public class Opaauthorization {
    private String allow;
    private String not_denied;
    private String user_read_permission;
    private String user_write_permission;
    private String user_converted_to_read_only;
    private String hasWritePermission = "false";
}
