 INSERT INTO service (nom) VALUES('Direction')
 INSERT INTO service (nom) VALUES('Secrétariat administratif')
 INSERT INTO service (nom) VALUES('Comptabilité')
 INSERT INTO service (nom) VALUES('Service scolarité')
 INSERT INTO service (nom) VALUES('Service de coopération')

 INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES ('08:00','17:00')
 INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES ('09:00','17:00')
 INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES ('08:00','18:00')
 INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES ('09:00','18:00')

 INSERT INTO employe(matricule,username,password,type,sexe,
 couriel,id_service_ref,id_planning_ref,nom,prenom)
 VALUES (1,'User10','password','Directeur','M','super@gmail.com',1,1,'AKOBA','Patrick');