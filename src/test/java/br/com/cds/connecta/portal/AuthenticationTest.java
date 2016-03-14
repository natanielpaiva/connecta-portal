package br.com.cds.connecta.portal;

import static org.hamcrest.MatcherAssert.assertThat;
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
    
    static final String RESOURCE_APPLICATION = REST_PATH.concat("application");
    static final String RESOURCE_DASH = REST_PATH.concat("dashboard");

    @Test
    @Ignore
    public void authenticatePost() throws Exception {
        mockMvc().perform(
            post(RESOURCE_ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("username=user&password=password&client_id=testclient&client_secret=testsecret&grant_type=password")
        ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()));
    }
    
    @Test
    @Ignore
    public void errorUnauthorized() throws Exception {
        mockMvc().perform(get(RESOURCE_APPLICATION))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$", notNullValue()));
        
        mockMvc().perform(get(RESOURCE_DASH))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$", notNullValue()));
    }
    
    @Test
    @Ignore
    public void authenticateGet() throws Exception {
        assertThat(getAccessToken(), notNullValue());
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
//        mockMvc().perform(get(RESOURCE_APPLICATION)
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
