package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.connector.obiee.service.Login;
import br.com.cds.connecta.framework.connector.obiee.service.Obiee;
import br.com.cds.connecta.portal.dto.ObieeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("obiee")
public class ObieeController {

    private static final String OBIEE_PATH = "/analytics/saw.dll?WSDL";

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody ObieeDTO obieeDTO){
        Login login = new Login();
        ResponseEntity responseEntity;

        try {
            login.login(getLocationWsdl(obieeDTO.getLocation()), obieeDTO.getUser(), obieeDTO.getPassword());
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            responseEntity = new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return responseEntity;
    }

    @RequestMapping(value = "catalog", method = RequestMethod.GET)
    public ResponseEntity getFolderObiee(@RequestParam String location, @RequestParam String user,
                                         @RequestParam String password, @RequestParam(required = false) String path) {
        Obiee obiee = new Obiee(getLocationWsdl(location), user, password, path);
        return new ResponseEntity<>(obiee.getCatalog(), HttpStatus.OK);
    }

    @RequestMapping(value = "metadata", method = RequestMethod.GET)
    public ResponseEntity getMetadataObiee(@RequestParam String location, @RequestParam String user,
                                           @RequestParam String password, @RequestParam String path) {
        Obiee obiee = new Obiee(getLocationWsdl(location), user, password, path);
        return new ResponseEntity<>(obiee.getMetadata(), HttpStatus.OK);
    }

    private String getLocationWsdl(String location){
        int index = location.length() - 1;

        if(location.charAt(index) == '/'){
            location = location.substring(0, index);
        }

        location += OBIEE_PATH;
        return location;
    }

}
