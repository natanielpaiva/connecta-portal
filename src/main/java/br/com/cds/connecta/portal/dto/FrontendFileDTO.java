package br.com.cds.connecta.portal.dto;

import java.util.Date;

/**
 * DTO específico de arquivos que tem que fazer upload por base64 sem perder informações
 * 
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class FrontendFileDTO {
    private String name;
    private Long size;
    private String type;
    private Date lastModifiedDate;
    private String base64;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
