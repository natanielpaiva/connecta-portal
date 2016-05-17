use portal;
INSERT INTO TB_USER (PK_USER, DS_EMAIL, LN_USER, DS_LOGIN, NM_USER, DS_PASSWORD, URL_IMAGE)
VALUES (1, "connecta.csds.com.br","User", "connecta", "User", "connecta", "http://www.cds.com.br/img/logo-cds.png");

INSERT INTO TB_ROLE (PK_ROLE, NM_ROLE) values (1, "ROLE_USER");

INSERT INTO TA_USER_ROLE (FK_USER, FK_ROLE) values (1,1);

INSERT INTO TB_DOMAIN (PK_DOMAIN, NM_DOMAIN) values (1, "cds");

INSERT INTO TA_USER_DOMAIN (FK_USER, FK_DOMAIN) values (1,1);
