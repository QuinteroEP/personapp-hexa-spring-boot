db = db.getSiblingDB("persona_db")

db.persona.insertMany([
	{
		"_id": NumberInt(123456789),
		"nombre": "Pepe",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(987654321),
		"nombre": "Pepito",
		"apellido": "Perez",
		"genero": "M",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(321654987),
		"nombre": "Pepa",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(147258369),
		"nombre": "Pepita",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(10),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(963852741),
		"nombre": "Fede",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(18),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	}
], { ordered: false })

db.profesion.insertOne({
	"_id": NumberInt(1),
	"nom": "Sistemas",
	"des": "Ingenieria de Sistemas",
	"_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
})

db.telefono.insertOne({
	"_id": "1001234567",
	"oper": "Claro",
	"primaryDuenio": {
		"$ref": "persona",
		"$id": NumberInt(123456789)
	},
	"_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
})

db.estudios.insertOne({
	"_id": "1",
	"primaryPersona": {
		"$ref": "persona",
		"$id": NumberInt(123456789)
	},
	"primaryProfesion": {
		"$ref": "profesion",
		"$id": NumberInt(1)
	},
	"fecha": ISODate("2025-11-15T00:00:00Z"),
	"univer": "Universidad Javeriana",
	"_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
})
