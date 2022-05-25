create database APS_BANCO;

use APS_BANCO;

create table Funcionarios ( 
	id TINYINT NOT NULL auto_increment,
    usuario VARCHAR(15) NOT NULL UNIQUE,
    senha VARCHAR(15) NOT NULL,
    PRIMARY KEY(id)
);

create table Audios (
	id TINYINT NOT NULL PRIMARY KEY auto_increment,
    id_func TINYINT NOT NULL,
    audio_file LONGBLOB NOT NULL,
    audio_name VARCHAR(50) NOT NULL,
	FOREIGN KEY (id_func)
    REFERENCES Funcionarios(id)
);

INSERT INTO Funcionarios(usuario,senha) VALUES("Carlos","carlos123");
INSERT INTO Funcionarios(usuario,senha) VALUES("Marcos","marcos123");

select * from Funcionarios;