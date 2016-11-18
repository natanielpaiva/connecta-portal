package br.com.cds.connecta.portal.config;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.UserRepository;

/**
 * Classe utilizada para criptografar todos as senhas dos usuarios cadastrados no banco
 * @author cleto
 *
 */
@Component
public class PasswordLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

	private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		List<User> usuarios = userRepository.findAll();

		for (User user : usuarios) {
                    if(user.getPassword() != null){
			if (!BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userRepository.save(user);
			}
                    }
		}

	}
}