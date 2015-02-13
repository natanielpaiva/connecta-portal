INSERT INTO CONNECTA.APPLICATIONS (ID, NAME, TITLE, HOST)
    VALUES
(CONNECTA.SEQ_APPLICATION.nextval, 'presenter', 'Presenter', 'http://localhost:7001/presenter2-dev'),
(CONNECTA.SEQ_APPLICATION.nextval, 'collector', 'Collector', 'http://localhost:7001/collector2-dev'),
(CONNECTA.SEQ_APPLICATION.nextval, 'maps4',     'Maps4',     'http://localhost:7001/maps4-dev'     );

-- Aplicação a ser deletada
INSERT INTO CONNECTA.APPLICATIONS (ID, NAME, TITLE, HOST)
    VALUES (99, 'dummy', 'Dummy', 'http://lemonparty.org/');