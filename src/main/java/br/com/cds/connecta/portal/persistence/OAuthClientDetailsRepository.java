package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.auth.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Nataniel Paiva <nataniel.paiva@gmail.com at natanielpaiva.github.io>
 */
@Repository
public interface OAuthClientDetailsRepository extends JpaRepository<OAuthClientDetails, String> {
    
}