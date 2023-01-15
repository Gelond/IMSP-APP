CREATE TABLE  IF NOT EXIST service (
	id_service           INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT ,
	nom                  TEXT  UNIQUE NOT NULL
 );


 CREATE TABLE  TABLE  IF NOT EXIST  planning(
          id_planning    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          heure_debut_officielle         TEXT    ,
 	 heure_fin_officille       TEXT   ,
  );
CREATE TABLE TABLE  IF NOT EXIST  employe (
	matricule            INTEGER NOT NULL  PRIMARY KEY  ,
	nom                 TEXT NOT NULL    ,
	prenom               TEXT NOT NULL    ,
	sexe                 TEXT NOT NULL    ,
	couriel                TEXT  UNIQUE NOT NULL    ,
	qr_code              BLOB UNIQUE NOT NULL    ,
	photo                BLOB NOT   ,
	username                TEXT UNIQUE NOT NULL   ,
	password             TEXT  NOT NULL   ,
    type                 TEXT DEFAULT 'Simple',
	id_service_ref       INTEGER NOT NULL   ,
	id_planning_ref INTEGER   NOT NULL ,
	FOREIGN KEY employe( id_service_ref ) REFERENCES service( id_service ) ,
	FOREIGN KEY employe( id_planning_ref ) REFERENCES planning( id_planning )
 );
CREATE TABLE TABLE  IF NOT EXIST  jour (
        id_jour INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
	date_jour           TEXT NOT NULL PRIMARY KEY AUTOINCREMENT
 );

CREATE TABLE TABLE  IF NOT EXIST   pointage (

	id_pointage          INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT ,
	heure_entree         TEXT    ,
	heure_sortie         TEXT   ,
	date_jour_ref        INTEGER NOT NULL,
	matricule_ref        INTEGER NOT NULL    ,
	FOREIGN KEY pointage( matricule_ref ) REFERENCES employe( matricule )
	FOREIGN KEY  pointage( id_jour_ref ) REFERENCES jour( id_jour)
 );


