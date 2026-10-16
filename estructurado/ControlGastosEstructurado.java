package estructurado;

import java.util.ArrayList;
import java.util.Scanner;

public class ControlGastosEstructurado {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<String> conceptos = new ArrayList<>();
        ArrayList<Double> montos = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>();

        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n=== SISTEMA DE CONTROL DE GASTOS (PARADIGMA ESTRUCTURADO) ===");
            System.out.println("1. Registrar gasto");
            System.out.println("2. Mostrar listado de gastos");
            System.out.println("3. Calcular gasto total acumulado");
            System.out.println("4. Mostrar el gasto de mayor valor");
            System.out.println("5. Mostrar gastos mayores a un monto específico");
            System.out.println("6. Buscar gasto por concepto");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                registrarGasto(conceptos, montos, categorias);
            } else if (opcion == 2) {
                mostrarGastos(conceptos, montos, categorias);
            } else if (opcion == 3) {
                double total = calcularTotal(montos);
                System.out.println("El gasto total acumulado es: $" + total);
            } else if (opcion == 4) {
                mostrarMayorGasto(conceptos, montos, categorias);
            } else if (opcion == 5) {
                System.out.print("Ingrese el monto mínimo (umbral $) a filtrar: ");
                double umbral = Double.parseDouble(scanner.nextLine());
                filtrarPorMonto(conceptos, montos, categorias, umbral);
            } else if (opcion == 6) {
                System.out.print("Ingrese palabra clave o concepto a buscar: ");
                String palabraClave = scanner.nextLine();
                buscarPorConcepto(conceptos, montos, categorias, palabraClave);
            } else if (opcion == 7) {
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public static void registrarGasto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias) {
        System.out.print("Ingrese el concepto (descripción): ");
        String concepto = scanner.nextLine();

        System.out.print("Ingrese el monto ($): ");
        double monto = Double.parseDouble(scanner.nextLine());

        if (monto <= 0) {
            System.out.println("Error: El monto debe ser mayor a cero.");
            return;
        }

        System.out.print("Ingrese la categoría (ej. Alimentación, Transporte, Servicios, Entretenimiento): ");
        String categoria = scanner.nextLine();

        conceptos.add(concepto);
        montos.add(monto);
        categorias.add(categoria);

        System.out.println("¡Gasto registrado exitosamente!");
    }

    public static void mostrarGastos(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias) {
        if (conceptos.isEmpty()) {
            System.out.println("No hay gastos registrados aún.");
        } else {
            System.out.println("\n--- LISTADO TOTAL DE GASTOS ---");
            for (int i = 0; i < conceptos.size(); i++) {
                System.out.println((i + 1) + ". Concepto: " + conceptos.get(i)
                        + " | Monto: $" + montos.get(i)
                        + " | Categoría: " + categorias.get(i));
            }
        }
    }

    public static double calcularTotal(ArrayList<Double> montos) { return 0; }
    public static void mostrarMayorGasto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias) {}
    public static void filtrarPorMonto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias, double umbral) {}
    public static void buscarPorConcepto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias, String palabraClave) {}
}