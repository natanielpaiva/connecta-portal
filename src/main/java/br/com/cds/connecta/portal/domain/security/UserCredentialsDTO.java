package br.com.cds.connecta.portal.domain.security;
/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserCredentialsDTO {

  protected String password;
  protected String authenticatedUserPassword;

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getAuthenticatedUserPassword() {
    return authenticatedUserPassword;
  }
  public void setAuthenticatedUserPassword(String authenticatedUserPassword) {
    this.authenticatedUserPassword = authenticatedUserPassword;
  }

}