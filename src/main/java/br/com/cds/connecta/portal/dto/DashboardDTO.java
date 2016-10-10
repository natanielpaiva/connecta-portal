package br.com.cds.connecta.portal.dto;

import java.util.List;

import br.com.cds.connecta.portal.domain.DashboardDisplayMode;
import br.com.cds.connecta.portal.domain.DashboardSectionAnimation;

public class DashboardDTO {
    private Long id;
    private String name;
    private Short rowHeight;
    private Short maxRows;
    private Short columns;
    private Short padding;
    private Short opacity;
    private Short sectionTransitionInterval;
    private Short sectionTransitionDuration;
    private String backgroundColor;
    
    private FrontendFileDTO backgroundImage;
    
    private DashboardDisplayMode displayMode;
    private DashboardSectionAnimation sectionTransitionAnimation;
    private List<DashboardSectionDTO> sections;
    private String domain;
    private boolean isPublic;
    private DashboardPublicDTO publicDashboard;

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

    public Short getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Short rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Short getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(Short maxRows) {
        this.maxRows = maxRows;
    }

    public Short getColumns() {
        return columns;
    }

    public void setColumns(Short columns) {
        this.columns = columns;
    }

    public Short getOpacity() {
        return opacity;
    }

    public void setOpacity(Short opacity) {
        this.opacity = opacity;
    }

    public Short getPadding() {
        return padding;
    }

    public void setPadding(Short padding) {
        this.padding = padding;
    }

    public Short getSectionTransitionInterval() {
        return sectionTransitionInterval;
    }

    public void setSectionTransitionInterval(Short sectionTransitionInterval) {
        this.sectionTransitionInterval = sectionTransitionInterval;
    }

    public Short getSectionTransitionDuration() {
        return sectionTransitionDuration;
    }

    public void setSectionTransitionDuration(Short sectionTransitionDuration) {
        this.sectionTransitionDuration = sectionTransitionDuration;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public FrontendFileDTO getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(FrontendFileDTO backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public DashboardDisplayMode getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(DashboardDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public DashboardSectionAnimation getSectionTransitionAnimation() {
        return sectionTransitionAnimation;
    }

    public void setSectionTransitionAnimation(DashboardSectionAnimation sectionTransitionAnimation) {
        this.sectionTransitionAnimation = sectionTransitionAnimation;
    }

    public List<DashboardSectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<DashboardSectionDTO> sections) {
        this.sections = sections;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public DashboardPublicDTO getPublicDashboard() {
		return publicDashboard;
	}

	public void setPublicDashboard(DashboardPublicDTO publicDashboard) {
		this.publicDashboard = publicDashboard;
	}

}
