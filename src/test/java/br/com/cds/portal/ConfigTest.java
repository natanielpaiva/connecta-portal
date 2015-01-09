/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.portal;

import org.junit.Test;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;
import static br.com.cds.portal.util.matchers.ConnectaMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author pires
 */
public class ConfigTest extends BaseTest {
    static final String RESOURCE = REST_PATH.concat("config.json");
    
    @Test
    public void sucessoBuscarConfiguracao() throws Exception {
        mockMvc().perform(get(RESOURCE)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.applications", notNullValue()))
            .andExpect(jsonPath("$.applications[*]", hasSize(greaterThan(1))))
            .andExpect(jsonPath("$.applications[*].host", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$.applications[*].name", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$.applications[*].title", todosOsItens(notNullValue())));
        
    }
    
}
