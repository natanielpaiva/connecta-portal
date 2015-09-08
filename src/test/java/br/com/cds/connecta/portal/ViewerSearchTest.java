package br.com.cds.connecta.portal;

import static br.com.cds.connecta.framework.core.test.ConnectaMatchers.*;
import br.com.cds.connecta.portal.domain.DashboardDisplayMode;
import br.com.cds.connecta.portal.domain.DashboardSectionAnimation;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;

/**
 * 
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class ViewerSearchTest extends BaseTest {
    
    static final String RESOURCE = REST_PATH.concat("viewer");
    
    @Test
    public void searchViewers() throws Exception {
        mockMvc().perform(get(RESOURCE)
            .param("page", "1")
            .param("count", "10")
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", notNullValue()))
            .andExpect(jsonPath("$.content[*]", hasSize(greaterThan(0))))
            .andExpect(jsonPath("$.content[*].id", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$.content[*].name", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$.content[*].displayMode", todosOsItens(anyOf(
                enumKeyFor(DashboardDisplayMode.VERTICAL),
                enumKeyFor(DashboardDisplayMode.PAGE)
            ))))
            .andExpect(jsonPath("$.content[*].sectionTransitionAnimation", todosOsItens(anyOf(
                enumKeyFor(DashboardSectionAnimation.FADE),
                enumKeyFor(DashboardSectionAnimation.SLIDE_DOWN),
                enumKeyFor(DashboardSectionAnimation.SLIDE_LEFT),
                enumKeyFor(DashboardSectionAnimation.SLIDE_RIGHT),
                enumKeyFor(DashboardSectionAnimation.SLIDE_UP)
            ))))
            .andExpect(jsonPath("$.content[*].sections", todosOsItens(nullValue())))
            ;
    }
    
}
 