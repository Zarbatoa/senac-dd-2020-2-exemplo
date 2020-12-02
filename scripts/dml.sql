USE DBSISTEMACOVID;

# DML PARA TESTES
INSERT INTO INSTITUICAO(IDINSTITUICAO, NOME) VALUES (1, 'UFSC');
INSERT INTO INSTITUICAO(IDINSTITUICAO, NOME) VALUES (2, 'USP');
INSERT INTO INSTITUICAO(IDINSTITUICAO, NOME) VALUES (3, 'MIT');
INSERT INTO INSTITUICAO(IDINSTITUICAO, NOME) VALUES (4, 'Universidade de Moscow');
INSERT INTO INSTITUICAO(IDINSTITUICAO, NOME) VALUES (5, 'Universidade de Tokio');


INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(1, 1, 1, 'JOAQUIM', 'PEREIRA', '1977-10-15', 'M', '11111111111');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(2, 1, 3, 'GREGORY', 'SMITH', '1972-05-20', 'M', '22222222222');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(3, 1, 5, 'AKI', 'TAKAYAMA', '1985-01-26', 'F', '33333333333');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(4, 2, NULL, 'MARIA', 'DA SILVA', '1988-02-10', 'F', '44444444444');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(5, 2, NULL, 'FERNANDA', 'ZANOTTO', '1992-11-11', 'F', '55555555555');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(6, 3, NULL, 'MICHEL', 'HANADA', '1966-09-07', 'M', '66666666666');
INSERT INTO PESSOA(IDPESSOA, IDTIPO, IDINSTITUICAO, NOME, SOBRENOME, DATA_NASCIMENTO, SEXO, CPF)
	VALUES(7, 3, NULL, 'CLEYSON', 'SHMIDT', '1973-03-18', 'M', '77777777777');

# ESTAGIO_INICIAL = 1;
# ESTAGIO_TESTES = 2;
# ESTAGIO_APLICACAO_EM_MASSA = 3;
INSERT INTO VACINA(IDVACINA, IDPESQUISADOR, NOME, PAIS_ORIGEM, ESTAGIO_PESQUISA, DATA_INICIO_PESQUISA)
	VALUES(1, 1, 'VACINA DAS TAINHAS', 'BRASIL', 1, '2020-09-15');
INSERT INTO VACINA(IDVACINA, IDPESQUISADOR, NOME, PAIS_ORIGEM, ESTAGIO_PESQUISA, DATA_INICIO_PESQUISA)
	VALUES(2, 3, 'VACINA DOS SAMURAIS', 'JAPÃO', 2, '2020-06-23');
INSERT INTO VACINA(IDVACINA, IDPESQUISADOR, NOME, PAIS_ORIGEM, ESTAGIO_PESQUISA, DATA_INICIO_PESQUISA)
	VALUES(3, 2, 'SPUTNIK V', 'RÚSSIA', 3, '2020-04-29');




INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (1, 1, 4.0);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (1, 3, 3.3);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (1, 4, 2.6);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (1, 6, 3.8);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (1, 7, 4.9);

INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (2, 2, 4.0);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (2, 3, 3.8);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (2, 5, 5.0);

INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 1, 3.3);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 2, 2.1);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 3, 4.1);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 4, 5.0);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 5, 2.7);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 6, 2.2);
INSERT INTO NOTA(IDVACINA, IDPESSOA, VALOR) VALUES (3, 7, 1.9);








