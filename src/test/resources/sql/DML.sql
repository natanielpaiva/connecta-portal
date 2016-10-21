-- Applications
INSERT INTO CONNECTA.TB_APPLICATION (ID, NAME, TITLE, HOST)
    VALUES
(1, 'presenter', 'Presenter', 'http://localhost:7001/presenter2-dev'),
(2, 'collector', 'Collector', 'http://localhost:7001/collector2-dev'),
(3, 'maps4',     'Maps4',     'http://localhost:7001/maps4-dev'     );

-- Aplicação a ser deletada
INSERT INTO CONNECTA.TB_APPLICATION (ID, NAME, TITLE, HOST)
    VALUES (99, 'dummy', 'Dummy', 'http://lemonparty.org/');

-- Dashboards
INSERT INTO CONNECTA.TB_DASHBOARD (PK_DASHBOARD, NM_DASHBOARD, NU_ROW_HEIGHT, NU_MAX_ROWS, NU_COLUMNS, NU_PADDING, NU_SECTION_TRANS_INT, NU_SECTION_TRANS_DUR, DS_BACKGROUND_COLOR, BN_BACKGROUND_IMAGE, ST_DISPLAY_MODE, ST_DISPLAY_SECTION_ANIM)
    VALUES (10, 'Painél antigo', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'HORIZONTAL', 'FADE');
INSERT INTO CONNECTA.TB_DASHBOARD_SECTION (PK_DASHBOARD_SECTION, FK_DASHBOARD)
    VALUES (10, 10);
INSERT INTO CONNECTA.TB_DASHBOARD_ITEM (PK_DASHBOARD_ITEM, FK_DASHBOARD_SECTION)
    VALUES (10, 10);

-- Aplicação a ser deletada
INSERT INTO CONNECTA.TB_DASHBOARD (PK_DASHBOARD, NM_DASHBOARD, NU_ROW_HEIGHT, NU_MAX_ROWS, NU_COLUMNS, NU_PADDING, NU_SECTION_TRANS_INT, NU_SECTION_TRANS_DUR, DS_BACKGROUND_COLOR, BN_BACKGROUND_IMAGE, ST_DISPLAY_MODE, ST_DISPLAY_SECTION_ANIM)
    VALUES (99, 'Dashboard deletado', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'HORIZONTAL', 'FADE');

INSERT INTO CONNECTA.TB_DASHBOARD_SECTION (PK_DASHBOARD_SECTION, FK_DASHBOARD)
    VALUES (99, 99);

INSERT INTO CONNECTA.TB_DASHBOARD_ITEM (PK_DASHBOARD_ITEM, FK_DASHBOARD_SECTION)
    VALUES (99, 99);

-- INSERT INTO CONNECTA.TB_APPLICATION_CONFIG (CONFIG_PARAM, CONFIG_VALUE)
-- VALUES('AUTH_PROVIDER_URL', 'http://localhost:8080/camunda');


-- OAUTH2
INSERT INTO CONNECTA.TB_USER (PK_USER, DS_EMAIL, NM_USER)
        VALUES(10, 'user', 'pass');
-- Usuário a ser atualizado
--DS_PASSWORD = 123
INSERT INTO CONNECTA.TB_USER (PK_USER, DS_EMAIL, NM_USER, DS_PASSWORD)
        VALUES(11, 'cds', '123', '$2a$11$36rkYO356PeEGgP4mtCDDOdlL5D0CECcZp.5m.bvRIg3JgbHt9.fS');
--Usuário a ser alterado a senha
--DS_PASSWORD = 123
INSERT INTO CONNECTA.TB_USER (PK_USER, DS_EMAIL, NM_USER, DS_PASSWORD)
        VALUES(12, 'ednaldopereira', '123', '$2a$11$36rkYO356PeEGgP4mtCDDOdlL5D0CECcZp.5m.bvRIg3JgbHt9.fS');

--Usuario convidado, ainda não confirmado
INSERT INTO CONNECTA.TB_USER (PK_USER, DS_EMAIL, DS_HASH_INVITED)
        VALUES(13, 'abc@cds.com.br', '3d47ffb4-3e68-432e-86c2-f3d232674d0a');

INSERT INTO CONNECTA.TB_ROLE (PK_ROLE, NM_ROLE)
        VALUES(10, 'ROLE_USER');

INSERT INTO CONNECTA.TA_USER_ROLE(FK_USER, FK_ROLE)
        VALUES(10,10);

-- DomainService
INSERT INTO CONNECTA.TB_DOMAIN(PK_DOMAIN, NM_DOMAIN)
    VALUES(99, 'New Domain');

INSERT INTO CONNECTA.TB_DOMAIN(PK_DOMAIN, NM_DOMAIN)
    VALUES(100, 'Novo dominio');

INSERT INTO CONNECTA.TB_DOMAIN(PK_DOMAIN, NM_DOMAIN)
    VALUES(101, 'Novo dominio');