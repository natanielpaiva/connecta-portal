package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * 
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
@Entity
@Table(name = "TB_DASHBOARD_SECTION")
public class DashboardSection extends AbstractBaseEntity {

    @Id
    @SequenceGenerator(allocationSize = 1,
            name = "TB_DASHBOARD_SECTION_SEQ", sequenceName = "TB_DASHBOARD_SECTION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_DASHBOARD_SECTION_SEQ")
    @Column(name = "PK_DASHBOARD_SECTION")
    private Long id;

    @Column(name = "NM_SECTION")
    private String name;

    @Column(name = "NU_ORDER")
    private Short order;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_DASHBOARD_SECTION")
    private List<DashboardItem> items;

    @Override
    public Long getId() {
        return this.id;
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

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
    }

    public List<DashboardItem> getItems() {
        return items;
    }

    public void setItems(List<DashboardItem> items) {
        this.items = items;
    }
    
}
