package co.edu.javeriana.as.personapp.model.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaRequest {
	private String dni;
	private String firstName;
	private String lastName;
	private String age;
	private String sex;
	private String database;
	private List<PhoneRequest> phones = new ArrayList<>();

	// Preserve the original 6-arg constructor used across the codebase
	public PersonaRequest(String dni, String firstName, String lastName, String age, String sex, String database) {
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.sex = sex;
		this.database = database;
	}
}
