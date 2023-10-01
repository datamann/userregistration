package no.sivertsensoftware.userregistration.endpoints;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class Utillities {
    @AnonymousAllowed
    public String checkUser() {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Stig B - auth: " + auth);
        return auth == null ? null : auth.getName();
    }
}
