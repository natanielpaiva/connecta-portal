package br.com.cds.connecta.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import java.security.Principal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("domain")
public class DomainController {

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

        domain = domainAS.save(domain);
        User user = userAS.get(userLogged);
        user.getDomains().add(domain);
        userAS.update(user);

        return new ResponseEntity(domain, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateDomain(@PathVariable("id") Long id, @RequestBody Domain domain) {

        Domain save = domainAS.update(domain);
        return new ResponseEntity(domain, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDomain(@PathVariable("id") Long id) {

        domainAS.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> getDomainsByUsername(@RequestParam("email") String email) {
        Iterable<Domain> listDomains = domainAS.getByUser(email);
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }

}
