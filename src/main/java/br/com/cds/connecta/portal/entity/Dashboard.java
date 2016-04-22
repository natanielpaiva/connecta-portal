package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import br.com.cds.connecta.portal.domain.DashboardSectionAnimation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cds.connecta.portal.domain.DashboardDisplayMode;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
@Table(name = "TB_DASHBOARD")
public class Dashboard extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_DASHBOARD")
    private Long id;

    @Column(name = "NM_DASHBOARD")
    private String name;

    @Column(name = "NU_ROW_HEIGHT")
    private Short rowHeight;

    @Column(name = "NU_MAX_ROWS")
    private Short maxRows;

    @Column(name = "NU_COLUMNS")
    private Short columns;

    @Column(name = "NU_PADDING")
    private Short padding;
    
    @Column(name = "NU_OPACITY")
    private Short opacity;

    @Column(name = "NU_SECTION_TRANS_INT")
    private Short sectionTransitionInterval;

    @Column(name = "NU_SECTION_TRANS_DUR")
    private Short sectionTransitionDuration;

    @Column(name = "DS_BACKGROUND_COLOR")
    private String backgroundColor;

    @Lob
    @Column(name = "BN_BACKGROUND_IMAGE")
    private String backgroundImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "ST_DISPLAY_MODE")
    private DashboardDisplayMode displayMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "ST_DISPLAY_SECTION_ANIM")
    private DashboardSectionAnimation sectionTransitionAnimation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_DASHBOARD")
    private List<DashboardSection> sections;
    
    @Column(name = "NM_DOMAIN")
    private String domain;

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

    public Short getPadding() {
        return padding;
    }

    public void setPadding(Short padding) {
        this.padding = padding;
    }

    public Short getOpacity() {
        return opacity;
    }

    public void setOpacity(Short opacity) {
        this.opacity = opacity;
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

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
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

    public List<DashboardSection> getSections() {
        return sections;
    }

    public void setSections(List<DashboardSection> sections) {
        this.sections = sections;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}


}
