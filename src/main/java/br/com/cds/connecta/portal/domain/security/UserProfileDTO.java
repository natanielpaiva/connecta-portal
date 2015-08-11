package br.com.cds.connecta.portal.domain.security;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserProfileDTO {

  protected String id;
  protected String firstName;
  protected String lastName;
  protected String email;
    
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
}