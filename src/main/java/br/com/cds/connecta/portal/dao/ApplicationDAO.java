/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.dao;

import br.com.cds.connecta.framework.core.persistence.jpa.common.AbstractBaseJpaDAO;
import br.com.cds.connecta.portal.entity.Application;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pires
 */
@Repository
public class ApplicationDAO extends AbstractBaseJpaDAO<Application> {
    
    public List<Application> list() {
        Query query = getEntityManager().createNamedQuery("Application.findAll");
        return query.getResultList();
    }
    
}
