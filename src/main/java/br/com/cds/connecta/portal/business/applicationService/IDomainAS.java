package br.com.cds.connecta.portal.business.applicationService;

import java.util.List;

import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;

/**
 * 
 * @author heloisa
 */
public interface IDomainAS {

    Domain save(Domain domain);
    
    List<Domain> getAll();

    List<Domain> getByUser(String username);

    Domain get(Long id);

    Domain getById(Long id);

    List<User> getParticipants(Long id);
    
    Domain update(Domain domain);

    Domain removeUser(Long idDomain, Long idUser);

    void delete(Long id);
}
