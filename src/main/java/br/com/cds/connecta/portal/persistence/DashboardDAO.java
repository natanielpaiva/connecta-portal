package br.com.cds.connecta.portal.persistence;

import org.springframework.stereotype.Repository;

import br.com.cds.connecta.portal.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DashboardDAO extends JpaRepository<Dashboard, Long> {
    
    
    
}
