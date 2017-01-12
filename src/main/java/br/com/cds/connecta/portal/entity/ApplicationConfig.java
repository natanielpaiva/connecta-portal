package br.com.cds.connecta.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Julio Lemes
 * @date Jul 27, 2015
 */
@Entity
@Table(name = "TB_APPLICATION_CONFIG")
public class ApplicationConfig implements Serializable {

    @Id
    @Column(name = "CONFIG_PARAM")
    private String param;

    @Column(name = "CONFIG_VALUE")
    private String value;
    
    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "BN_IMAGE")
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
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