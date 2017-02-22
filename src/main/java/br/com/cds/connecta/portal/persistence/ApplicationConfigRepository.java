package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.ApplicationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julio Lemes
 * @date Jul 27, 2015
 */
@Repository
public interface ApplicationConfigRepository extends JpaRepository<ApplicationConfig, String> {
    
    ApplicationConfig findByParam(String param);
}