package co.edu.javeriana.as.personapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;

@Mapper
public class PersonaMapperRest {
    
	public PersonaResponse fromDomainToAdapterRestMaria(Person person) {
		return fromDomainToAdapterRest(person, "MariaDB");
	}
	public PersonaResponse fromDomainToAdapterRestMongo(Person person) {
		return fromDomainToAdapterRest(person, "MongoDB");
	}
    
	public PersonaResponse fromDomainToAdapterRest(Person person, String database) {
		PersonaResponse resp = new PersonaResponse(
				person.getIdentification()+"", 
				person.getFirstName(), 
				person.getLastName(), 
				person.getAge()+"", 
				person.getGender().toString(), 
				database,
				"OK");

		if (person.getPhoneNumbers() != null) {
			List<PhoneRequest> phones = person.getPhoneNumbers().stream()
					.map(p -> new PhoneRequest(p.getNumber(), p.getCompany()))
					.collect(Collectors.toList());
			resp.setPhones(phones);
		}

		return resp;
	}

	public Person fromAdapterToDomain(PersonaRequest request) {
		// TODO Auto-generated method stub
		return new Person();
	}
        
}
