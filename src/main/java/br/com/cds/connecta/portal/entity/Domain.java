package br.com.cds.connecta.portal.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TB_DOMAIN")
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_DOMAIN")
    private Long id;

    @NotEmpty
    @Column(name = "NM_DOMAIN")
    private String name;

    @ManyToMany(mappedBy="domains", 
    		fetch = FetchType.LAZY, 
    		cascade = CascadeType.REMOVE)
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> user) {
        this.users = user;
    }
    
    @PreRemove
    private void removeDomainsFromUsers(){
    	for(User u : users){
    		u.getDomains().remove(this);
    	}
    	this.getUsers().clear();
    }

}
