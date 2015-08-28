package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Julio Lemes
 * @date Aug 27, 2015
 */
public interface UserImageDAO extends JpaRepository<UserImage, Long> {
    
    UserImage findByUserLogin(String userLogin);

}
