DROP DATABASE IF EXISTS DBSISTEMACOVID;

CREATE DATABASE DBSISTEMACOVID;

USE DBSISTEMACOVID;

CREATE TABLE TIPO (
	IDTIPO INT NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR(50),
    PRIMARY KEY (IDTIPO)
);

CREATE TABLE INSTITUICAO (
	IDINSTITUICAO INT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(100) NOT NULL,
    PRIMARY KEY (IDINSTITUICAO)
);

CREATE TABLE PESSOA (
	IDPESSOA INT NOT NULL AUTO_INCREMENT,
    IDTIPO INT NOT NULL,
    IDINSTITUICAO INT,
    NOME VARCHAR(255) NOT NULL,
    SOBRENOME VARCHAR(255) NOT NULL,
    DATA_NASCIMENTO DATE NOT NULL,
    SEXO CHAR(1) NOT NULL,
    CPF CHAR(11) NOT NULL,
    PRIMARY KEY (IDPESSOA),
    CONSTRAINT FK_PESSOA_TIPO FOREIGN KEY (IDTIPO) REFERENCES TIPO (IDTIPO)
);

# Sugestão para o futuro: 
#  Mudar os ON DELETE CASCADE por uma atualização em tabelas de log via trigger
CREATE TABLE VACINA (
	IDVACINA INT NOT NULL AUTO_INCREMENT,
    IDPESQUISADOR INT NOT NULL,
    NOME VARCHAR(255) NOT NULL,
    PAIS_ORIGEM VARCHAR(255) NOT NULL,
    ESTAGIO_PESQUISA INT NOT NULL,
    DATA_INICIO_PESQUISA DATE NOT NULL,
    PRIMARY KEY(IDVACINA),
    CONSTRAINT FK_VACINA_PESQUISADOR FOREIGN KEY (IDPESQUISADOR) 
		REFERENCES PESSOA (IDPESSOA)
        ON DELETE CASCADE
);

CREATE TABLE NOTA (
	IDNOTA INT NOT NULL AUTO_INCREMENT,
    IDVACINA INT NOT NULL,
    IDPESSOA INT NOT NULL,
    VALOR DECIMAL(10,4) NOT NULL,
    PRIMARY KEY(IDNOTA),
    CONSTRAINT FK_NOTA_VACINA FOREIGN KEY (IDVACINA)
		REFERENCES VACINA(IDVACINA)
		ON DELETE CASCADE,
    CONSTRAINT FK_NOTA_PESSOA FOREIGN KEY (IDPESSOA)
		REFERENCES PESSOA(IDPESSOA)
        ON DELETE CASCADE
);

/*DELIMITER $$

# VERIFICAR SE A PESSOA RELACIONADA NA FK DA VACINA É DO TIPO PESQUISADOR
CREATE TRIGGER TR_VACINA_BI BEFORE INSERT ON VACINA FOR EACH ROW
BEGIN
	DECLARE TIPO_DESCRICAO INT;
    
    SELECT T.DESCRICAO
    INTO TIPO_DESCRICAO
    FROM PESSOA P INNER JOIN
		TIPO T ON P.IDTIPO = T.IDTIPO 
    WHERE IDPESSOA = NEW.IDPESQUISADOR;
    
    IF (TIPO_DESCRICAO = 'PESQUISADOR') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A pessoa definida como pesquisador responsável, não é um pesquisador!';
    END IF;
END $$

DELIMITER ;*/


INSERT INTO TIPO(IDTIPO, DESCRICAO) VALUES (1, 'PESQUISADOR');
INSERT INTO TIPO(IDTIPO, DESCRICAO) VALUES (2, 'PUBLICO_GERAL');
INSERT INTO TIPO(IDTIPO, DESCRICAO) VALUES (3, 'VOLUNTARIO');