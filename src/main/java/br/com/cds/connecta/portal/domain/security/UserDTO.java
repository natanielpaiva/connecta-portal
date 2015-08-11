package br.com.cds.connecta.portal.domain.security;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserDTO {

  protected UserProfileDTO profile;
  protected UserCredentialsDTO credentials;
  
  public UserProfileDTO getProfile() {
    return profile;
  }

  public void setProfile(UserProfileDTO profile) {
    this.profile = profile;
  }

  public UserCredentialsDTO getCredentials() {
    return credentials;
  }

  public void setCredentials(UserCredentialsDTO credentials) {
    this.credentials = credentials;
  }

}