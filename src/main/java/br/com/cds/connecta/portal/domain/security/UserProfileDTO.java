package br.com.cds.connecta.portal.domain.security;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserProfileDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private SocialTokenType tokenType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SocialTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(SocialTokenType tokenType) {
        this.tokenType = tokenType;
    }

}
