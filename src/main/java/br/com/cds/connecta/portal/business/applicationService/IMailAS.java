package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.vo.InviteRequestVO;
import java.util.List;

public interface IMailAS {

    void sendConfirmationEmail(User user);

    void sendInvite(InviteRequestVO inviteVO, List<String> emails);

    void sendRecovery(User user, String url, String email);

    void sendRememberInvite(User user, String url, String email);

}
