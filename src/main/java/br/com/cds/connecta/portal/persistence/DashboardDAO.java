package br.com.cds.connecta.portal.persistence;

import org.springframework.stereotype.Repository;

import br.com.cds.connecta.portal.entity.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DashboardDAO extends JpaRepository<Dashboard, Long>,
        JpaSpecificationExecutor<Dashboard> {

    @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.sections s "
    		+ "LEFT JOIN FETCH d.publicDashboard WHERE d.id = :id "
            + "AND UPPER(d.domain) = UPPER(:domain)")
    Dashboard findOneWithSections(@Param("id") Long id, @Param("domain") String domain);
    
    @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.sections s "
    		+ "LEFT JOIN FETCH d.publicDashboard public WHERE d.id = :id "
            + "AND d.isPublic = true")
    Dashboard findOneWithSectionsPublic(@Param("id") Long id);
    
    @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.sections s "
    		+ "LEFT JOIN FETCH d.publicDashboard public WHERE public.id = :key "
    		+ "AND d.isPublic = true")
    Dashboard findOneByPublicKey(@Param("key") Long publicKeyId);
    
    @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.sections s "
    		+ "LEFT JOIN FETCH d.publicDashboard public WHERE d.id = :id "
    		+ "AND d.isPublic = true "
            + "AND public.id = :key")
    Dashboard findOneByPublicKeyAndId(@Param("id") Long id, @Param("key") Long publicKey);

    @Query("FROM Dashboard d WHERE TRANSLATE(TRIM(UPPER(d.name)),"
            + "'ÁÂÀÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜÇ','AAAAAEEEEEIIIIIOOOOOUUUUUC') LIKE :name")
    Page<Dashboard> findAllLikeName(@Param("name") String name, Pageable pageable);

    @Query("FROM Dashboard d WHERE UPPER(d.domain) = UPPER(:domain)")
    Page<Dashboard> findByDomain(@Param("domain") String domain, Pageable pageable);

    @Query("FROM Dashboard d WHERE UPPER(d.domain) = UPPER(:domain) AND TRANSLATE(TRIM(UPPER(d.name)),"
            + "'ÁÂÀÃÄÉÈÊẼËÍÌÎĨÏÓÒÔÕÖÚÙÛŨÜÇ','AAAAAEEEEEIIIIIOOOOOUUUUUC') LIKE :name")
    Page<Dashboard> findByDomainLikeName(@Param("domain") String domain,
            @Param("name") String name, Pageable pageable);

}
