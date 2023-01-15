-- add a FullNames column to Employees
ALTER TABLE "Employees" RENAME TO 'Employees_ME_TMP';
INSERT INTO "Employees"  ("EmployeeID", "LastName", "FirstName",
 "Title", "TitleOfCourtesy", "BirthDate", "HireDate", "Address",
  "City", "Region", "PostalCode", "Country", "HomePhone", "Extension", "Photo",
   "Notes", "ReportsTo","PhotoPath", "FullName")
   SELECT "EmployeeID", "LastName",
    "FirstName", "Title","TitleOfCourtesy", "BirthDate", "HireDate", "Address",
     "City", "Region", "PostalCode", "Country", "HomePhone", "Extension", "Photo",
      "Notes", "ReportsTo", "PhotoPath", "FirstName" || ' '|| "LastName"
       FROM "Employees_ME_TMP";

DROP TABLE "Employees_ME_TMP";





 ALTER TABLE EMPLOYE RENAME TO EMPLOYE_TEMP;
 ALTER TABLE SERVICE RENAME TO  SERVICE_TEMP;
 ALTER TABLE POINTAGE RENAME TO POINTAGE_TEMP;
 ALTER TABLE JOUR RENAME TO JOUR_TEMP;
 ALTER TABLE PLANNING RENAME TO PLANNING_TEMP;
  ALTER TABLE VARIABLE  RENAME TO VARIABLE_TEMP;

 INSERT INTO EMPLOYE


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





