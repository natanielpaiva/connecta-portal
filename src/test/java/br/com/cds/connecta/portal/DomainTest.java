/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.entity.Domain;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author allex
 */
public class DomainTest extends BaseTest {

    @Autowired
    private IDomainAS service;

    @Test
    public void saveDomain() {
        Domain domain = new Domain();
        domain.setName("novo Dominio");

        Domain save = service.save(domain);

        assertThat(save.getId(), greaterThan(0L));
        assertThat(save.getName(), equalTo("novo Dominio"));
    }

    @Test
    public void saveEquals() {
        Domain domain = new Domain();

        domain.setName("algo aleatório");
        Domain save = service.save(domain);

        Domain domainEquals = new Domain();
        domainEquals.setName("algo aleatório");
        Domain saveEquals = service.save(domainEquals);
    }

    @Test
    public void updateDomain() {
        Domain domain = new Domain();
        domain.setName("Um nome qualquer");
        Domain save = service.save(domain);

        assertThat(save.getName(), equalTo("Um nome qualquer"));
        domain.setName("another name");
        service.update(domain);

        assertThat(save.getName(), equalTo("another name"));

    }

//    /**
//     *
//     */
    @Test
    public void deleteDomain() {
        service.delete(99L);
        Domain domain = new Domain();
        domain.setName("New Domain");
        Domain save = service.save(domain);
        assertThat(save.getName(), equalTo("New Domain"));

    }
}
