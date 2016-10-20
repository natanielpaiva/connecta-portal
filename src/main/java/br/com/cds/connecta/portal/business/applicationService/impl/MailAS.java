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
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.vo.InviteRequestVO;
import java.util.List;
import java.util.UUID;

@Service
public class MailAS implements IMailAS {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUserAS userAS;

    @Autowired
    private VelocityEngine velocityEngine;

    private static final String FROM_EMAIL = "no-reply@connecta.io";

    @Override
    public void sendConfirmationEmail(final User user) {

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);

        MimeMessagePreparator preparator = createMessage(model, user.getEmail(), "Confirmação de Cadastro",
                "/mail/registrationConfirmation.vm");
        //TODO tratar erros && criar entidade para o Email caso o servidor 
        // smtp esteja fora, para posterior envio
        this.mailSender.send(preparator);
    }

    private MimeMessagePreparator createMessage(final Map<String, Object> model, final String receiverEmail,
            final String subject, final String template) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(receiverEmail);
                message.setFrom(FROM_EMAIL);
                message.setSubject(subject);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, template, "UTF-8", model);
                message.setText(text, true);
            }
        };
        return preparator;
    }

    @Override
    public void sendInvite(InviteRequestVO inviteVO, List<String> emails) {

        Map<String, Object> model = new HashMap<>();

        for (String email : emails) {
            inviteVO.setReceiver(email);
            model.put("invite", inviteVO);

            userAS.saveInvite(inviteVO, UUID.randomUUID());

            MimeMessagePreparator preparator = createMessage(model, inviteVO.getReceiver(), inviteVO.getSender()
                    + " lhe convidou!", "/mail/inviteTemplate.vm");

            this.mailSender.send(preparator);
        }

    }

    @Override
    public void sendRecovery(User user, String url) {

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("url", url);

        MimeMessagePreparator preparator = createMessage(model, user.getEmail(),
                "Recovery password", "/mail/recoveryPassword.vm");

        this.mailSender.send(preparator);
    }

    @Override
    public void sendRememberInvite(User user, String url) {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("url", url);

        MimeMessagePreparator preparator = createMessage(model, user.getEmail(), "Remember invite",
                "/mail/rememberInvite.vm");
        //TODO tratar erros && criar entidade para o Email caso o servidor 
        // smtp esteja fora, para posterior envio
        this.mailSender.send(preparator);
    }

}
