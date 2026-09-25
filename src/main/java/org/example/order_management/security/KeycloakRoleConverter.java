package org.example.order_management.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourcesAccess = jwt.getClaim("resource_access");

        if (resourcesAccess == null) {
            return List.of();
        }

        Map<String, Object> client = (Map<String, Object>) resourcesAccess.get("order-management-api");
        if (client == null) {
            return List.of();
        }

        Collection<String> roles = (Collection<String>) client.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}
