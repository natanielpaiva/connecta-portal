package br.com.cds.connecta.portal.business.applicationService;

import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.dto.InviteRequestDTO;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 *

 * @author Heloisa Alves

 */
public interface IUserAS {

    User get(Long id);

    User get(Principal user);


    List<User> get(int length,Long idDomain);
    

    User getByEmail(String username);

    List<User> getByName(String name);

    User getByHashInvited(String hash);
    

    List<User> getByRegex(String regex, Long iDomains);
  
    User getByHashPassword(String hash);

    List<User> getAll();

    InputStream getUserImage(Long id) throws IOException;
    
    boolean isAvailableEmail(String email);

    User save(User user);

    User saveUser(User user);

    User saveInvited(User user);

    User saveInvite(InviteRequestDTO inviteRequestVO, UUID hash);

    User update(Long id, User user);

    User update(User user);

    User upload(Long id, MultipartFile file) throws IOException;

    User updatePassword(User userLogged, String oldPass, String newPass);

    void setUserImage(Long id) throws IOException;

    User removeDomain(Long idUser, Long idDomain);

    void sendRecoveryPassword(String email, String url);

    User resetPassword(String hash, String newPasss);

}
