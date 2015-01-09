package br.com.cds.portal.service.rest.dto;

import br.com.cds.framework.core.services.dto.AbstractDTO;

public class ConfigApplicationDTO extends AbstractDTO {

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

}
