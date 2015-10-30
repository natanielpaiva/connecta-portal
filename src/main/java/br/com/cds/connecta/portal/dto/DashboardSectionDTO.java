package br.com.cds.connecta.portal.dto;


import java.util.List;

/**
 * 
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class DashboardSectionDTO {
    private Long id;
    private String name;
    private Short order;
    private List<DashboardItemDTO> items;

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

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
    }

    public List<DashboardItemDTO> getItems() {
        return items;
    }

    public void setItems(List<DashboardItemDTO> items) {
        this.items = items;
    }
    
}
