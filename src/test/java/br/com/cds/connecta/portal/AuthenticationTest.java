package br.com.cds.connecta.portal;

import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Julio Lemes
 * @date Jul 28, 2015
 */
public class AuthenticationTest extends BaseTest {

    private @Autowired IAuthenticationAS auth;

    @Test
    public void serviceTest() {
        System.out.println("arg");
    }


}
