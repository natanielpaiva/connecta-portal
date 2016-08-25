package br.com.cds.connecta.portal.business.applicationService;

import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.portal.entity.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public interface IUserAS {

    User get(Long id);

    User getByEmail(String username);

    InputStream getUserImage(Long id) throws IOException;

    User upload(Long id, MultipartFile file) throws IOException;

    User save(User user);

    User update(Long id, User user);

    void setUserImage(Long id) throws IOException;

    List<User> getByName(String name);
}
