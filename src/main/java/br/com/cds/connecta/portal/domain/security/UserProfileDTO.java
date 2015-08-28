package br.com.cds.connecta.portal.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setUserId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
