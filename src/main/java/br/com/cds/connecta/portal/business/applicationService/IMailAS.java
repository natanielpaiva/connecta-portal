package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.User;

public interface IMailAS  {

	public void sendConfirmationEmail(User user);

}
