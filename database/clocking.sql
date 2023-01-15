CREATE TABLE service ( 
	id_service           INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT ,
	nom                  TEXT NOT NULL    
 );


CREATE TABLE employe ( 
	matricule            INTEGER NOT NULL  PRIMARY KEY  ,
	nom                 TEXT NOT NULL    ,
	prenom               TEXT NOT NULL    ,
	sexe                 CHAR(1) NOT NULL    ,
	couriel                TEXT  UNIQUE NOT NULL    ,
	qr_code              BLOB UNIQUE NOT NULL    ,

	--Picture can be NULL
	photo                BLOB NOT   ,
	username                TEXT UNIQUE NOT NULL   ,
	password             TEXT  NOT NULL   ,
    	type                 TEXT DEFAULT 'Simple',
	id_service_ref       INTEGER    ,
		id_planning_ref INTEGER ,
	FOREIGN KEY employe( id_service_ref ) REFERENCES service( id_service ) ,
	FOREIGN KEY employe( id_planning_ref ) REFERENCES planning( id_planning ) 
	
	
 );

CREATE TABLE jour ( 
        id_jour INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
	date_jour           TEXT NOT NULL PRIMARY KEY AUTOINCREMENT   
 );

CREATE TABLE pointage (

	id_pointage          INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT ,
	heure_entree         TEXT    ,
	heure_sortie         TEXT   ,
	date_jour_ref  INTEGER,
	matricule_ref        INTEGER NOT NULL    ,
	FOREIGN KEY pointage( matricule_ref ) REFERENCES employe( matricule )  
	FOREIGN KEY  pointage( id_jour_ref ) REFERENCES jour( id_jour) 
 );
 CREATE TABLE planning(
         id_planning    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
         heure_debut_officielle         TEXT    ,
	 heure_fin_officille       TEXT   ,
 );

