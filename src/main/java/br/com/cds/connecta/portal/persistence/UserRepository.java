package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Heloisa Alves
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String login);

    User findByHashPassword(String hashPassword);

    User findByHashInvited(String hashInvited);

    List<User> findByName(String name);
    
    @Query("SELECT u FROM User u WHERE "
            + ":domain NOT MEMBER OF u.domains "
            + "ORDER BY u.name, u.email")
    List<User> findByDomainsNotInOrder(@Param("domain")Domain domain, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE "
            + ":domain NOT MEMBER OF u.domains AND "
            + "(u.name LIKE CONCAT('%',:regex,'%') OR "
            + "u.email LIKE CONCAT('%',:regex,'%')) "  
            + "ORDER BY u.name, u.email")
    List<User> findByRegexOrderByNameAsc(@Param("regex") String regex, @Param("domain")Domain domain);
}