package br.com.cds.connecta.portal;

import static br.com.cds.connecta.framework.core.test.ConnectaMatchers.todosOsItens;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

/**
 *
 * @author pires
 */
public class ApplicationTest extends BaseTest {
    static final String RESOURCE = REST_PATH.concat("application");
    static final String RESOURCE_ID = RESOURCE.concat("/{id}");
    
    @Test
    public void listApplications() throws Exception {
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
    public void getApplication() throws Exception {
        mockMvc().perform(get(RESOURCE_ID, 1)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.host", allOf(notNullValue(), containsString("http://"), containsString("presenter"))))
            .andExpect(jsonPath("$.name", allOf(notNullValue(), equalTo("presenter"))))
            .andExpect(jsonPath("$.title", allOf(notNullValue(), equalToIgnoringCase("presenter"))));
    }
    
    @Test
    public void saveApplication() throws Exception {
        mockMvc().perform(post(RESOURCE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getJson("application/new-application"))
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.host", equalTo("http://speaknow.connecta.com")))
            .andExpect(jsonPath("$.name", equalTo("speaknow")))
            .andExpect(jsonPath("$.title", equalTo("SpeakNow")))
            .andExpect(jsonPath("$.id", allOf(notNullValue(), isA(Integer.class), greaterThan(0))));
    }
    
    @Test
    public void updateApplication() throws Exception {
        mockMvc().perform(put(RESOURCE_ID, 2)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getJson("application/edit-application"))
        ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.id", allOf(notNullValue(), isA(Integer.class), equalTo(2))))
            .andExpect(jsonPath("$.host", equalTo("http://connectad.cds.com.br/presenter")))
            .andExpect(jsonPath("$.name", equalTo("presenter")))
            .andExpect(jsonPath("$.title", equalTo("Presentacion de Pelota")));
    }
    
    @Test
    public void deleteApplication() throws Exception {
        mockMvc().perform(delete(RESOURCE_ID, 99)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
    }
    
}
