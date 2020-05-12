USE xsr;

CREATE TABLE Cliente (
	id		INT PRIMARY KEY,
	nome	VARCHAR(100),
	tlf		VARCHAR(100),
	email	VARCHAR(100),
	notas	TEXT
);

CREATE TABLE Peza (
	id			INT PRIMARY KEY,
	codigo		VARCHAR(100),
	prov		VARCHAR(100),
	nome		VARCHAR(100),
	foto		LONGBLOB,
	cantidade	INT,
	notas		TEXT
);

CREATE TABLE Reparacion (
	id			INT PRIMARY KEY,
	ini			DATETIME,
	fin			DATETIME,
	n_horas		INT,
	completa	BOOLEAN,
	causa		TEXT,
	solucion	TEXT,
	notas		TEXT,
	client_id	INT,

	FOREIGN KEY FK_Reparacion_ClientID (client_id)
		REFERENCES Cliente(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE Pedido (
	client_id	INT,
	peza_id		INT,
	estado		varchar(100),

	PRIMARY KEY (client_id, peza_id),
	
	FOREIGN KEY FK_Pedido_ClientID (client_id)
		REFERENCES Cliente(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	
	FOREIGN KEY FK_Pedido_PezaID (peza_id)
		REFERENCES Peza(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE PezasReparacion (
	reparacion_id	INT,
	peza_id			INT,
	
	PRIMARY KEY (reparacion_id, peza_id),
	
	FOREIGN KEY FK_PR_ReparID (reparacion_id)
		REFERENCES Reparacion(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	
	FOREIGN KEY FK_PR_PezaID (peza_id)
		REFERENCES Peza(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);
