package no.sivertsensoftware.userregistration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Objects;


@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtAuthConverterProperties {
    private String resourceId;
    private String principalAttribute;

    public JwtAuthConverterProperties() {
    }

    public JwtAuthConverterProperties(String resourceId, String principalAttribute) {
        this.resourceId = resourceId;
        this.principalAttribute = principalAttribute;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPrincipalAttribute() {
        return this.principalAttribute;
    }

    public void setPrincipalAttribute(String principalAttribute) {
        this.principalAttribute = principalAttribute;
    }

    public JwtAuthConverterProperties resourceId(String resourceId) {
        setResourceId(resourceId);
        return this;
    }

    public JwtAuthConverterProperties principalAttribute(String principalAttribute) {
        setPrincipalAttribute(principalAttribute);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof JwtAuthConverterProperties)) {
            return false;
        }
        JwtAuthConverterProperties jwtAuthConverterProperties = (JwtAuthConverterProperties) o;
        return Objects.equals(resourceId, jwtAuthConverterProperties.resourceId) && Objects.equals(principalAttribute, jwtAuthConverterProperties.principalAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, principalAttribute);
    }

    @Override
    public String toString() {
        return "{" +
            " resourceId='" + getResourceId() + "'" +
            ", principalAttribute='" + getPrincipalAttribute() + "'" +
            "}";
    }
}