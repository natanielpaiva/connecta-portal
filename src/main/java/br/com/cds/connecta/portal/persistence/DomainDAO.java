package br.com.cds.connecta.portal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cds.connecta.portal.entity.Domain;

public interface DomainDAO extends JpaRepository<Domain, Long> {
    
}
