package funcional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Gasto> gastos = List.of();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;

            while (continuar) {
                mostrarMenu();
                String opcion = scanner.nextLine().trim();

                try {
                    switch (opcion) {
                        case "1" -> {
                            System.out.print("Concepto: ");
                            String concepto = scanner.nextLine();

                            BigDecimal monto = leerMonto(
                                    scanner, "Monto del gasto: ");

                            System.out.print("Categoría: ");
                            String categoria = scanner.nextLine();

                            gastos = FuncionesGastos.registrarGasto(
                                    gastos, concepto, monto, categoria);

                            System.out.println(
                                    "Gasto registrado correctamente.");
                        }

                        case "2" -> mostrarGastos(
                                FuncionesGastos.listarGastos(gastos));

                        case "3" -> System.out.println(
                                "Total acumulado: $"
                                + FuncionesGastos.calcularTotal(gastos)
                                        .toPlainString());

                        case "4" -> FuncionesGastos
                                .obtenerGastoMayor(gastos)
                                .ifPresentOrElse(
                                        gasto -> System.out.println(
                                                "Gasto de mayor valor: "
                                                + gasto),
                                        () -> System.out.println(
                                                "Todavía no hay gastos.")
                                );

                        case "5" -> {
                            BigDecimal limite = leerMonto(
                                    scanner, "Monto de referencia: ");

                            mostrarGastos(
                                    FuncionesGastos.filtrarPorMonto(
                                            gastos, limite));
                        }

                        case "6" -> {
                            System.out.print("Texto que deseas buscar: ");
                            String texto = scanner.nextLine();

                            mostrarGastos(
                                    FuncionesGastos.buscarPorConcepto(
                                            gastos, texto));
                        }

                        case "0" -> {
                            continuar = false;
                            System.out.println("Programa finalizado.");
                        }

                        default -> System.out.println("Opción inválida.");
                    }
                } catch (IllegalArgumentException error) {
                    System.out.println("Error: " + error.getMessage());
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nCONTROL DE GASTOS — VERSIÓN FUNCIONAL");
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
            return new BigDecimal(entrada);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(
                    "Ingresa un número válido, por ejemplo 15000.50.");
        }
    }

    private static void mostrarGastos(List<Gasto> gastos) {
        if (gastos.isEmpty()) {
            System.out.println("No hay gastos para mostrar.");
            return;
        }

        gastos.forEach(System.out::println);
    }
}