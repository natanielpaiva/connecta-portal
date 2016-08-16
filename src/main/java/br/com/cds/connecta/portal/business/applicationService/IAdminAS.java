package br.com.cds.connecta.portal.business.applicationService;

import java.util.List;

import br.com.cds.connecta.portal.entity.Domain;

public interface IAdminAS {

    List<Domain> getAll();
    List<Domain> getByUsername(String username);

}
