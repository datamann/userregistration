package no.sivertsensoftware.userregistration.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import no.sivertsensoftware.userregistration.model.Opaauthorization;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OpaauthorizationService {

    private final Opaauthorization authorization;

    public OpaauthorizationService(Opaauthorization authorization) {
        this.authorization = authorization;
    }

    public String getAllow() {
        return authorization.getAllow();
    }

    public void setAllow(String setAllow) {
        this.authorization.setAllow(setAllow);
    }

    public String getNotDenied() {
        return authorization.getNot_denied();
    }

    public void setNotDenied(String setNotDenied) {
        this.authorization.setNot_denied(setNotDenied);
    }

    public String getReadPermission() {
        return authorization.getUser_read_permission();
    }
    public void setReadPermission(String setReadPermission) {
        this.authorization.setUser_read_permission(setReadPermission);
    }

    public String getWritePermission() {
        return authorization.getUser_write_permission();
    }
    public void setWritePermission(String setWritePermission) {
        this.authorization.setUser_write_permission(setWritePermission);
    }

    public String getReadOnlyPermission() {
        return this.authorization.getUser_converted_to_read_only();
    }
    public void setReadOnlyPermission(String setReadOnlyPermission) {
        this.authorization.setUser_converted_to_read_only(setReadOnlyPermission);
    }
    public String getUserHasWritePermission() {
        return this.authorization.getHasWritePermission();
    }
    public void setUserHasWritePermission(String setUserHasWritePermission) {
        this.authorization.setHasWritePermission(setUserHasWritePermission);
    }
}
