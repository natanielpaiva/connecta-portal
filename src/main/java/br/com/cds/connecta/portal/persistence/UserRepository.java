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
    
    User findByHash(String hash);

    List<User> findByName(String name);

    User findOne(Long id);

}
