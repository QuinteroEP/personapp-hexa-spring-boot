package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;

@Mapper
public class EstudiosMapperMaria {

	@Autowired
	private PersonaMapperMaria personaMapperMaria;

	@Autowired
	private ProfesionMapperMaria profesionMapperMaria;

	public EstudiosEntity fromDomainToAdapter(Study study) {
		EstudiosEntityPK estudioPK = new EstudiosEntityPK();
		estudioPK.setCcPer(study.getPerson().getIdentification());
		estudioPK.setIdProf(study.getProfession().getIdentification());
		EstudiosEntity estudio = new EstudiosEntity();
		estudio.setEstudiosPK(estudioPK);
		estudio.setFecha(validateFecha(study.getGraduationDate()));
		estudio.setUniver(validateUniver(study.getUniversityName()));
		// set minimal persona and profesion entities so JPA can manage the relationships
		co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity personaEntity = new co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity();
		personaEntity.setCc(study.getPerson().getIdentification());
		estudio.setPersona(personaEntity);

		co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity profesionEntity = new co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity();
		profesionEntity.setId(study.getProfession().getIdentification());
		estudio.setProfesion(profesionEntity);
		return estudio;
	}

	private Date validateFecha(LocalDate graduationDate) {
		return graduationDate != null
				? Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
				: null;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}

	public Study fromAdapterToDomain(EstudiosEntity estudiosEntity) {
		Study study = new Study();
		// persona or profesion may be null when JPA doesn't populate relations (FK stored in PK)
		if (estudiosEntity.getPersona() != null) {
			study.setPerson(personaMapperMaria.fromAdapterToDomain(estudiosEntity.getPersona()));
		} else if (estudiosEntity.getEstudiosPK() != null) {
			// build minimal Person with identification from PK
			co.edu.javeriana.as.personapp.domain.Person p = new co.edu.javeriana.as.personapp.domain.Person();
			p.setIdentification(estudiosEntity.getEstudiosPK().getCcPer());
			p.setFirstName("");
			p.setLastName("");
			p.setGender(co.edu.javeriana.as.personapp.domain.Gender.OTHER);
			study.setPerson(p);
		}

		if (estudiosEntity.getProfesion() != null) {
			study.setProfession(profesionMapperMaria.fromAdapterToDomain(estudiosEntity.getProfesion()));
		} else if (estudiosEntity.getEstudiosPK() != null) {
			co.edu.javeriana.as.personapp.domain.Profession prof = new co.edu.javeriana.as.personapp.domain.Profession();
			prof.setIdentification(estudiosEntity.getEstudiosPK().getIdProf());
			prof.setName("");
			study.setProfession(prof);
		}
		study.setGraduationDate(validateGraduationDate(estudiosEntity.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosEntity.getUniver()));
		return study;
	}

	private LocalDate validateGraduationDate(Date fecha) {
		return fecha != null ? fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}

	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}
}