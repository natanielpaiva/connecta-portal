package br.com.cds.connecta.portal.persistence;

import br.com.cds.connecta.portal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pires
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
}
