package br.com.cds.connecta.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "DS_LOGIN")
    private String email;

    @Column(name = "NM_USER")
    private String name;
    
    @Column(name = "LN_USER")
    private String lastName;

    @Column(name = "DS_PASSWORD")
    private String password;

    @Column(name = "URL_IMAGE")
    private String imageUrl;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "BN_IMAGE")
    private byte[] image;

    @Column(name = "NB_FACEBOOK_ID")
    private Long facebookId;

    @Column(name = "DS_GOOGLE_TOKEN")
    private String googleToken;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TA_USER_ROLE",
            joinColumns = {
                @JoinColumn(name = "FK_USER")},
            inverseJoinColumns = {
                @JoinColumn(name = "FK_ROLE")})
    private List<Role> roles;

    public User() {
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

}
