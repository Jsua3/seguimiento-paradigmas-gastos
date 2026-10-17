package declarativo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Paradigma Declarativo - Sistema de Control de Gastos Personales (MySQL + JDBC).
 * Menú de consola que invoca las consultas SQL de ConsultasGastos.
 */
public class Main {

    private static final String[] CATEGORIAS =
            {"Alimentación", "Transporte", "Servicios", "Entretenimiento", "Otros"};

    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.conectar()) {
            ConsultasGastos consultas = new ConsultasGastos(conexion);
            ejecutarMenu(consultas);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a MySQL: " + e.getMessage());
            System.out.println("Verifica que el servidor esté encendido, que el driver "
                    + "MySQL Connector/J esté agregado y que las credenciales sean correctas.");
        }
    }

    private static void ejecutarMenu(ConsultasGastos consultas) throws SQLException {
        String opciones = """

                ===== CONTROL DE GASTOS (Paradigma Declarativo - MySQL) =====
                1. Registrar gasto
                2. Mostrar listado de gastos
                3. Calcular gasto total acumulado
                4. Mostrar el gasto de mayor valor
                5. Mostrar gastos mayores a un monto
                6. Buscar gasto por concepto
                0. Salir""";

        while (true) {
            System.out.println(opciones);
            System.out.print("Selecciona una opción: ");
            String opcion = entrada.nextLine().trim();

            switch (opcion) {
                case "1" -> {
                    System.out.print("  Concepto: ");
                    String concepto = entrada.nextLine().trim();
                    if (concepto.isEmpty()) {
                        System.out.println("  El concepto no puede estar vacío.");
                        continue;
                    }
                    double monto = pedirMonto("  Monto: $");
                    String categoria = pedirCategoria();
                    consultas.registrarGasto(concepto, monto, categoria);
                    System.out.println("  Gasto registrado correctamente.");
                }
                case "2" -> mostrarGastos(consultas.listarGastos());
                case "3" -> System.out.printf("  Total acumulado: $%,.2f%n", consultas.calcularTotal());
                case "4" -> mostrarGastos(consultas.gastoMayor());
                case "5" -> {
                    double umbral = pedirMonto("  Mostrar gastos mayores a: $");
                    mostrarGastos(consultas.gastosMayoresA(umbral));
                }
                case "6" -> {
                    System.out.print("  Palabra a buscar: ");
                    String palabra = entrada.nextLine().trim();
                    mostrarGastos(consultas.buscarPorConcepto(palabra));
                }
                case "0" -> {
                    System.out.println("Hasta luego.");
                    return;
                }
                default -> System.out.println("  Opción no válida.");
            }
        }
    }

    /** Imprime una lista de gastos en formato de tabla. */
    private static void mostrarGastos(List<Map<String, Object>> filas) {
        if (filas.isEmpty()) {
            System.out.println("  No se encontraron gastos.");
            return;
        }
        System.out.printf("  %-4s%-25s%14s  %s%n", "ID", "Concepto", "Monto", "Categoría");
        System.out.println("  " + "-".repeat(62));
        for (Map<String, Object> g : filas) {
            System.out.printf("  %-4s%-25s%,14.2f  %s%n",
                    g.get("id"), g.get("concepto"), g.get("monto"), g.get("categoria"));
        }
    }

    /** Solicita un número positivo hasta que el usuario lo ingrese bien. */
    private static double pedirMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(entrada.nextLine().trim());
                if (valor > 0) {
                    return valor;
                }
                System.out.println("  El monto debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("  Ingresa un número válido (sin puntos de miles, ej. 15000).");
            }
        }
    }

    /** Muestra las categorías disponibles y devuelve la elegida. */
    private static String pedirCategoria() {
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, CATEGORIAS[i]);
        }
        while (true) {
            System.out.print("  Elige la categoría (número): ");
            String opcion = entrada.nextLine().trim();
            if (opcion.matches("\\d+")) {
                int n = Integer.parseInt(opcion);
                if (n >= 1 && n <= CATEGORIAS.length) {
                    return CATEGORIAS[n - 1];
                }
            }
            System.out.println("  Opción no válida.");
        }
    }
}
