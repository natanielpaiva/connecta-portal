/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author pires
 */
@Entity
@Table(name = "C_APPLICATIONS")
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "FROM Application a ORDER BY a.id")
})
public class Application extends AbstractBaseEntity implements Serializable {

    @Id
    @SequenceGenerator(
        sequenceName="SEQ_APPLICATION",
        name="SEQ_APPLICATION",
        initialValue=1,
        allocationSize=1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_APPLICATION")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "HOST")
    private String host;

    @Override
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
