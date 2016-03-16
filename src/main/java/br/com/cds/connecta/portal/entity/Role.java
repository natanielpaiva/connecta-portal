package br.com.cds.connecta.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Nataniel Paiva
 */

@Entity
@Table(name = "TB_ROLE")
public class Role  implements GrantedAuthority{
    
    private static final long serialVersionUID = 1L;
            
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ROLE")
    private Long id;
    
    @NotEmpty
    @Column(name = "NM_ROLE")
    private String name;
    
    @Override
    public String getAuthority() {
        return name;
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
    
}
