package br.com.cds.connecta.portal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
}
