package br.com.cds.connecta.portal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

/**
 *
 * @author Heloisa
 */
public class ApplicationConfigTest extends BaseTest {

    static final String RESOURCE = REST_PATH.concat("application-config");
    static final String RESOURCE_ID = RESOURCE.concat("/{param}/logo");

    @Test
    public void saveFile() throws Exception {
        byte[] content = "fileteste".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("logo", "imagem.jpg", "image/jpeg", content);

        mockMvc().perform(fileUpload(RESOURCE_ID, "param-test")
                .file(mockMultipartFile)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.param", equalTo("param-test")))
                .andExpect(jsonPath("$.image", equalTo(null))); //null 'cause @JsonIgnore is present
    }

}