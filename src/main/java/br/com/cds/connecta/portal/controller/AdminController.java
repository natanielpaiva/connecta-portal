package br.com.cds.connecta.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cds.connecta.portal.business.applicationService.IAdminAS;
import br.com.cds.connecta.portal.entity.Domain;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    
    @Autowired
    private IAdminAS adminAS;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> getAll() {
        Iterable<Domain> listDomains = adminAS.getAll();
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Domain>> 
    		getDomainsByUsername(@PathVariable("username") String username) {
        Iterable<Domain> listDomains = adminAS.getByUsername(username);
        return new ResponseEntity<>(listDomains, HttpStatus.OK);
    }
   
}
