package br.com.cds.portal.service.rest.dto;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import java.util.ArrayList;
import java.util.List;

public class Configuration extends AbstractBaseEntity {

    private List<ApplicationConfiguration> applications = new ArrayList<ApplicationConfiguration>();

    public List<ApplicationConfiguration> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationConfiguration> applications) {
        this.applications = applications;
    }

    public void addApplication(ApplicationConfiguration application) {
        this.applications.add(application);
    }

    @Override
    public Long getId() {
        return null;
    }

}
