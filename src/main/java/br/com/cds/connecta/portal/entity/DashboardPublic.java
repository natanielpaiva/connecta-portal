package br.com.cds.connecta.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;

@Entity
@Table(name = "TB_DASHBOARD_PUBLIC")
public class DashboardPublic extends AbstractBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PUBLIC_DASHBOARD = "publicDashboard";
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private byte[] publicKey;
    
    @Column(updatable=false, nullable=false)
    @JsonIgnore
    private byte[] privateKeyEncodedString;
    
    @Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public byte[] getPrivateKeyEncodedString() {
		return privateKeyEncodedString;
	}

	public void setPrivateKeyEncodedString(byte[] privateKeyEncodedString) {
		this.privateKeyEncodedString = privateKeyEncodedString;
	}

}
