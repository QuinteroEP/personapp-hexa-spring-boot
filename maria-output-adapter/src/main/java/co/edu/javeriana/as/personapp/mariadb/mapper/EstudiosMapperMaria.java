package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;

@Mapper
public class EstudiosMapperMaria {

	    public EstudiosEntity fromDomainToAdapter(Study study) {
        EstudiosEntityPK pk = new EstudiosEntityPK();
        pk.setCcPer(study.getPerson().getIdentification());
        pk.setIdProf(study.getProfession().getIdentification());

        EstudiosEntity entity = new EstudiosEntity();
        entity.setEstudiosPK(pk);
        entity.setFecha(validateFecha(study.getGraduationDate()));
        entity.setUniver(validateUniver(study.getUniversityName()));

        // persona mínima
        PersonaEntity persona = new PersonaEntity();
        persona.setCc(study.getPerson().getIdentification());
        entity.setPersona(persona);

        // profesión mínima
        ProfesionEntity prof = new ProfesionEntity();
        prof.setId(study.getProfession().getIdentification());
        entity.setProfesion(prof);

        return entity;
    }

	private Date validateFecha(LocalDate graduationDate) {
		return graduationDate != null
				? Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
				: null;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}

	public Study fromAdapterToDomain(EstudiosEntity entity) {
		Study study = new Study();

		Person p = new Person();
		p.setIdentification(entity.getEstudiosPK().getCcPer());
		study.setPerson(p);

		Profession prof = new Profession();
		prof.setIdentification(entity.getEstudiosPK().getIdProf());
		study.setProfession(prof);

		study.setGraduationDate(validateGraduationDate(entity.getFecha()));
		study.setUniversityName(validateUniversityName(entity.getUniver()));

		return study;
	}

	private LocalDate validateGraduationDate(Date fecha) {
		if (fecha == null) {
			return null;
		}

		if (fecha instanceof java.sql.Date) {
			return ((java.sql.Date) fecha).toLocalDate();
		}

		return fecha.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}


	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}
}