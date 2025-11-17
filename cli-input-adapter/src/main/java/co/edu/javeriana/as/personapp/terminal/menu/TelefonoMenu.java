package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelefonoMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
	private static final int PERSISTENCIA_MARIADB = 1;
	private static final int PERSISTENCIA_MONGODB = 2;

	private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
	private static final int OPCION_VER_UNA = 1;
	private static final int OPCION_VER_TODO = 2;
	private static final int OPCION_CREAR = 3;
	private static final int OPCION_EDITAR = 4;
	private static final int OPCION_ELIMINAR = 5;

	public void iniciarMenu(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
					telefonoInputAdapterCli.setPersonOutputPortInjection("MARIA");
					menuOpciones(telefonoInputAdapterCli,keyboard);
					break;
				case PERSISTENCIA_MONGODB:
					telefonoInputAdapterCli.setPersonOutputPortInjection("MONGO");
					menuOpciones(telefonoInputAdapterCli,keyboard);
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			}  catch (InvalidOptionException e) {
				log.warn(e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
					System.out.print("Ingrese el numero de telefono: ");
					String id_buscar = keyboard.nextLine();
					telefonoInputAdapterCli.buscar(id_buscar);					
					break;
				case OPCION_VER_TODO:
					telefonoInputAdapterCli.historial();					
					break;
				case OPCION_CREAR:
                    keyboard.nextLine();
                    System.out.print("Ingrese la id del dueño: ");
					Integer id_duenio = Integer.parseInt(keyboard.nextLine());
					Phone telefono_nuevo = leerDatosTelefono(keyboard);
					telefonoInputAdapterCli.crear(id_duenio, telefono_nuevo);					
					break;
				case OPCION_EDITAR:
					keyboard.nextLine();
					Phone telefono_editar = leerDatosTelefonoEditar(keyboard);
					telefonoInputAdapterCli.editar(telefono_editar);				
					break;
				case OPCION_ELIMINAR:
					keyboard.nextLine();
					System.out.print("Ingrese el numero de telefono: ");
					String id_eliminar = keyboard.nextLine();
					telefonoInputAdapterCli.eliminar(telefonoInputAdapterCli.findByNumber(id_eliminar));					
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
		System.out.println(OPCION_VER_UNA + " para buscar un telefono");
		System.out.println(OPCION_VER_TODO + " para ver todas los telefonos");
		System.out.println(OPCION_CREAR + " para crear un nuevo telefono");
		System.out.println(OPCION_EDITAR + " para editar un telefonos");
		System.out.println(OPCION_ELIMINAR + " para eliminar un telefono");
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

	private Phone leerDatosTelefono(Scanner keyboard){
		System.out.print("Ingrese numero: ");
		String numero = keyboard.nextLine();

		System.out.print("Ingrese operador: ");
		String operador = keyboard.nextLine();

		Phone p = new Phone();
		p.setCompany(operador);
		p.setNumber(numero);

		return p;
	}

	private Phone leerDatosTelefonoEditar(Scanner keyboard){
		System.out.print("Ingrese el numero del telefono a editar: ");
		String number = keyboard.nextLine();

		System.out.print("Ingrese operador: ");
		String operador = keyboard.nextLine();

		Phone p = new Phone();
		p.setCompany(operador);
		p.setNumber(number);

		return p;
	}
}
