package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;

@Mapper
public class PersonaMapperMaria {

	@Autowired
    private TelefonoMapperMaria telefonoMapperMaria;

    public PersonaEntity fromDomainToAdapter(Person person) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setCc(person.getIdentification());
        personaEntity.setNombre(person.getFirstName());
        personaEntity.setApellido(person.getLastName());
        personaEntity.setGenero(validateGenero(person.getGender()));
        personaEntity.setEdad(validateEdad(person.getAge()));

        // NO mapear estudios desde aquí → produce ciclo
        personaEntity.setTelefonos(
                validateTelefonos(person.getPhoneNumbers())
        );

        return personaEntity;
    }

    private Character validateGenero(Gender gender) {
        return gender == Gender.FEMALE ? 'F' :
               gender == Gender.MALE ? 'M' : ' ';
    }

    private Integer validateEdad(Integer age) {
        return age != null && age >= 0 ? age : null;
    }

    private List<TelefonoEntity> validateTelefonos(List<Phone> phoneNumbers) {
        return phoneNumbers != null ?
                phoneNumbers.stream()
                        .map(telefonoMapperMaria::fromDomainToAdapter)
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }

    public Person fromAdapterToDomain(PersonaEntity personaEntity) {
        Person person = new Person();
        person.setIdentification(personaEntity.getCc());
        person.setFirstName(personaEntity.getNombre());
        person.setLastName(personaEntity.getApellido());
        person.setGender(validateGender(personaEntity.getGenero()));
        person.setAge(validateAge(personaEntity.getEdad()));

        // Again: NO cargar estudios aquí
        person.setPhoneNumbers(
                validatePhones(personaEntity.getTelefonos())
        );

        return person;
    }

    private Gender validateGender(Character genero) {
        return genero == 'F' ? Gender.FEMALE :
               genero == 'M' ? Gender.MALE :
               Gender.OTHER;
    }

    private Integer validateAge(Integer edad) {
        return edad != null && edad >= 0 ? edad : null;
    }

    private List<Phone> validatePhones(List<TelefonoEntity> telefonoEntities) {
        return telefonoEntities != null ?
                telefonoEntities.stream()
                        .map(telefonoMapperMaria::fromAdapterToDomain)
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }
}