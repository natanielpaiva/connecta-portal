package br.com.cds.connecta.portal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cds.connecta.portal.entity.Domain;

/**
 *
 * @author Allex Araujo
 */
public interface DomainRepository extends JpaRepository<Domain, Long> {

    Domain findByName(String name);

}
