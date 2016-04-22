package br.com.cds.connecta.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.entity.Domain;

@RestController
@RequestMapping("domain")
public class DomainController {
    
    @Autowired
    private IDomainAS domainAS;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> getAll() {
        Iterable<Domain> listDomains = domainAS.getAll();
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> 
    		getDomainsByUsername(@PathVariable("username") String username) {
        Iterable<Domain> listDomains = domainAS.getByUsername(username);
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }
   
}
