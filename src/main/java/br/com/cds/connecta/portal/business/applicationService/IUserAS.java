package br.com.cds.connecta.portal.business.applicationService;

import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.vo.InviteRequestVO;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author heloisa
 */
public interface IUserAS {

    User get(Long id);

    User get(Principal user);

    User getByEmail(String username);

    List<User> getByName(String name);

    User getByHash(String hash);

    List<User> getAll();

    InputStream getUserImage(Long id) throws IOException;

    boolean isAvailableEmail(String email);

    User save(User user);

    User saveUser(User user);

    User saveInvited(User user);

    User saveInvite(InviteRequestVO inviteRequestVO, UUID hash);

    User update(Long id, User user);

    User update(User user);

    User upload(Long id, MultipartFile file) throws IOException;

    User updatePassword(User userLogged, String oldPass, String newPass);

    void setUserImage(Long id) throws IOException;

    User removeDomain(Long idUser, Long idDomain);

    void recoveryPassword(String email);

    void sendRecoveryPassword(String email);

    User resetPassword(Long id, String newPass);

}
