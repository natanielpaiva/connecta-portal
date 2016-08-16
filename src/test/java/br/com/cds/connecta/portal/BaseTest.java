package br.com.cds.connecta.portal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.cds.connecta.framework.core.test.MockMvcProvider;
import static br.com.cds.connecta.portal.AuthenticationTest.RESOURCE_ACCESS_TOKEN;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste base de todos os projetos
 * @author pires
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration({ 
                       "classpath:spring/security.xml",
                       "classpath:META-INF/br.com.cds.connecta.framework.test.xml"})
public class BaseTest {
    
    @Autowired
    private MockMvcProvider mmp;
    
    protected Logger logger;
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final String TEST_RESOURCE_FOLDER = "src/test/resources/";
    protected static final String FILE_CHARSET = "UTF-8";
    protected static final MediaType MEDIATYPE_JSON_UTF8 = MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE+";charset="+FILE_CHARSET);
    
    protected static final String REST_PATH = "/";
    protected static final String RESOURCE_ACCESS_TOKEN = REST_PATH.concat("oauth/token");
    protected static final String RESOURCE_USER_AUTORIZE = REST_PATH.concat("oauth/authorize?client_id=testclient");
    
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
     * Busca o conteúdo de um JSON da pasta src/test/resources/json como texto.
     * 
     * Exemplo: 
     * <br />
     * <b>Diretório:</b>
     * <pre>
     * src/
     *  test/
     *   resources/
     *    json/
     *     meu-modulo/
     *     - objeto-qualquer.json
     * </pre>
     * 
     * <b>Código:</b>
     * <pre>
     * // json = "{ ... }"
     * String json = getJson("meu-modulo/objeto-qualquer");
     * </pre>
     * 
     * @param jsonName
     * @return O conteúdo do JSON
     */
    protected String getJson(String jsonName) {
        return  getTestResourceContent(String.format("json/%s.json", jsonName));
    }
    
    /**
     * Retorna o conteúdo de um recurso da pasta src/test/resources como texto.
     * 
     * Exemplo: 
     * 
     * <br />
     * 
     * <b>Diretório:</b>
     * <pre>
     * src/
     *  test/
     *   resources/
     *    pasta/
     *    - arquivo.txt
     * </pre>
     * 
     * <b>Código:</b>
     * <pre>
     * String conteudo = getTestResourceContent("pasta/arquivo.txt");
     * </pre>
     * 
     * @param fileName
     * @return O conteúdo do arquivo
     */
    protected String getTestResourceContent(String fileName) {
        try {
            InputStream is = getTestResourceInputStream(fileName);
            return IOUtils.toString(is, FILE_CHARSET);
        } catch (IOException ex) {
            logger().error("Erro ao buscar arquivo "+fileName, ex);
        }
        return null;
    }

    protected static InputStream getTestResourceInputStream(String fileName) {
        InputStream is = null;
        try {
            is = new FileInputStream(TEST_RESOURCE_FOLDER.concat(fileName));
        } catch (FileNotFoundException ex) {}
        return is;
    }
    
    protected String getAccessToken() throws Exception {
        MvcResult result = mockMvc().perform(
            get(RESOURCE_ACCESS_TOKEN)
                .param("grant_type", "password")
                .param("client_id", "frontend")
                .param("client_secret", "secret")
                .param("username", "user")
                .param("password", "pass")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        
        // FIXME traduzir JSON
        OAuth2Response response = objectMapper.readValue(result.getResponse().getContentAsString(), OAuth2Response.class);
        
        return response.getAccess_token();
    }

    /**
     * Mantido apenas para execução do JUnit 
     */
    @Test
    public void baseTest() {}
    
    class OAuth2Response {
        private String access_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }

}
