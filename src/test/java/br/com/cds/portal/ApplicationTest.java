/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.portal;

import org.junit.Test;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;
import static br.com.cds.connecta.framework.core.test.ConnectaMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author pires
 */
public class ApplicationTest extends BaseTest {
    static final String RESOURCE = REST_PATH.concat("application");
    static final String RESOURCE_ID = RESOURCE.concat("/1");
    
    @Test
    public void sucessoBuscarConfiguracao() throws Exception {
        mockMvc().perform(get(RESOURCE)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$[*]", hasSize(greaterThan(1))))
            .andExpect(jsonPath("$[*].host", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$[*].name", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$[*].title", todosOsItens(notNullValue())));
    }
    
    @Test
    public void sucessoBuscarConfiguracaoPorID() throws Exception {
        mockMvc().perform(get(RESOURCE_ID)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.host", allOf(notNullValue(), containsString("http://"), containsString("presenter"))))
            .andExpect(jsonPath("$.name", allOf(notNullValue(), equalTo("presenter"))))
            .andExpect(jsonPath("$.title", allOf(notNullValue(), equalToIgnoringCase("presenter"))));
    }
    
}
