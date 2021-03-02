DROP VIEW VW_estoque;
DROP VIEW VW_funcCaixa;
DROP VIEW VW_funcEstoque;
DROP VIEW VW_desligamento;
DROP VIEW VW_gerente;

DROP INDEX IN_Estoque;

DROP TABLE estoque;
DROP TABLE del_estoque;
DROP TABLE cadastro;
DROP TABLE compras;
DROP TABLE sol_desligamento;
DROP TABLE funcionarios;
DROP TABLE lojas;
DROP TABLE logs;

CREATE TABLE lojas(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	cidade TEXT NOT NULL UNIQUE
);

CREATE TABLE estoque(
	id_prod INTEGER PRIMARY KEY AUTOINCREMENT,
	descricao TEXT NOT NULL UNIQUE,
	preco NUMERIC NOT NULL,
	qtd INTEGER NOT NULL,
	loja_id INTEGER NOT NULL,
	FOREIGN KEY (loja_id)
	REFERENCES lojas(id)
);

CREATE TABLE del_estoque(
	id_prod INTEGER PRIMARY KEY AUTOINCREMENT,
	descricao TEXT NOT NULL UNIQUE,
	preco NUMERIC NOT NULL,
	loja_id INTEGER NOT NULL,
	FOREIGN KEY (loja_id)
	REFERENCES lojas(id)
);

CREATE TABLE funcionarios(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nome TEXT NOT NULL,
	sobrenome TEXT NOT NULL,
	ramal TEXT UNIQUE,
	loja_id INTEGER,
	cargo TEXT NOT NULL,
	venda NUMERIC NOT NULL,
	gerenteId INTEGER NOT NULL,
	FOREIGN KEY (loja_id)
	REFERENCES lojas(id)
);

CREATE TABLE cadastro(
	funcionario_id INTEGER PRIMARY KEY AUTOINCREMENT,
	usuario TEXT NOT NULL UNIQUE,
	senha TEXT NOT NULL,
	FOREIGN KEY (funcionario_id)
	REFERENCES funcionarios(id)
);

CREATE TABLE compras(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	lucro NUMERIC NOT NULL,
	dt TEXT,
	prods TEXT NOT NULL,
	funcionario_id INTEGER,
	FOREIGN KEY (funcionario_id)
	REFERENCES funcionarios(id)
);

CREATE TABLE sol_desligamento(
	id INTEGER PRIMARY KEY,
	FOREIGN KEY (id)
	REFERENCES funcionarios(id)
);

CREATE TABLE logs(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	atividade TEXT,
	momento TEXT
);

CREATE VIEW VW_estoque
	AS SELECT e.id_prod as 'ID', e.descricao as 'Produto', e.preco as 'Preço', e.qtd as 'Estoque', l.cidade as 'Loja'
	FROM estoque e INNER JOIN lojas l
	ON e.loja_id = l.id;
	
CREATE VIEW VW_funcCaixa
	AS SELECT f.id as 'Matricula', f.nome || ' ' || f.sobrenome as 'Nome_completo', f.ramal as 'Ramal', l.cidade as 'Loja', f.venda as 'Lucro', f.gerenteId
	FROM funcionarios f INNER JOIN lojas l
	ON f.loja_id = l.id
	WHERE f.cargo = 'Caixa';
	
CREATE VIEW VW_funcEstoque
	AS SELECT f.id as 'Matricula', f.nome || ' ' || f.sobrenome as 'Nome_completo', f.ramal as 'Ramal', l.cidade as 'Loja', f.gerenteId
	FROM funcionarios f INNER JOIN lojas l
	ON f.loja_id = l.id
	WHERE f.cargo = 'Logistica';

CREATE VIEW VW_desligamento
	AS SELECT f.id as 'Matricula', f.cargo as 'Cargo', f.nome || ' ' || f.sobrenome as 'Nome_completo'
	FROM funcionarios f INNER JOIN sol_desligamento s
		 ON f.id = s.id;

CREATE VIEW VW_gerente
	AS SELECT id AS 'Matricula', nome || ' ' || sobrenome as 'Nome_completo'
	FROM funcionarios
	WHERE cargo = 'Gerente';
	
CREATE INDEX IN_Estoque ON estoque (descricao, id_prod);

INSERT INTO lojas (cidade)
VALUES 
	('São Paulo'),
	('Santos'),
	('Acre');

INSERT INTO estoque (descricao, preco, qtd, loja_id)
VALUES 
	('Monitor',500.99,10,2),
	('Mouse',150.99,50,3),
	('Teclado',100.50,100,1),
	('Celular',999,40,2),
	('Notebook',2500.99,50,1),
	('Video Game',1500.98,150,3);
	
INSERT INTO funcionarios(nome, sobrenome, ramal, loja_id, cargo, gerenteId, venda)
VALUES
	('ADM','','',1,'ADM',0,0),
	('Leonardo','Lacerda','1148',1, 'Gerente', 0,0),
	('Alyne','Canuto','1234',1 , 'Caixa', 2,50.99),
	('Victória', 'Rocha', '9876',3 , 'Logistica', 5,0),
	('Socorro','Pinheiro','8946',2,'Gerente',0,0);
	

INSERT INTO cadastro(usuario,senha)
VALUES
	('adm','adm'),
	('leonardo','123'),
	('aline','123'),
	('vitoria','123'),
	('socorro','123');
	
	
INSERT INTO compras(lucro,dt,prods,funcionario_id)
VALUES
	(953.96, datetime('now', 'localtime'), '2x3;1x1;', 3);
