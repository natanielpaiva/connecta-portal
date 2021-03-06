package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.domain.annotation.PublicResource;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IDashboardAS;
import br.com.cds.connecta.portal.dto.DashboardDTO;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PublicResource
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private IDashboardAS service;

    @RequestMapping(value = "viewer", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>>
            searchViewers(@RequestParam(value = "text", defaultValue = "") String text,
                    @RequestHeader("Domain") String domain) {

        List<Map<String, Object>> viewers = service.searchViewers(text, domain);
        return new ResponseEntity<>(viewers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Dashboard> get(@PathVariable("id") Long id,
            @RequestHeader("Domain") String domain) {
        Dashboard entity = service.get(id, domain);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
    
    @RequestMapping(value = "public/{id}", method = RequestMethod.GET)
    public ResponseEntity<Dashboard> getPublic(@PathVariable("id") Long id) {
        Dashboard entity = service.getPublic(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Dashboard>> list(DashboardPaginationFilter filter,
            @RequestHeader("Domain") String domain) {
        filter.setDomain(domain);
        return new ResponseEntity<>(service.list(filter), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Dashboard> save(@RequestBody DashboardDTO dashboard) {
        Dashboard entity = service.save(dashboard);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Dashboard> update(
            @PathVariable("id") Long id,
            @RequestBody DashboardDTO entity) {

        Dashboard updatedEntity = service.update(entity);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity bulkDelete(@RequestBody List<Long> ids,
            @RequestHeader("Domain") String domain) {
        service.deleteAll(ids, domain);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="public/publicKey/validate", method = RequestMethod.GET)
    public ResponseEntity validatePublicKey(@RequestParam Long key, @RequestParam Long viewerId) {
        if(Util.isNotNull(key)){
        	if(!service.validatePublicKey(key, viewerId)){
        		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        	}
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="public/publicKey/validateDash", method = RequestMethod.GET)
    public ResponseEntity validatePublicKeyDash(@RequestParam Long key, @RequestParam Long dashId) {
        if(Util.isNotNull(key)){
        	if(!service.validatePublicKeyDash(key, dashId)){
        		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        	}
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
