package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Julio Lemes
 * @date Aug 27, 2015
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String login);
    
    User findByHashPassword(String hashPassword);
    
    User findByHashInvited(String hashInvited);
    
    List<User> findByName(String name);
    
}
