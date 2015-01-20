package br.com.cds.portal;

import br.com.cds.connecta.framework.core.test.MockMvcProvider;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Teste base de todos os projetos
 * @author pires
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration("test-application-context.xml")
public class BaseTest {
    @Autowired
    private MockMvcProvider mmp;
    protected Logger logger;
    protected static final String REST_PATH = "/";
    
    /**
     * Getter da configuração de {@link MockMvc} da {@link BaseTest}
     * @return 
     */
    protected MockMvc mockMvc() {
        return mmp.getInstance();
    }
    
    /**
     * Getter do Logger
     * @return 
     */
    protected Logger logger() {
        if (logger == null) {
            logger = Logger.getLogger(getClass());
        }
        return logger;
    }

    /**
     * Mantido apenas para execução do JUnit 
     */
    @Test
    public void baseTest() {}
}
