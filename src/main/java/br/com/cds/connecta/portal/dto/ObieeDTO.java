package br.com.cds.connecta.portal.dto;


public class ObieeDTO {

    String location;
    String user;
    String password;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
