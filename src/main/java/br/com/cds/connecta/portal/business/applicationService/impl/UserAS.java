package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.MessageEnum;
import static br.com.cds.connecta.framework.core.util.Util.isNull;
import static br.com.cds.connecta.framework.core.util.Util.isNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.RoleDAO;
import br.com.cds.connecta.portal.persistence.UserRepository;
import br.com.cds.connecta.portal.persistence.specification.RoleSpecification;
import br.com.cds.connecta.portal.security.UserRepositoryUserDetails;
import br.com.cds.connecta.portal.vo.InviteRequestVO;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 *
 * @author <heloisa.morais@cds.com.br>
 */
@Service
public class UserAS implements IUserAS {

    private final static String DEFAULT_USER_IMAGE = "./user-default.jpg";

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IDomainAS domainAS;

    @Autowired
    private RoleDAO roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User get(Long id) {
        User user = userRepository.findOne(id);

        if (isNull(user)) {
            throw new ResourceNotFoundException(User.class.getCanonicalName());
        }

        return user;
    }

    @Override
    public User get(Principal user) {
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) user;
        UserRepositoryUserDetails repositoryUserDetails
                = (UserRepositoryUserDetails) auth2Authentication.getPrincipal();

        return getByEmail(repositoryUserDetails.getUser().getEmail());
    }

    @Override
    public List<User> getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getByEmail(String username) {
        User user = userRepository.findByEmail(username);

        if (isNull(user)) {
            throw new ResourceNotFoundException(User.class.getCanonicalName());
        }

        return user;
    }
    
    @Override
    public User getByHash(String hash) {
        User user = userRepository.findByHash(hash);

        if (isNull(user)) {
            throw new ResourceNotFoundException(User.class.getCanonicalName());
        }

        return user;
    }
    
    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public boolean isAvailableEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (isNotNull(user)) {
            throw new AlreadyExistsException(User.class.getSimpleName(), "email");
        }

        return true;
    }

    @Override
    public InputStream getUserImage(Long id) throws IOException {
        User user = get(id);

        InputStream is;

        if (isNull(user.getImage())) {
            is = getClass().getClassLoader().getResourceAsStream(DEFAULT_USER_IMAGE);
        } else {
            Hibernate.initialize(user.getImage());
            is = new ByteArrayInputStream(user.getImage());
        }

        return is;
    }

    @Override
    public void setUserImage(Long id) throws IOException {
        User user = get(id);
        user.setImage(null);
    }

    @Override
    public User upload(Long id, MultipartFile file) throws IOException {
        User user = get(id);

        if (isNull(file)) {
            user.setImage(null);
        } else {
            user.setImage(file.getBytes());
        }

        return userRepository.save(user);
    }

    @Override
    public User save(User user) {

//        Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));
//        user.setRoles(Arrays.asList(roleUsr));
        user.setRoles(new ArrayList<Role>());
        user.getRoles().add(roleRepository.findOne(RoleSpecification.byName("ROLE_USER")));

        user.setImage(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        User usr = userRepository.findByEmail(user.getEmail());

        if (Util.isNotNull(usr)) {
            throw new AlreadyExistsException("Usuário", "E-mail");
        }

        return save(user);
    }

    @Override
    public User saveInvited(User user) {
        User convite = getByEmail(user.getEmail());
                
        convite.setName(user.getName());
        convite.setPassword(user.getPassword());
        convite.setHash(null);
        
        return save(convite);
    }

    @Override
    public User saveInvite(InviteRequestVO inviteRequestVO, UUID hash) {
        User user = userRepository.findByEmail(inviteRequestVO.getReceiver());
        
        if (isNull(user)) {
            user = new User();
            user.setDomains(Arrays.asList(inviteRequestVO.getDomain()));
            user.setHash(hash.toString());
            user.setEmail(inviteRequestVO.getReceiver());
            inviteRequestVO.setUrl(inviteRequestVO.getUrl() + "?hash=" + hash.toString());
        }else if(isNotNull(user.getHash())){
            user.setHash(hash.toString());
            inviteRequestVO.setUrl(inviteRequestVO.getUrl() + "?hash=" + hash.toString());
        }
        
        if (!user.getDomains().contains(inviteRequestVO.getDomain())) {
            user.getDomains().add(inviteRequestVO.getDomain());
        }

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userFromDatabase = get(id); // Somente para verificar se já existe

        user.setId(id); // O ID da URL prevalece
        user.setImage(userFromDatabase.getImage()); // Imagem continua a anterior
        user.setPassword(userFromDatabase.getPassword()); // Senha continua a anterior
        user.setRoles(userFromDatabase.getRoles()); // Roles também
        user.setDomains(userFromDatabase.getDomains()); // E Domínios também :)

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User userLogged, String oldPass, String newPass) {
        if (!passwordEncoder.matches(oldPass, userLogged.getPassword())) {
            throw new BusinessException(MessageEnum.INTEGRITY_ERROR);
        }
        userLogged.setPassword(passwordEncoder.encode(newPass));
        return userRepository.save(userLogged);
    }
    
    @Override
    public User removeDomain(Long idUser, Long idDomain) {
        User user = get(idUser);
        user.getDomains().remove(domainAS.get(idDomain));
        
        return userRepository.save(user);
    }

}
