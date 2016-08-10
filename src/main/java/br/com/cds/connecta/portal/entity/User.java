package br.com.cds.connecta.portal.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Nataniel Paiva
 * @date Feb 12, 2016
 */
@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_USER")
    private Long id;

//    @Column(name = "DS_LOGIN", unique = true)
//    private String login;

    @Column(name = "DS_EMAIL", unique = true)
    private String email;

    @Column(name = "NM_USER")
    private String name;

    @Column(name = "DS_PASSWORD")
    private String password;
//
//    @Column(name = "URL_IMAGE")
//    private String imageUrl;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "BN_IMAGE")
    private byte[] image;

    @Column(name = "NB_FACEBOOK_ID")
    private Long facebookId;

    @Column(name = "DS_GOOGLE_TOKEN")
    private String googleToken;

    @Column(name = "DS_LANGUAGE")
    private String language;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TA_USER_ROLE",
            joinColumns = {
                @JoinColumn(name = "FK_USER")},
            inverseJoinColumns = {
                @JoinColumn(name = "FK_ROLE")})
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TA_USER_DOMAIN",
            joinColumns = {
                @JoinColumn(name = "FK_USER")},
            inverseJoinColumns = {
                @JoinColumn(name = "FK_DOMAIN")})
    private List<Domain> domains;


    public User() {
    }

    public User(String username) {
        this.email = username;
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.domains = user.getDomains();
        this.language = user.getLanguage();
    }

    public void mergePropertiesProfile(User newUser) {
        this.name = newUser.getName();
        this.email = newUser.getEmail();
        this.language = newUser.getLanguage();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
