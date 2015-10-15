package br.com.cds.connecta.portal;

import br.com.cds.connecta.framework.core.domain.MessageEnum;
import br.com.cds.connecta.framework.core.domain.MessageTypeEnum;
import static br.com.cds.connecta.framework.core.test.ConnectaMatchers.*;
import br.com.cds.connecta.portal.domain.DashboardDisplayMode;
import br.com.cds.connecta.portal.domain.DashboardItemType;
import br.com.cds.connecta.portal.domain.DashboardSectionAnimation;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;

/**
 * 
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class DashboardTest extends BaseTest {
    
    static final String RESOURCE = REST_PATH.concat("dashboard");
    static final String RESOURCE_VIEWERS = RESOURCE.concat("/viewer");
    static final String RESOURCE_ID = RESOURCE.concat("/{id}");
    
    @Test
    public void listDashboards() throws Exception {
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
                enumKeyFor(DashboardDisplayMode.HORIZONTAL)
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
    
    @Test
    @Ignore // FIXME Falta fazer um mock do server do Solr.
    public void searchViewers() throws Exception {
        mockMvc().perform(get(RESOURCE_VIEWERS)
            .param("page", "1")
            .param("count", "10")
            .param("text", "vi")
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$[*]", hasSize(greaterThan(0))))
            .andExpect(jsonPath("$[*].id", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$[*].name", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$[*].description", todosOsItens(notNullValue())))
            .andExpect(jsonPath("$[*]._hibernate_class", todosOsItens(notNullValue())))
                ;
    }
    
    @Test
    public void listDashboardsByName() throws Exception {
        performSearch("painel", 1);
        performSearch("PAINEL", 1);
        performSearch(" PaInel   ", 1);
        performSearch(" PáInel   ", 1);
        performSearch("bogus", 0);
    }

    private void performSearch(String filterName, int expectedSize) throws Exception {
        mockMvc().perform(get(RESOURCE)
                .param("page", "1")
                .param("count", "10")
                .param("name", filterName)
        ).andDo(print())
                .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", notNullValue()))
                .andExpect(jsonPath("$.content[*]", hasSize(expectedSize)));
    }
    
    @Test
    public void getDashboard() throws Exception {
        mockMvc().perform(get(RESOURCE_ID, 10)
            .contentType(MEDIATYPE_JSON_UTF8)
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.id", is(10)))
            .andExpect(jsonPath("$.name", notNullValue()))
            .andExpect(jsonPath("$.sections", hasSize(1)))
            .andExpect(jsonPath("$.sections[0].id", is(10)))
            .andExpect(jsonPath("$.sections[0].items", hasSize(1)))
            .andExpect(jsonPath("$.sections[0].items[0].id", is(10)))
        ;
    }
    
    @Test
    public void dashboardNotFound() throws Exception {
        mockMvc().perform(get(RESOURCE_ID, 999)
            .contentType(MEDIATYPE_JSON_UTF8)
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.code", is(MessageEnum.NOT_FOUND.name())))
            .andExpect(jsonPath("$.type", is(MessageTypeEnum.ERROR.name())))
            .andExpect(jsonPath("$.message", is("Registro de Painel não encontrado.")))
        ;
    }
    
    @Test
    public void saveDashboard() throws Exception {
        mockMvc().perform(post(RESOURCE)
            .contentType(MEDIATYPE_JSON_UTF8)
            .content(getJson("dashboard/new-dashboard"))
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", equalTo("Novo Painel")))
            .andExpect(jsonPath("$.rowHeight", equalTo(32)))
            .andExpect(jsonPath("$.maxRows", equalTo(50)))
            .andExpect(jsonPath("$.columns", equalTo(4)))
            .andExpect(jsonPath("$.padding", equalTo(10)))
            .andExpect(jsonPath("$.displayMode", enumKeyFor(DashboardDisplayMode.VERTICAL)))
            .andExpect(jsonPath("$.sectionTransitionInterval", equalTo(5)))
            .andExpect(jsonPath("$.sectionTransitionDuration", equalTo(1000)))
            .andExpect(jsonPath("$.sectionTransitionAnimation", enumKeyFor(DashboardSectionAnimation.FADE)))
            .andExpect(jsonPath("$.backgroundColor", equalTo("#FF0000")))
            .andExpect(jsonPath("$.backgroundImage", equalTo(null)))
            .andExpect(jsonPath("$.sections", hasSize(2)))
                
            .andExpect(jsonPath("$.sections[0].id", is(1)))
            .andExpect(jsonPath("$.sections[0].name", is("Seção 1")))
            .andExpect(jsonPath("$.sections[0].order", is(0)))
            .andExpect(jsonPath("$.sections[0].items", hasSize(2)))
                
                .andExpect(jsonPath("$.sections[0].items[0].id", is(1)))
                .andExpect(jsonPath("$.sections[0].items[0].viewerUrl", is("http://localhost:7002/connecta-presenter/viewer/1")))
                .andExpect(jsonPath("$.sections[0].items[0].type", enumKeyFor(DashboardItemType.XY_CHART)))
                .andExpect(jsonPath("$.sections[0].items[0].backgroundColor", is("#00FF00")))
                .andExpect(jsonPath("$.sections[0].items[0].backgroundImage", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[0].contentPositionX", is(0)))
                .andExpect(jsonPath("$.sections[0].items[0].contentPositionY", is(0)))
                .andExpect(jsonPath("$.sections[0].items[0].sizeX", is(2)))
                .andExpect(jsonPath("$.sections[0].items[0].sizeY", is(1)))
                .andExpect(jsonPath("$.sections[0].items[0].contentHeight", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[0].contentWidth", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[0].row", is(1)))
                .andExpect(jsonPath("$.sections[0].items[0].column", is(1)))

                .andExpect(jsonPath("$.sections[0].items[1].id", is(2)))
                .andExpect(jsonPath("$.sections[0].items[1].viewerUrl", is("http://localhost:7002/connecta-presenter/viewer/2")))
                .andExpect(jsonPath("$.sections[0].items[1].type", enumKeyFor(DashboardItemType.LINEAR_CHART)))
                .andExpect(jsonPath("$.sections[0].items[1].backgroundColor", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[1].backgroundImage", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[1].contentPositionX", is(0)))
                .andExpect(jsonPath("$.sections[0].items[1].contentPositionY", is(0)))
                .andExpect(jsonPath("$.sections[0].items[1].sizeX", is(1)))
                .andExpect(jsonPath("$.sections[0].items[1].sizeY", is(2)))
                .andExpect(jsonPath("$.sections[0].items[1].contentHeight", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[1].contentWidth", nullValue()))
                .andExpect(jsonPath("$.sections[0].items[1].row", is(3)))
                .andExpect(jsonPath("$.sections[0].items[1].column", is(1)))
            
            .andExpect(jsonPath("$.sections[1].id", is(2)))
            .andExpect(jsonPath("$.sections[1].name", is("Seção 2")))
            .andExpect(jsonPath("$.sections[1].order", is(1)))
            .andExpect(jsonPath("$.sections[1].items", hasSize(2)))
                
                .andExpect(jsonPath("$.sections[1].items[0].id", is(3)))
                .andExpect(jsonPath("$.sections[1].items[0].viewerUrl", is("http://localhost:7002/connecta-presenter/viewer/1")))
                .andExpect(jsonPath("$.sections[1].items[0].type", enumKeyFor(DashboardItemType.XY_CHART)))
                .andExpect(jsonPath("$.sections[1].items[0].backgroundColor", is("#00FF00")))
                .andExpect(jsonPath("$.sections[1].items[0].backgroundImage", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[0].contentPositionX", is(0)))
                .andExpect(jsonPath("$.sections[1].items[0].contentPositionY", is(0)))
                .andExpect(jsonPath("$.sections[1].items[0].sizeX", is(2)))
                .andExpect(jsonPath("$.sections[1].items[0].sizeY", is(1)))
                .andExpect(jsonPath("$.sections[1].items[0].contentHeight", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[0].contentWidth", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[0].row", is(1)))
                .andExpect(jsonPath("$.sections[1].items[0].column", is(1)))

                .andExpect(jsonPath("$.sections[1].items[1].id", is(4)))
                .andExpect(jsonPath("$.sections[1].items[1].viewerUrl", is("http://localhost:7002/connecta-presenter/viewer/2")))
                .andExpect(jsonPath("$.sections[1].items[1].type", enumKeyFor(DashboardItemType.LINEAR_CHART)))
                .andExpect(jsonPath("$.sections[1].items[1].backgroundColor", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[1].backgroundImage", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[1].contentPositionX", is(0)))
                .andExpect(jsonPath("$.sections[1].items[1].contentPositionY", is(0)))
                .andExpect(jsonPath("$.sections[1].items[1].sizeX", is(1)))
                .andExpect(jsonPath("$.sections[1].items[1].sizeY", is(2)))
                .andExpect(jsonPath("$.sections[1].items[1].contentHeight", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[1].contentWidth", nullValue()))
                .andExpect(jsonPath("$.sections[1].items[1].row", is(3)))
                .andExpect(jsonPath("$.sections[1].items[1].column", is(1)))
        ;
    }
    
    @Test
    public void updateDashboard() throws Exception {
        mockMvc().perform(put(RESOURCE_ID, 10)
            .contentType(MEDIATYPE_JSON_UTF8)
            .content(getJson("dashboard/edit-dashboard"))
        ).andDo(print())
            .andExpect(content().contentType(MEDIATYPE_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(10)))
            .andExpect(jsonPath("$.name", equalTo("Painel atualizado")))
            .andExpect(jsonPath("$.rowHeight", equalTo(10)))
            .andExpect(jsonPath("$.maxRows", equalTo(10)))
            .andExpect(jsonPath("$.columns", equalTo(10)))
            .andExpect(jsonPath("$.padding", equalTo(10)))
            .andExpect(jsonPath("$.displayMode", enumKeyFor(DashboardDisplayMode.HORIZONTAL)))
            .andExpect(jsonPath("$.sectionTransitionInterval", equalTo(10)))
            .andExpect(jsonPath("$.sectionTransitionDuration", equalTo(1000)))
            .andExpect(jsonPath("$.sectionTransitionAnimation", enumKeyFor(DashboardSectionAnimation.SLIDE_UP)))
            .andExpect(jsonPath("$.backgroundColor", equalTo("#000000")))
            .andExpect(jsonPath("$.backgroundImage", nullValue()))
            .andExpect(jsonPath("$.sections", hasSize(1)))
                
            .andExpect(jsonPath("$.sections[0].id", is(10)))
            .andExpect(jsonPath("$.sections[0].name", is("Seção 1")))
            .andExpect(jsonPath("$.sections[0].order", is(0)))
            .andExpect(jsonPath("$.sections[0].items", hasSize(1)))
                
            .andExpect(jsonPath("$.sections[0].items[0].id", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].viewerUrl", is("http://localhost:7002/connecta-presenter/viewer/1")))
            .andExpect(jsonPath("$.sections[0].items[0].type", enumKeyFor(DashboardItemType.XY_CHART)))
            .andExpect(jsonPath("$.sections[0].items[0].backgroundColor", is("#00FF00")))
            .andExpect(jsonPath("$.sections[0].items[0].backgroundImage", nullValue()))
            .andExpect(jsonPath("$.sections[0].items[0].contentPositionX", is(0)))
            .andExpect(jsonPath("$.sections[0].items[0].contentPositionY", is(0)))
            .andExpect(jsonPath("$.sections[0].items[0].sizeX", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].sizeY", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].contentHeight", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].contentWidth", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].row", is(10)))
            .andExpect(jsonPath("$.sections[0].items[0].column", is(10)))
        ;
    }
    
    @Test
    public void deleteDashboard() throws Exception {
        mockMvc().perform(delete(RESOURCE_ID, 99)
            .contentType(MEDIATYPE_JSON_UTF8)
        ).andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
    }
}
 