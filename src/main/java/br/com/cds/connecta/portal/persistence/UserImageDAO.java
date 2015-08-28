package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio Lemes
 * @date Aug 27, 2015
 */
public interface UserImageDAO extends JpaRepository<UserImage, Long> {
    
    UserImage findByUserLogin(@Param("login") String login);

}
