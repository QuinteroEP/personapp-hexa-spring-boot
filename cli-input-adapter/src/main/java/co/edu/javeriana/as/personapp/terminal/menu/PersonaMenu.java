package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.adapter.PersonaInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonaMenu {

	private static final int OPCION_REGRESAR_MODULOS = 0;
	private static final int PERSISTENCIA_MARIADB = 1;
	private static final int PERSISTENCIA_MONGODB = 2;

	private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
	private static final int OPCION_VER_UNA = 1;
	private static final int OPCION_VER_TODO = 2;
	private static final int OPCION_CREAR = 3;
	private static final int OPCION_EDITAR = 4;
	private static final int OPCION_ELIMINAR = 5;

	public void iniciarMenu(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuMotorPersistencia();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
				case OPCION_REGRESAR_MODULOS:
					isValid = true;
					break;
				case PERSISTENCIA_MARIADB:
					personaInputAdapterCli.setPersonOutputPortInjection("MARIA");
					menuOpciones(personaInputAdapterCli,keyboard);
					break;
				case PERSISTENCIA_MONGODB:
					personaInputAdapterCli.setPersonOutputPortInjection("MONGO");
					menuOpciones(personaInputAdapterCli,keyboard);
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			}  catch (InvalidOptionException e) {
				log.warn(e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuOpciones();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
				case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
					isValid = true;
					break;
				case OPCION_VER_UNA:
					keyboard.nextLine();
					System.out.print("Ingrese la id de la persona: ");
					Integer id_buscar = Integer.parseInt(keyboard.nextLine());
					personaInputAdapterCli.buscar(id_buscar);					
					break;
				case OPCION_VER_TODO:
					personaInputAdapterCli.historial();					
					break;
				case OPCION_CREAR:
					Person persona_nueva = leerDatosPersona(keyboard);
					personaInputAdapterCli.crear(persona_nueva);					
					break;
				case OPCION_EDITAR:
					keyboard.nextLine();
					System.out.print("Ingrese la id de la persona: ");
					Integer id_editar = Integer.parseInt(keyboard.nextLine());
					Person persona_editar = leerDatosPersonaEditar(keyboard);
					personaInputAdapterCli.editar(id_editar, persona_editar);					
					break;
				case OPCION_ELIMINAR:
					keyboard.nextLine();
					System.out.print("Ingrese la id de la persona: ");
					Integer id_eliminar = Integer.parseInt(keyboard.nextLine());
					personaInputAdapterCli.eliminar(id_eliminar);					
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			} catch (InputMismatchException e) {
				log.warn("Solo se permiten números.");
			}
		} while (!isValid);
	}

	private void mostrarMenuOpciones() {
		System.out.println("----------------------");
		System.out.println(OPCION_VER_UNA + " para buscar una persona");
		System.out.println(OPCION_VER_TODO + " para ver todas las personas");
		System.out.println(OPCION_CREAR + " para crear una nueva persona");
		System.out.println(OPCION_EDITAR + " para editar una personas");
		System.out.println(OPCION_ELIMINAR + " para eliminar una persona");
		System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
	}

	private void mostrarMenuMotorPersistencia() {
		System.out.println("----------------------");
		System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
		System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
		System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
	}

	private int leerOpcion(Scanner keyboard) {
		try {
			System.out.print("Ingrese una opción: ");
			return keyboard.nextInt();
		} catch (InputMismatchException e) {
			log.warn("Solo se permiten números.");
			return leerOpcion(keyboard);
		}
	}

	private Person leerDatosPersona(Scanner keyboard){
		keyboard.nextLine();

		System.out.print("Ingrese nombre: ");
		String nombre = keyboard.nextLine();

		System.out.print("Ingrese apellido: ");
		String apellido = keyboard.nextLine();

		System.out.print("Ingrese edad: ");
		int edad = Integer.parseInt(keyboard.nextLine());

		System.out.print("Ingrese genero (MALE, FEMALE, OTHER): ");
		String generoStr = keyboard.nextLine().toUpperCase();
		Gender genero = Gender.valueOf(generoStr);

		System.out.print("Ingrese id: ");
		int ident = Integer.parseInt(keyboard.nextLine());

		Person p = new Person();
		p.setFirstName(nombre);
		p.setLastName(apellido);
		p.setAge(edad);
		p.setGender(genero);
		p.setIdentification(ident);

		return p;
	}

	private Person leerDatosPersonaEditar(Scanner keyboard){
		System.out.print("Ingrese nombre: ");
		String nombre = keyboard.nextLine();

		System.out.print("Ingrese apellido: ");
		String apellido = keyboard.nextLine();

		System.out.print("Ingrese edad: ");
		int edad = Integer.parseInt(keyboard.nextLine());

		System.out.print("Ingrese genero (MALE, FEMALE, OTHER): ");
		String generoStr = keyboard.nextLine().toUpperCase();
		Gender genero = Gender.valueOf(generoStr);

		Person p = new Person();
		p.setFirstName(nombre);
		p.setLastName(apellido);
		p.setAge(edad);
		p.setGender(genero);

		return p;
	}

}
 