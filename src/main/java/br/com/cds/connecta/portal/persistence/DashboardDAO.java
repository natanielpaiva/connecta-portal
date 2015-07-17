package br.com.cds.connecta.portal.persistence;

import org.springframework.stereotype.Repository;

import br.com.cds.connecta.portal.entity.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DashboardDAO extends JpaRepository<Dashboard, Long> {

    @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.sections s WHERE d.id = :id")
    Dashboard findOneWithSections(@Param("id") Long id);
    
    @Query("FROM Dashboard d WHERE TRANSLATE(TRIM(UPPER(d.name)),'ÁÂÀÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜÇ','AAAAAEEEEEIIIIIOOOOOUUUUUC') LIKE :name")
    Page<Dashboard> findAllLikeName(@Param("name") String name, Pageable pageable);
    
}