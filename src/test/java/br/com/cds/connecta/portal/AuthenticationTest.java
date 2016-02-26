package br.com.cds.connecta.portal;

import static br.com.cds.connecta.framework.core.test.ConnectaMatchers.todosOsItens;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Nataniel Paiva
 * @date Feb 19, 2016
 */
public class AuthenticationTest extends BaseTest {
    
    

    static final String RESOURCE_ACCESS_TOKEN = REST_PATH.concat("oauth/token");
    static final String RESOURCE_USER_AUTORIZE = REST_PATH.concat("oauth/authorize?client_id=testclient");
    static final String RESOURCE_HOME = REST_PATH.concat("application");
    static final String RESOURCE_DASH = REST_PATH.concat("dashboard/viewer");

    @Test
    @Ignore
    public void authenticatePost() throws Exception {
        mockMvc().perform(post(RESOURCE_ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("username=user&password=password&client_id=testclient&client_secret=testsecret&grant_type=password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

    }
    @Test
    @Ignore
    public void authenticateGet() throws Exception {
        mockMvc().perform(get(RESOURCE_ACCESS_TOKEN + "?grant_type=implicit&username=user&password=batata"
                + "&client_id=connecta_portal&client_secret=secret"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

    }

    @Test
    @Ignore
    public void authorizeTest() throws Exception {
        mockMvc().perform(get(RESOURCE_USER_AUTORIZE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
    
//    @Test
//    public void dashboard() throws Exception {
//        mockMvc().perform(get(RESOURCE_DASH)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()));
//    }
//
//    @Test
//    public void home() throws Exception {
//        mockMvc().perform(get(RESOURCE_HOME)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$[*]", hasSize(greaterThan(1))))
//                .andExpect(jsonPath("$[*].host", todosOsItens(notNullValue())))
//                .andExpect(jsonPath("$[*].name", todosOsItens(notNullValue())))
//                .andExpect(jsonPath("$[*].title", todosOsItens(notNullValue())));
//    }
//    
//    

}
