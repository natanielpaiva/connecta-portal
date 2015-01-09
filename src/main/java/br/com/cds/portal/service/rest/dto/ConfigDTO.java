package br.com.cds.portal.service.rest.dto;

import br.com.cds.framework.core.services.dto.AbstractDTO;
import java.util.ArrayList;
import java.util.List;

public class ConfigDTO extends AbstractDTO {

    private List<ConfigApplicationDTO> applications = new ArrayList<ConfigApplicationDTO>();

    public List<ConfigApplicationDTO> getApplications() {
        return applications;
    }

    public void setApplications(List<ConfigApplicationDTO> applications) {
        this.applications = applications;
    }

    public void addApplication(ConfigApplicationDTO application) {
        this.applications.add(application);
    }

}
