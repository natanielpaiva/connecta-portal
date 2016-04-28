package br.com.cds.connecta.portal.business.applicationService;

import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.portal.entity.User;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public interface IUserAS {
    User get(User user);
    User saveOrUpdateWithUpload(User user, MultipartFile image) throws Exception;
}
