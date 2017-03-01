package br.com.cds.connecta.portal.config;

import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.auth.OAuthClientDetails;
import br.com.cds.connecta.portal.persistence.OAuthClientDetailsRepository;
import br.com.cds.connecta.portal.persistence.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nataniel Paiva <nataniel.paiva@gmail.com at natanielpaiva.github.io>
 */
@Component
public class OAuthDatabaseConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private OAuthClientDetailsRepository authClientDetailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {

        List<OAuthClientDetails> authClientDetails = authClientDetailsRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        if (authClientDetails.size() == 0) {
            OAuthClientDetails authClientDetailsPersist = new OAuthClientDetails();
            authClientDetailsPersist.setClientId("frontend");
            authClientDetailsPersist.setResourceIds("springsec");
            authClientDetailsPersist.setClientSecret("$2a$11$UwjT9YpLgdPVlL95kFGV1.W02d.voVJ/gUxWKHZdlWsYWrsuQRG4S");
            authClientDetailsPersist.setScope("read,write,trust");
            authClientDetailsPersist.setAuthorizedGrantTypes("password,refresh_token,client_credentials");
            authClientDetailsPersist.setAuthorities("ROLE_USER,ROLE_ADMIN");
            authClientDetailsPersist.setAccessTokenValidity("50000");
            authClientDetailsPersist.setRefreshTokenValidity("20000");

            authClientDetailsRepository.save(authClientDetailsPersist);
        }

        if (roles.size() == 0) {
            Role role = new Role();
            role.setName("ROLE_USER");

            roleRepository.save(role);
        }

    }

}
