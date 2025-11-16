package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;

@Mapper
public class ProfesionMapperMaria {

	@Autowired
	private EstudiosMapperMaria estudiosMapperMaria;

	public ProfesionEntity fromDomainToAdapter(Profession profession) {
		ProfesionEntity profesionEntity = new ProfesionEntity();
		profesionEntity.setId(profession.getIdentification());
		profesionEntity.setNom(profession.getName());
		profesionEntity.setDes(validateDes(profession.getDescription()));
		profesionEntity.setEstudios(validateEstudios(profession.getStudies()));
		return profesionEntity;
	}

	private String validateDes(String description) {
		return description != null ? description : "";
	}

	private List<EstudiosEntity> validateEstudios(List<Study> studies) {
		if (studies == null || studies.isEmpty()) {
			return new ArrayList<>();
		}

		return studies.stream().map(study -> {
			EstudiosEntity e = new EstudiosEntity();

			// --- Composite PK ---
			EstudiosEntityPK pk = new EstudiosEntityPK();
			pk.setCcPer(study.getPerson().getIdentification());
			pk.setIdProf(study.getProfession().getIdentification());
			e.setEstudiosPK(pk);

			// --- Simple fields ---
			e.setFecha(study.getGraduationDate() != null
				? Date.from(study.getGraduationDate()
					.atStartOfDay()
					.atZone(ZoneId.systemDefault())
					.toInstant())
				: null
			);

			e.setUniver(study.getUniversityName() != null
				? study.getUniversityName()
				: ""
			);
			return e;
		}).collect(Collectors.toList());
	}


	public Profession fromAdapterToDomain(ProfesionEntity profesionEntity) {
		Profession profession = new Profession();
		profession.setIdentification(profesionEntity.getId());
		profession.setName(profesionEntity.getNom());
		profession.setDescription(validateDescription(profesionEntity.getDes()));
		profession.setStudies(validateStudies(profesionEntity.getEstudios()));
		return profession;
	}

	private String validateDescription(String des) {
		return des != null ? des : "";
	}

	private List<Study> validateStudies(List<EstudiosEntity> estudiosEntity) {
		return estudiosEntity != null && !estudiosEntity.isEmpty() ? estudiosEntity.stream()
				.map(estudio -> estudiosMapperMaria.fromAdapterToDomain(estudio)).collect(Collectors.toList())
				: new ArrayList<Study>();
	}
}
