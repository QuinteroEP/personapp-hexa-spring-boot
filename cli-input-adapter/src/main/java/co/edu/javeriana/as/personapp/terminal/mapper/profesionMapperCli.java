package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;

@Mapper
public class profesionMapperCli {
    public ProfesionModelCli fromDomainToAdapterCli(Profession profession) {
        ProfesionModelCli ProfesionModelCli = new ProfesionModelCli();
        ProfesionModelCli.setId(profession.getIdentification());
        ProfesionModelCli.setNom(profession.getName());
        ProfesionModelCli.setDesc(profession.getDescription());
        return ProfesionModelCli;
    }
}
