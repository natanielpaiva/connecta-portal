package br.com.cds.connecta.portal.business.applicationService.impl;


import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import br.com.cds.connecta.portal.business.applicationService.IMailAS;
import br.com.cds.connecta.portal.entity.User;

@Service
public class MailAS implements IMailAS {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	private static final String FROM_EMAIL = "no-reply@connecta.io";

	@Override
	public void sendConfirmationEmail(final User user) {
		MimeMessagePreparator preparator = createMessage(user, "Confirmação de Cadastro", 
				"/mail/registrationConfirmation.vm");
        //TODO tratar erros && criar entidade para o Email caso o servidor 
        // smtp esteja fora, para posterior envio
        this.mailSender.send(preparator);
	}

	private MimeMessagePreparator createMessage(final User user, final String subject, final String template) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setFrom(FROM_EMAIL);
                message.setSubject(subject);
                Map<String, Object> model = new HashMap<>();
                model.put("user", user);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, template , "UTF-8", model);
                message.setText(text, true);
            }
        };
		return preparator;
	}

}
