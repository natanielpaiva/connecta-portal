package br.com.cds.connecta.portal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.cds.connecta.portal.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>, 
										JpaSpecificationExecutor<Role>  {
    
}
