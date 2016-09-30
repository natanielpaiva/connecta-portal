package br.com.cds.connecta.portal;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import static br.com.cds.connecta.portal.BaseTest.getTestResourceInputStream;
import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.business.applicationService.impl.UserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.vo.InviteRequestVO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import static org.apache.commons.io.IOUtils.contentEquals;
import static org.hamcrest.Matchers.greaterThan;
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
    private IUserAS userService;
    
    @Autowired
    private IDomainAS domainService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private class FileUploadTest implements MultipartFile {

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

        userService.saveUser(user);

        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getEmail(), equalTo("dgdg"));
        assertTrue(passwordEncoder.matches("rgd", user.getPassword()));
        assertThat(user.getImage(), nullValue());

        InputStream userImage = userService.getUserImage(user.getId());

        assertThat(contentEquals(userImage, defaultImage()), equalTo(true));
    }
    
    @Test
    public void saveInvited(){
        User user = userService.get(13L);
        user.setName("Algum nome bonito");
        user.setPassword("senha123");
        User userSaved = userService.saveInvited(user);
        
        assertThat(userSaved.getHash(), nullValue());
        assertThat(userSaved.getEmail(), equalTo("abc@cds.com.br"));
        
    }
    
    @Test
    public void saveInvite(){
        InviteRequestVO inviteRequestVO = new InviteRequestVO();
        Domain domain = domainService.get(100L);
        inviteRequestVO.setDomain(domain);
        inviteRequestVO.setReceiver("xyz@cds.com");
        User user = userService.saveInvite(inviteRequestVO, UUID.randomUUID());
        
        assertThat(user.getHash(), not(nullValue()));
        assertThat(user.getEmail(), equalTo("xyz@cds.com"));
        assertTrue(user.getDomains().contains(domain));
        
    }
    
    //Somento o ultimo hash será válido
    @Test
    public void saveManyInvite(){
        
        InviteRequestVO inviteRequestVO = new InviteRequestVO();
        inviteRequestVO.setDomain(domainService.get(100L));
        inviteRequestVO.setReceiver("xyz@cds.com");
        UUID hashUm = UUID.randomUUID();
        User user = userService.saveInvite(inviteRequestVO, hashUm);
        
        inviteRequestVO = new InviteRequestVO();
        inviteRequestVO.setDomain(domainService.get(101L));
        inviteRequestVO.setReceiver("xyz@cds.com");
        UUID hashDois = UUID.randomUUID();
        user = userService.saveInvite(inviteRequestVO, hashDois);
        
        assertThat(user.getHash(), equalTo(hashDois.toString()));
        
    }
    
    @Test
    public void inviteExistingUser(){
        
        InviteRequestVO inviteRequestVO = new InviteRequestVO();
        Domain domain = domainService.get(100L);
        inviteRequestVO.setDomain(domain);
        inviteRequestVO.setReceiver("ednaldopereira");
        User user = userService.saveInvite(inviteRequestVO, UUID.randomUUID());
        
        assertThat(user.getHash(), nullValue());
        assertTrue(user.getDomains().contains(domain));
    }
    
    @Test(expected = AlreadyExistsException.class)
    public void unavailableEmail(){
        userService.isAvailableEmail("ednaldopereira");
    }
    
    @Test
    public void updateUserPassword() throws Exception{
        User user = userService.get(12L);
        
        User updatedUser = userService.updatePassword(user,"123", "abc");
        
        //Garantir que os dados serão mantidos
        assertThat(updatedUser.getEmail(), equalTo("ednaldopereira")); // Não deve ser atualizado
        assertThat(updatedUser.getName(), equalTo("123"));// Também :D
        assertThat(passwordEncoder.matches("abc", updatedUser.getPassword()), equalTo(true));
    }
    
    @Test
    public void updateUser() throws Exception {
        User user = new User();

        user.setName("ooo");
        user.setEmail("abc");
        user.setPassword("qualquercoisa");  // Não deve ser atualizado
        user.setImage("qualquercoisa".getBytes());  // Também :D

        User updatedUser = userService.update(11L, user);

        assertThat(updatedUser.getName(), equalTo("ooo"));
        assertThat(updatedUser.getEmail(), equalTo("abc"));

        assertThat(updatedUser.getPassword(), not(equalTo("qualquercoisa")));
        assertThat(updatedUser.getImage(), not(equalTo("qualquercoisa".getBytes())));
    }

    
    @Test
    public void uploadNewUserImage() throws IOException {
        userService.upload(11L, new FileUploadTest());

        InputStream userImage = userService.getUserImage(11L);

        assertThat(contentEquals(userImage, emptyPixel()), equalTo(true));
    }

    @Test
    public void deleteUserImage() throws IOException {
        userService.upload(10L, new FileUploadTest());

        InputStream firstImage = userService.getUserImage(10L);

        assertThat(contentEquals(firstImage, defaultImage()), equalTo(false));

        userService.upload(10L, null);

        InputStream userImage = userService.getUserImage(10L);

        assertThat(contentEquals(userImage, defaultImage()), equalTo(true));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserImageUnexistingUser() throws IOException {
        userService.getUserImage(50L);
    }

}
