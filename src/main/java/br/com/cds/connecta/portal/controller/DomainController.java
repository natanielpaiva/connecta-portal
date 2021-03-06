package br.com.cds.connecta.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IMailAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.dto.InviteRequestDTO;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("domain")
public class DomainController {

    @Autowired
    private IMailAS mailAS;
    @Autowired
    private IDomainAS domainAS;
    @Autowired
    private IUserAS userAS;

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<Iterable<Domain>> getAll() {
//        Iterable<Domain> listDomains = domainAS.getAll();
//        return new ResponseEntity<>(listDomains, HttpStatus.OK);
//    }
    /**
     *
     * @param domain
     * @param userLogged
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDomain(@RequestBody Domain domain, Principal userLogged) throws Exception {

        User user = userAS.get(userLogged);
        Domain newDomain = domainAS.save(domain);

        user.getDomains().add(newDomain);
        userAS.update(user);

        return new ResponseEntity(newDomain, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateDomain(@PathVariable("id") Long id, @RequestBody Domain domain) {

        Domain save = domainAS.update(domain);
        return new ResponseEntity(save, HttpStatus.OK);
    }
    
    @RequestMapping(value = "domain/{id}", method = RequestMethod.PUT)
    public ResponseEntity removeUser(@PathVariable("id") Long idDomain, @RequestBody Long idUser) {
        userAS.removeDomain(idUser,idDomain);
        
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDomain(@PathVariable("id") Long id) {

        domainAS.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Domain> getById(@PathVariable("id") Long id) {
    	Domain domain = domainAS.get(id);
    	return new ResponseEntity<>(domain, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> getDomainsByUsername(@RequestParam("email") String email) {
        Iterable<Domain> listDomains = domainAS.getByUser(email);
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }
    
    @RequestMapping(value="participants", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getParticipants(@RequestParam("id") Long id) {
        Iterable<User> listUsers = domainAS.getParticipants(id);
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "invite", method = RequestMethod.POST)
    public ResponseEntity inviteUser(@RequestBody List<String> emails,
            @RequestParam("idDomain") Long idDomain,
            @RequestParam("url") String url,
            Principal userLogged) {
       
        InviteRequestDTO i = new InviteRequestDTO();
        i.setDomain(domainAS.get(idDomain));
        i.setSender(userAS.get(userLogged).getName());
        i.setUrl(url);
        mailAS.sendInvite(i, emails);

        return new ResponseEntity(HttpStatus.OK);
    }

}
