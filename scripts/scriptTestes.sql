USE DBSISTEMACOVID;

SET SQL_SAFE_UPDATES = 0;

SELECT * FROM TIPO;
SELECT * FROM INSTITUICAO;
select * from PESSOA;
SELECT * FROM PESSOA NATURAL JOIN TIPO;
SELECT V.*, CONCAT(P.NOME,' ',P.SOBRENOME) AS 'PESQUISADOR' FROM VACINA V
	INNER JOIN PESSOA P ON V.IDPESQUISADOR = P.IDPESSOA;
SELECT N.IDNOTA, V.NOME, P.NOME, N.VALOR 
	FROM NOTA N NATURAL JOIN VACINA V 
	INNER JOIN PESSOA P ON P.IDPESSOA = N.IDPESSOA
    ORDER BY N.IDNOTA;
SELECT * FROM NOTA;

DELETE FROM INSTITUICAO;
ALTER TABLE INSTITUICAO AUTO_INCREMENT = 1;

DELETE FROM PESSOA;
ALTER TABLE PESSOA AUTO_INCREMENT = 1;

##
## SELECTs para relatórios abaixo
##

/*TOTAL DE VACINAS PESQUISADAS POR PESQUISADOR*/
SELECT 
    P.NOME,
    P.SOBRENOME,
    COUNT(V.IDVACINA) AS 'NUMERO_DE_VACINAS'
FROM
	PESSOA P INNER JOIN
	VACINA V ON P.IDPESSOA = V.IDPESQUISADOR
    RIGHT JOIN PESSOA P ON 
    V.IDPESQUISADOR = P.IDPESSOA
WHERE
	P.IDTIPO = 1
GROUP BY
	P.IDPESSOA
ORDER BY
	NUMERO_DE_VACINAS;
        
/*TOTAL DE VACINAS POR PAÍS DE ORIGEM */              
SELECT 
	PAIS_ORIGEM,
    COUNT(VACINA.IDVACINA) AS 'NUMERO_DE_VACINAS'
FROM
	PESSOA P INNER JOIN
	VACINA V ON P.IDPESSOA = V.IDPESQUISADOR
#WHERE
#	DATA_INICIO_PESQUISA >= '2020-06-23'
GROUP BY
	PAIS_ORIGEM
ORDER BY
	NUMERO_DE_VACINAS;
    
/*TOTAL DE VACINAS POR ESTÁGIO DE PESQUISA*/
SELECT
	ESTAGIO_PESQUISA,
    COUNT(IDVACINA) AS 'NUMERO_DE_VACINAS'
FROM
	PESSOA P INNER JOIN
	VACINA V ON P.IDPESSOA = V.IDPESQUISADOR
WHERE
	DATA_INICIO_PESQUISA >= '2020-06-23'
GROUP BY
	ESTAGIO_PESQUISA
ORDER BY
	NUMERO_DE_VACINAS;

/*NUMERO DE AVALIACOES POR VACINA*/
SELECT
	V.NOME,
    COUNT(N.IDVACINA) AS 'NUMERO_DE_AVALIACOES'
FROM
	PESSOA P INNER JOIN
	VACINA V ON P.IDPESSOA = V.IDPESQUISADOR
    RIGHT JOIN NOTA N ON V.IDVACINA = N.IDVACINA
#WHERE
	#DATA_INICIO_PESQUISA >= '2020-06-23'
    #P.SEXO='F'
GROUP BY V.NOME
ORDER BY NUMERO_DE_AVALIACOES DESC;

/*MÉDIA DE AVALIACOES POR VACINA*/
SELECT
	V.NOME,
    AVG(N.VALOR) AS 'MEDIA_DE_AVALIACOES'
FROM
	PESSOA P INNER JOIN
	VACINA V ON P.IDPESSOA = V.IDPESQUISADOR
    RIGHT JOIN NOTA N ON V.IDVACINA = N.IDVACINA
#WHERE
	#DATA_INICIO_PESQUISA >= '2020-06-23'
    #P.SEXO='F'
GROUP BY V.NOME
ORDER BY MEDIA_DE_AVALIACOES DESC;

SELECT *
FROM
	VACINA V RIGHT JOIN
    NOTA N ON V.IDVACINA = N.IDVACINA
;


# ^
# VERIFICADO

/*NÃO SEI COMO FAZ COM RELAÇÃO AO PERÍODO , 
PARA GANHAR TEMPO VOU FAZER O RESTANTE DAS QUERYS DE RELATÓRIO DESCONSIDERANDO O FILTRO DE PERÍODO */

/*TOTAL DE PESSOAS POR SEXO*/
SELECT 
	COUNT(IDPESSOA),
    SEXO
FROM
	PESSOA
GROUP BY
	SEXO;

/*TOTAL DE PESSOAS POR INSTITUICAO*/
SELECT 
	COUNT(IDPESSOA),
    INSTITUICAO.NOME
FROM
	VACINA
    RIGHT JOIN PESSOA ON 
    PESSOA.IDPESSOA = VACINA.IDPESQUISADOR 
    RIGHT JOIN INSTITUICAO ON
    INSTITUICAO.IDINSTITUICAO = PESSOA.IDINSTITUICAO
GROUP BY
	INSTITUICAO.NOME;
    
/*TOTAL DE PESSOAS POR CATEGORIA*/
SELECT 
	COUNT(IDPESSOA),
    TIPO.DESCRICAO
