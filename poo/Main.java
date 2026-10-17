package poo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GestorGastos gestor = new GestorGastos();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;

            while (continuar) {
                mostrarMenu();
                String opcion = scanner.nextLine().trim();

                try {
                    switch (opcion) {
                        case "1": {
                            System.out.print("Concepto: ");
                            String concepto = scanner.nextLine();

                            BigDecimal monto = leerMonto(
                                    scanner, "Monto del gasto: ");

                            System.out.print("Categoría: ");
                            String categoria = scanner.nextLine();

                            gestor.agregarGasto(
                                    concepto, monto, categoria);

                            System.out.println(
                                    "Gasto registrado correctamente.");
                            break;
                        }

                        case "2": {
                            mostrarGastos(gestor.listarGastos());
                            break;
                        }

                        case "3": {
                            System.out.println(
                                    "Total acumulado: $"
                                    + gestor.calcularTotal()
                                            .toPlainString());
                            break;
                        }

                        case "4": {
                            Gasto mayor = gestor.obtenerGastoMayor();

                            if (mayor == null) {
                                System.out.println(
                                        "Todavía no hay gastos registrados.");
                            } else {
                                System.out.println(
                                        "Gasto de mayor valor:");
                                System.out.println(mayor);
                            }
                            break;
                        }

                        case "5": {
                            BigDecimal limite = leerMonto(
                                    scanner, "Monto de referencia: ");

                            mostrarGastos(
                                    gestor.filtrarPorMonto(limite));
                            break;
                        }

                        case "6": {
                            System.out.print(
                                    "Texto que deseas buscar: ");
                            String texto = scanner.nextLine();

                            mostrarGastos(
                                    gestor.buscarPorConcepto(texto));
                            break;
                        }

                        case "0": {
                            continuar = false;
                            System.out.println(
                                    "Programa finalizado.");
                            break;
                        }

                        default: {
                            System.out.println(
                                    "Opción inválida.");
                        }
                    }
                } catch (IllegalArgumentException error) {
                    System.out.println(
                            "Error: " + error.getMessage());
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nCONTROL DE GASTOS PERSONALES");
        System.out.println("1. Registrar gasto");
        System.out.println("2. Listar gastos");
        System.out.println("3. Calcular total acumulado");
        System.out.println("4. Mostrar gasto de mayor valor");
        System.out.println("5. Mostrar gastos mayores a un monto");
        System.out.println("6. Buscar por concepto");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static BigDecimal leerMonto(
            Scanner scanner, String mensaje) {

        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();

        try {
            // Usar punto decimal y no escribir separadores de miles.
            return new BigDecimal(entrada);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(
                    "Ingresa un número válido, por ejemplo 15000.50.");
        }
    }

    private static void mostrarGastos(List<Gasto> gastos) {
        if (gastos.isEmpty()) {
            System.out.println(
                    "No hay gastos para mostrar.");
            return;
        }

        for (int i = 0; i < gastos.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + gastos.get(i));
        }
    }
}
