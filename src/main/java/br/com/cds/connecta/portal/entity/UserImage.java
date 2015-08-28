package br.com.cds.connecta.portal.entity;

import br.com.cds.connecta.framework.core.entity.AbstractBaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Julio Lemes
 * @date Aug 27, 2015
 */
@Entity
@Table(name = "TB_USER_IMAGE")
@NamedQueries({
    @NamedQuery(name = "UserImage.findByUserLogin", query = "SELECT ui FROM UserImage ui WHERE ui.user.login = :login")
})
public class UserImage extends AbstractBaseEntity {

    @Id
    @SequenceGenerator(sequenceName = "SEQ_USER_IMG", name = "SEQ_USER_IMG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_IMG")
    @Column(name = "PK_USER_IMG")
    private Long id;

    @Lob
    @Column(name = "IMG_USER")
    private byte[] image;

    @Column(name = "NM_IMAGE")
    private String name;

    @OneToOne(mappedBy = "image")
    private User user;

    public UserImage() {
    }

    public UserImage(User user, byte[] image) {
        this.image = image;
        this.user = user;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
