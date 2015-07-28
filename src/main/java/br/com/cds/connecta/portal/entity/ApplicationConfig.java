package br.com.cds.connecta.portal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Julio Lemes
 * @date Jul 27, 2015
 */
@Entity
@Table(name = "TB_APPLICATION_CONFIG")
@NamedQueries({
    @NamedQuery(name = "ApplicationConfig.findByName", query = "SELECT p FROM ApplicationConfig p WHERE p.param = :param")
})
public class ApplicationConfig implements Serializable {

    @Id
    @Column(name = "CONFIG_PARAM")
    private String param;

    @Column(name = "CONFIG_VALUE")
    private String value;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
