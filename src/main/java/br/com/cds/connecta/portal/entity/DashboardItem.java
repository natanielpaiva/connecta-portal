package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.portal.domain.DashboardItemType;
import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "TB_DASHBOARD_ITEM")
public class DashboardItem extends AbstractBaseEntity {

    @Id
    @SequenceGenerator(allocationSize = 1,
            name = "TB_DASHBOARD_ITEM_SEQ", sequenceName = "TB_DASHBOARD_ITEM_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_DASHBOARD_ITEM_SEQ")
    @Column(name = "PK_DASHBOARD_ITEM")
    private Long id;
    
    @Column(name = "URL_VIEWER")
    private String viewerUrl;

    @Column(name = "NU_CONTENT_HEIGHT")
    private Short contentHeight;
    
    @Column(name = "NU_CONTENT_WIDTH")
    private Short contentWidth;
    
    @Column(name = "NU_CONTENT_POSITION_X")
    private Short contentPositionX;
    
    @Column(name = "NU_CONTENT_POSITION_Y")
    private Short contentPositionY;
    
    @Column(name = "NU_SIZE_X")
    private Short sizeX;
    
    @Column(name = "NU_SIZE_Y")
    private Short sizeY;
    
    @Column(name = "NU_ROW")
    private Short row;
    
    @Column(name = "NU_COLUMN")
    private Short column;
    
    @Column(name = "DS_BACKGROUND_COLOR")
    private String backgroundColor;
    
    @Lob
    @Column(name = "BN_BACKGROUND_IMAGE")
    private Byte[] backgroundImage;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ST_ITEM_TYPE")
    private DashboardItemType type;

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViewerUrl() {
        return viewerUrl;
    }

    public void setViewerUrl(String viewerUrl) {
        this.viewerUrl = viewerUrl;
    }

    public Short getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(Short contentHeight) {
        this.contentHeight = contentHeight;
    }

    public Short getContentWidth() {
        return contentWidth;
    }

    public void setContentWidth(Short contentWidth) {
        this.contentWidth = contentWidth;
    }

    public Short getContentPositionX() {
        return contentPositionX;
    }

    public void setContentPositionX(Short contentPositionX) {
        this.contentPositionX = contentPositionX;
    }

    public Short getContentPositionY() {
        return contentPositionY;
    }

    public void setContentPositionY(Short contentPositionY) {
        this.contentPositionY = contentPositionY;
    }

    public Short getSizeX() {
        return sizeX;
    }

    public void setSizeX(Short sizeX) {
        this.sizeX = sizeX;
    }

    public Short getSizeY() {
        return sizeY;
    }

    public void setSizeY(Short sizeY) {
        this.sizeY = sizeY;
    }

    public Short getRow() {
        return row;
    }

    public void setRow(Short row) {
        this.row = row;
    }

    public Short getColumn() {
        return column;
    }

    public void setColumn(Short column) {
        this.column = column;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public DashboardItemType getType() {
        return type;
    }

    public void setType(DashboardItemType type) {
        this.type = type;
    }

}
