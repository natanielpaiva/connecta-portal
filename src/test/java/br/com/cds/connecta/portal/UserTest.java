package br.com.cds.connecta.portal;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import static br.com.cds.connecta.portal.BaseTest.getTestResourceInputStream;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.business.applicationService.impl.UserAS;
import br.com.cds.connecta.portal.entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import static org.apache.commons.io.IOUtils.contentEquals;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Heloisa
 */
public class UserTest extends BaseTest {

    @Autowired
    private IUserAS service;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private class FileUploadTest implements MultipartFile{

        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getOriginalFilename() {
            return "";
        }

        @Override
        public String getContentType() {
            return "";
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 1L;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return IOUtils.toByteArray(getInputStream());
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return emptyPixel();
        }

        @Override
        public void transferTo(File file) throws IOException, IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public static InputStream defaultImage() {
        return UserAS.class.getClassLoader().getResourceAsStream("./user-default.jpg");
    }
    
    public static InputStream emptyPixel() {
        return getTestResourceInputStream("file/pixel.png");
    }

    @Test
    public void saveUser() throws IOException {
        User user = new User();
        user.setEmail("dgdg");
        user.setPassword("rgd");
        user.setImage("".getBytes()); // qualquer binário pra assegurar se ele ignora a imagem
        
        service.save(user);
        
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getEmail(), equalTo("dgdg"));
//        assertThat(user.getPassword(), equalTo("rgd"));
        assertThat(user.getImage(), nullValue());
        
        InputStream userImage = service.getUserImage(user.getId());
        
        assertThat(contentEquals(userImage, defaultImage()), equalTo(true));
    }
    
    @Test
    public void updateUser() throws Exception{
        User user = new User();
        
        user.setName("ooo");
        user.setEmail("abc");
        user.setPassword("qualquercoisa");  // Não deve ser atualizado
        user.setImage("qualquercoisa".getBytes());  // Também :D
        
        User updatedUser = service.update(11L, user);
        
        assertThat(updatedUser.getName(), equalTo("ooo"));
        assertThat(updatedUser.getEmail(), equalTo("abc"));
        
        assertThat(updatedUser.getPassword(), not(equalTo("qualquercoisa")));
        assertThat(updatedUser.getImage(), not(equalTo("qualquercoisa".getBytes())));
    }
    
    @Test
    public void updateUserPassword() throws Exception{
        User user = service.get(11L);
        user.setImage("".getBytes());
        
        User updatedUser = service.updatePassword(user.getId(),"321", "abc");
        
        InputStream userImage = service.getUserImage(user.getId());
        
        assertTrue(passwordEncoder.matches("abc", updatedUser.getPassword()));
        //Garantir que os dados serão mantidos
        assertThat(contentEquals(userImage, defaultImage()), equalTo(true)); 
        assertThat(user.getEmail(), equalTo("cds"));
    }
    
    @Test
    public void uploadNewUserImage() throws IOException {
        service.upload(11L, new FileUploadTest());
        
        InputStream userImage = service.getUserImage(11L);
        
        assertThat(contentEquals(userImage, emptyPixel()), equalTo(true));
    }
    
    @Test
    public void deleteUserImage() throws IOException{
        service.upload(10L, new FileUploadTest());
        
        InputStream firstImage = service.getUserImage(10L);
        
        assertThat(contentEquals(firstImage, defaultImage()), equalTo(false));
        
        service.upload(10L, null);
        
        InputStream userImage = service.getUserImage(10L);
        
        assertThat(contentEquals(userImage, defaultImage()), equalTo(true));
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getUserImageUnexistingUser() throws IOException{
        service.getUserImage(50L);
    }

}
