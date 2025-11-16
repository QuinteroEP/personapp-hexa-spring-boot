INSERT INTO 
	`persona_db`.`persona`(`cc`,`nombre`,`apellido`,`genero`,`edad`) 
VALUES
	(123456789,'Pepe','Perez','M',30),
	(987654321,'Pepito','Perez','M',null),
	(321654987,'Pepa','Juarez','F',30),
	(147258369,'Pepita','Juarez','F',10),
	(963852741,'Fede','Perez','M',18);

INSERT INTO 
	`persona_db`.`profesion`(`id`,`nom`,`des`) 
VALUES
	(1,'Sistemas','Ingenieria de Sistemas');

INSERT INTO 
	`persona_db`.`telefono`(`num`,`oper`,`duenio`) 
VALUES
	(100123456,'Claro', 123456789);

INSERT INTO 
	`persona_db`.`estudios`(`id_prof`,`cc_per`,`fecha`,`univer`) 
VALUES
	(1, 123456789, '2025-11-15' ,'Universidad Javeriana');