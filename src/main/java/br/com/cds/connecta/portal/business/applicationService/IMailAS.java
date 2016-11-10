package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.dto.InviteRequestDTO;
import java.util.List;

public interface IMailAS {

    void sendConfirmationEmail(User user);

    void sendInvite(InviteRequestDTO inviteVO, List<String> emails);

    void sendRecovery(User user, String url);

    void sendRememberInvite(User user, String url);

}
