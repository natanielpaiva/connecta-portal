//package br.com.cds.connecta.portal.business.applicationService;
//
//import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
//import br.com.cds.connecta.portal.domain.security.UserCredentialsDTO;
//import br.com.cds.connecta.portal.domain.security.UserDTO;
//import br.com.cds.connecta.portal.domain.security.UserProfileDTO;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// *
// * @author Julio Lemes
// * @date Aug 10, 2015
// */
//public interface IUserAS {
//    
//    AuthenticationDTO saveUser(UserDTO user) throws Exception;
//    
//    UserDTO saveOrUpdateWithUpload(UserDTO user, MultipartFile image) throws Exception;
//    
//    void updatePassword(String userId, UserCredentialsDTO credentials);
//    
//    void updateUser(UserProfileDTO user);
//    
//    void deleteUser(String userId);
//    
//    byte[] getUserImage(String username);
//    
//    String generateAvatarUrl(String userId, String email, AuthenticationDTO authDTO);
//    
//}
