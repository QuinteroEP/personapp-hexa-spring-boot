package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfesionMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
	private static final int PERSISTENCIA_MARIADB = 1;
	private static final int PERSISTENCIA_MONGODB = 2;

	private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
	private static final int OPCION_VER_UNA = 1;
	private static final int OPCION_VER_TODO = 2;
	private static final int OPCION_CREAR = 3;
	private static final int OPCION_EDITAR = 4;
	private static final int OPCION_ELIMINAR = 5;

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
					profesionInputAdapterCli.setProfessionOutputPortInjection("MARIA");
					menuOpciones(profesionInputAdapterCli,keyboard);
					break;
				case PERSISTENCIA_MONGODB:
					profesionInputAdapterCli.setProfessionOutputPortInjection("MONGO");
					menuOpciones(profesionInputAdapterCli,keyboard);
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			}  catch (InvalidOptionException e) {
				log.warn(e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
					System.out.print("Ingrese la id de la profesion: ");
					Integer id_buscar = Integer.parseInt(keyboard.nextLine());
					profesionInputAdapterCli.buscar(id_buscar);						
					break;
				case OPCION_VER_TODO:
                    profesionInputAdapterCli.historial();	
					break;
				case OPCION_CREAR:	
					Profession profession_nueva = leerDatosProfesion(keyboard);
					profesionInputAdapterCli.crear(profession_nueva);			
					break;
				case OPCION_EDITAR:
					keyboard.nextLine();
					System.out.print("Ingrese la id de la persona: ");
					Integer id_editar = Integer.parseInt(keyboard.nextLine());
					Profession profesion_editar = leerDatosProfesionEditar(keyboard);
					profesionInputAdapterCli.editar(id_editar, profesion_editar);				
					break;
				case OPCION_ELIMINAR:
					keyboard.nextLine();
					System.out.print("Ingrese la id de la persona: ");
					Integer id_eliminar = Integer.parseInt(keyboard.nextLine());
					profesionInputAdapterCli.eliminar(id_eliminar);	
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
		System.out.println(OPCION_VER_UNA + " para buscar una profesion");
		System.out.println(OPCION_VER_TODO + " para ver todas las profesions");
		System.out.println(OPCION_CREAR + " para crear una nueva profesion");
		System.out.println(OPCION_EDITAR + " para editar una profesions");
		System.out.println(OPCION_ELIMINAR + " para eliminar una profesion");
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

	private Profession leerDatosProfesion(Scanner keyboard){
		keyboard.nextLine();

		System.out.print("Ingrese id: ");
		int ident = Integer.parseInt(keyboard.nextLine());

		System.out.print("Ingrese nombre: ");
		String nombre = keyboard.nextLine();

		System.out.print("Ingrese descripcion: ");
		String apellido = keyboard.nextLine();

		Profession p = new Profession();
		p.setName(nombre);
		p.setDescription(apellido);
		p.setIdentification(ident);

		return p;
	}

	private Profession leerDatosProfesionEditar(Scanner keyboard){
		System.out.print("Ingrese nombre: ");
		String nombre = keyboard.nextLine();

		System.out.print("Ingrese descripcion: ");
		String apellido = keyboard.nextLine();

		Profession p = new Profession();
		p.setName(nombre);
		p.setDescription(apellido);

		return p;
	}
}
