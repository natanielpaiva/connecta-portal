package br.com.cds.connecta.portal.dto;

public class DashboardPublicDTO {
    private Long id;
    private byte[] publicKey;
    private byte[] privateKeyEncodedString;

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
