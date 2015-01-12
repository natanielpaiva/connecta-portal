package br.com.cds.portal.service.rest.dto;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;

public class ApplicationConfiguration extends AbstractBaseEntity {

    private String name;
    private String title;
    private String host;

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

    @Override
    public Long getId() {
        return null;
    }

}