FROM
	PESSOA
    RIGHT JOIN TIPO ON
    PESSOA.IDTIPO = TIPO.IDTIPO
GROUP BY
	TIPO.DESCRICAO;

/*TOTAL DE PESSOAS POR FAIXA DE IDADE E MEDIA DA NOTA DA VACINA PELA FAIXA*/

/*calculo por faixa de idade - 10 anos*/
SELECT 
	CASE 
		WHEN T.IDADE BETWEEN 1 AND 10 THEN 'De 1 a 10' 
		WHEN T.IDADE BETWEEN 11 AND 20 THEN 'De 11 a 20' 
		WHEN T.IDADE BETWEEN 21 AND 30 THEN 'De 21 a 30'
		WHEN T.IDADE BETWEEN 31 AND 40 THEN 'De 31 a 40'
		WHEN T.IDADE BETWEEN 41 AND 50 THEN 'De 41 a 50'
		WHEN T.IDADE BETWEEN 51 AND 60 THEN 'De 51 a 60'
		WHEN T.IDADE BETWEEN 61 AND 70 THEN 'De 61 a 70'
		WHEN T.IDADE BETWEEN 71 AND 80 THEN 'De 71 a 80'
		WHEN T.IDADE BETWEEN 81 AND 90 THEN 'De 81 a 90'
		WHEN T.IDADE > 90 THEN 'De 91 em diante'
	END AS FAIXAS,
	SUM(T.TOTAL) AS TOTAL,
    AVG(T.VALOR) AS MEDIA_NOTA
FROM 
	( 
		SELECT 
			YEAR(NOW()) - YEAR(P.DATA_NASCIMENTO) - (DAYOFYEAR(NOW()) < DAYOFYEAR(P.DATA_NASCIMENTO)) AS IDADE, COUNT(P.IDPESSOA) AS TOTAL, N.VALOR AS VALOR
		FROM
			PESSOA P INNER JOIN
            NOTA N ON P.IDPESSOA = N.IDPESSOA
		WHERE
			N.IDVACINA = 3
		GROUP BY
			IDADE
	) AS T
GROUP BY FAIXAS
ORDER BY FAIXAS;

SELECT 
	YEAR(NOW()) - YEAR(P.DATA_NASCIMENTO) - (DAYOFYEAR(NOW()) < DAYOFYEAR(P.DATA_NASCIMENTO)) AS IDADE, COUNT(P.IDPESSOA) AS TOTAL
FROM
	PESSOA P INNER JOIN
	NOTA N ON P.IDPESSOA = N.IDPESSOA
GROUP BY
	IDADE;

SELECT *
FROM
	PESSOA P INNER JOIN NOTA N ON P.IDPESSOA = N.IDPESSOA
WHERE
	N.IDVACINA = 1;
/*calculo por faixa de idade - 10 anos* --> só não ficou quando não tem nenhuma pessoa/
select 
case 
when T2.idade between 1 and 10 then 'De 1 a 10' 
when T2.idade between 11 and 21 then 'De 11 a 21' 
when T2.idade between 22 and 32 then 'De 22 a 32' 
when T2.idade between 33 and 43 then 'De 33 a 43' 
when T2.idade between 44 and 54 then 'De 44 a 54' 
when T2.idade between 55 and 65 then 'De 55 a 65' 
when T2.idade between 66 and 76 then 'De 66 a 76'
when T2.idade between 77 and 87 then 'De 77 a 87'
when T2.idade between 97 and 107 then 'De 97 a 107'
end as faixas, sum(T2.total) as total
from ( SELECT YEAR(now()) - YEAR(DATA_NASCIMENTO) - ( DAYOFYEAR(now()) < DAYOFYEAR(DATA_NASCIMENTO)) as idade, count(*) as total 
from pessoa  
group by idade) T2 
group by faixas; 


/*MEDIA NOTA POR VACINA */
SELECT 
    VACINA.NOME,
	AVG(NOTA.VALOR)
FROM
	VACINA
    INNER JOIN NOTA ON
    NOTA.IDVACINA = VACINA.IDVACINA
GROUP BY
	VACINA.NOME;
	
/*MEDIA NOTA POR USUARIO */
SELECT 
    PESSOA.NOME,
	AVG(NOTA.VALOR)
FROM
	PESSOA
    INNER JOIN NOTA ON
    NOTA.IDPESSOA = PESSOA.IDPESSOA
GROUP BY
	PESSOA.NOME;

/*MEDIA NOTA POR CATEGORIA --> NÃO SEI SE ESTÁ OK*/
SELECT 
    TIPO.DESCRICAO,
	AVG(NOTA.VALOR)
FROM
	TIPO
    LEFT JOIN PESSOA ON
    TIPO.IDTIPO = PESSOA.IDTIPO
    LEFT JOIN NOTA ON
    NOTA.IDPESSOA = PESSOA.IDPESSOA
GROUP BY
	TIPO.DESCRICAO;

/*MEDIA NOTA POR SEXO */
SELECT 
    PESSOA.SEXO,
	AVG(NOTA.VALOR)
FROM
	TIPO
    LEFT JOIN PESSOA ON
    TIPO.IDTIPO = PESSOA.IDTIPO
    LEFT JOIN NOTA ON
    NOTA.IDPESSOA = PESSOA.IDPESSOA
GROUP BY
	PESSOA.SEXO;   
