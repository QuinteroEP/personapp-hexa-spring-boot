package co.edu.javeriana.as.personapp.terminal.menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.terminal.adapter.EstudioInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EstudioMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_UNA = 1;
    private static final int OPCION_VER_TODO = 2;
    private static final int OPCION_CREAR = 3;
    private static final int OPCION_EDITAR = 4;
    private static final int OPCION_ELIMINAR = 5;

    public void iniciarMenu(EstudioInputAdapterCli estudioAdapter, Scanner keyboard) {
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
                    estudioAdapter.setPersonOutputPortInjection("MARIA");
                    menuOpciones(estudioAdapter, keyboard);
                    break;
                case PERSISTENCIA_MONGODB:
                    estudioAdapter.setPersonOutputPortInjection("MONGO");
                    menuOpciones(estudioAdapter, keyboard);
                    break;
                default:
                    log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(EstudioInputAdapterCli estudioAdapter, Scanner keyboard) {
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
                    System.out.print("Ingrese personId (numero): ");
                    int pid = Integer.parseInt(keyboard.nextLine().trim());
                    System.out.print("Ingrese profId (numero): ");
                    int prid = Integer.parseInt(keyboard.nextLine().trim());
                    Study s = estudioAdapter.buscar(pid, prid);
                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("No existe el estudio " + pid + " / " + prid);
                    }
                    break;
                case OPCION_VER_TODO:
                    List<Study> all = estudioAdapter.historial();
                    if (all == null || all.isEmpty()) {
                        System.out.println("No hay estudios registrados.");
                    } else {
                        all.forEach(System.out::println);
                        System.out.println("Total: " + all.size());
                    }
                    break;
                case OPCION_CREAR:
                    keyboard.nextLine();
                    Study nuevo = pedirEstudioPorTeclado(estudioAdapter, keyboard, false);
                    if (nuevo != null) {
                        Study creado = estudioAdapter.crear(nuevo);
                        if (creado != null) {
                            System.out.println("Estudio creado: " + creado);
                        } else {
                            System.out.println("No se pudo crear el estudio.");
                        }
                    }
                    break;
                case OPCION_EDITAR:
                    keyboard.nextLine();
                    System.out.print("Ingrese personId del estudio a editar: ");
                    int ep = Integer.parseInt(keyboard.nextLine().trim());
                    System.out.print("Ingrese profId del estudio a editar: ");
                    int ef = Integer.parseInt(keyboard.nextLine().trim());
                    Study editData = pedirEstudioPorTeclado(estudioAdapter, keyboard, true);
                    if (editData != null) {
                        Study edited = estudioAdapter.editar(ep, ef, editData);
                        if (edited != null) {
                            System.out.println("Estudio editado: " + edited);
                        } else {
                            System.out.println("No se pudo editar el estudio.");
                        }
                    }
                    break;
                case OPCION_ELIMINAR:
                    keyboard.nextLine();
                    System.out.print("Ingrese personId del estudio a eliminar: ");
                    int dp = Integer.parseInt(keyboard.nextLine().trim());
                    System.out.print("Ingrese profId del estudio a eliminar: ");
                    int df = Integer.parseInt(keyboard.nextLine().trim());
                    boolean ok = estudioAdapter.eliminar(dp, df);
                    System.out.println(ok ? "Estudio eliminado." : "No se pudo eliminar (no existe).");
                    break;
                default:
                    log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                log.warn("Solo se permiten números o formato inválido.");
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_UNA + " para buscar un estudio");
        System.out.println(OPCION_VER_TODO + " para ver todos los estudios");
        System.out.println(OPCION_CREAR + " para crear un nuevo estudio");
        System.out.println(OPCION_EDITAR + " para editar un estudio");
        System.out.println(OPCION_ELIMINAR + " para eliminar un estudio");
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
            keyboard.nextLine();
            return leerOpcion(keyboard);
        }
    }

    /**
     * Pide datos para un Study. Si existe la persona/profesión por id, pregunta
     * si reutilizar datos existentes (S/n). Si 'isEdit' es true, se permite dejar
     * campos vacíos para no sobrescribir.
     */
    private Study pedirEstudioPorTeclado(EstudioInputAdapterCli estudioAdapter, Scanner keyboard, boolean isEdit) {
        try {
            System.out.print("Ingrese personId (numero): ");
            Integer pid = Integer.parseInt(keyboard.nextLine().trim());
            Person person = estudioAdapter.buscarPersonaPorId(pid);
            if (person != null) {
                System.out.println("Persona encontrada: " + person);
                System.out.print("Usar datos existentes de la persona? (S/n): ");
                String use = keyboard.nextLine().trim();
                if (use.isEmpty() || use.equalsIgnoreCase("S")) {
                    // reutilizar persona existente
                } else {
                    // pedir datos nuevos y reemplazarlos
                    person = pedirPersonaDesdeTecladoParaId(pid, keyboard, isEdit);
                }
            } else {
                // no existe: pedir datos
                person = pedirPersonaDesdeTecladoParaId(pid, keyboard, isEdit);
            }

            System.out.print("Ingrese profession id (numero): ");
            Integer prid = Integer.parseInt(keyboard.nextLine().trim());
            Profession prof = estudioAdapter.buscarProfesionPorId(prid);
            if (prof != null) {
                System.out.println("Profesion encontrada: " + prof);
                System.out.print("Usar datos existentes de la profesion? (S/n): ");
                String usep = keyboard.nextLine().trim();
                if (usep.isEmpty() || usep.equalsIgnoreCase("S")) {
                    // reuse
                } else {
                    prof = pedirProfesionDesdeTecladoParaId(prid, keyboard, isEdit);
                }
            } else {
                prof = pedirProfesionDesdeTecladoParaId(prid, keyboard, isEdit);
            }

            System.out.print("Ingrese graduationDate (YYYY-MM-DD) o vacío: ");
            String dateStr = keyboard.nextLine().trim();
            System.out.print("Ingrese universityName: ");
            String uni = keyboard.nextLine().trim();

            Study s = new Study(person, prof, null, uni);
            if (!dateStr.isEmpty()) {
                try {
                    s.setGraduationDate(LocalDate.parse(dateStr));
                } catch (DateTimeParseException ex) {
                    System.out.println("Fecha con formato inválido, se omite.");
                }
            }
            return s;
        } catch (Exception e) {
            System.out.println("Error leyendo datos: " + e.getMessage());
            return null;
        }
    }

    private Person pedirPersonaDesdeTecladoParaId(Integer pid, Scanner keyboard, boolean isEdit) {
        try {
            System.out.print("Ingrese person firstName" + (isEdit ? " (Enter para omitir)" : "") + ": ");
            String pfname = keyboard.nextLine().trim();
            System.out.print("Ingrese person lastName" + (isEdit ? " (Enter para omitir)" : "") + ": ");
            String plname = keyboard.nextLine().trim();
            System.out.print("Ingrese person gender (MALE/FEMALE/OTHER) o vacío: ");
            String gender = keyboard.nextLine().trim().toUpperCase();

            Gender g = null;
            if (!gender.isEmpty()) {
                try {
                    g = Gender.valueOf(gender);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Gender inválido, se asignará null.");
                }
            }

            // Si es edición y el usuario dejó vacío, ponemos nulls para no sobrescribir fuera de usecase.
            return new Person(pid, pfname.isEmpty() ? null : pfname, plname.isEmpty() ? null : plname, g, null, null, null);
        } catch (Exception e) {
            System.out.println("Error leyendo persona: " + e.getMessage());
            return null;
        }
    }

    private Profession pedirProfesionDesdeTecladoParaId(Integer prid, Scanner keyboard, boolean isEdit) {
        try {
            System.out.print("Ingrese profession name" + (isEdit ? " (Enter para omitir)" : "") + ": ");
            String pname = keyboard.nextLine().trim();
            System.out.print("Ingrese profession description" + (isEdit ? " (Enter para omitir)" : "") + ": ");
            String pdesc = keyboard.nextLine().trim();
            return new Profession(prid, pname.isEmpty() ? null : pname, pdesc.isEmpty() ? null : pdesc, null);
        } catch (Exception e) {
            System.out.println("Error leyendo profesion: " + e.getMessage());
            return null;
        }
    }
}