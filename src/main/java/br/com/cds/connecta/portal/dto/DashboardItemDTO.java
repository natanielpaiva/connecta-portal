package br.com.cds.connecta.portal.dto;

import br.com.cds.connecta.portal.domain.DashboardItemType;

public class DashboardItemDTO {
    
    private Long id;
    private String viewerUrl;
    private Short contentHeight;
    private Short contentWidth;
    private Short contentPositionX;
    private Short contentPositionY;
    private Short sizeX;
    private Short sizeY;
    private Short row;
    private Short column;
    private Short opacity;
    private String backgroundColor;

    private FrontendFileDTO backgroundImage;
    
    private DashboardItemType type;

    public Long getId() {
        return id;
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

    public Short getOpacity() {
        return opacity;
    }

    public void setOpacity(Short opacity) {
        this.opacity = opacity;
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

    public FrontendFileDTO getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(FrontendFileDTO backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public DashboardItemType getType() {
        return type;
    }

    public void setType(DashboardItemType type) {
        this.type = type;
    }

    
}
