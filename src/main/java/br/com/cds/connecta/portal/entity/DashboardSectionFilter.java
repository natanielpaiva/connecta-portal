package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SECTION_FILTER")
public class DashboardSectionFilter extends AbstractBaseEntity {

    /**
     * ID do filtro a ser buscado no Connecta Presenter
     */
    @Id
    @Column(name="PK_SECTION_FILTER")
    private Long id;

    @Column(name = "ST_ACTIVE")
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
