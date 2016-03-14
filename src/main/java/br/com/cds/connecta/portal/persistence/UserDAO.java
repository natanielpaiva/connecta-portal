package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Julio Lemes
 * @date Aug 27, 2015
 */
public interface UserDAO extends JpaRepository<User, Long> {
    
    User findByEmail(String email);

}
