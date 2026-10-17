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
            System.out.println("1. Registre gasto");
            System.out.println("2. Muestre listado de gastos");
            System.out.println("3. Calcule gasto total acumulado");
            System.out.println("4. Muestre el gasto de mayor valor");
            System.out.println("5. Muestre gastos mayores a un monto específico");
            System.out.println("6. Busque gasto por concepto");
            System.out.println("7. Salir");
            System.out.print("Seleccione una de tantas: ");

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
                System.out.print("Ingrese el monto mínimo ($) a filtrar: ");
                double umbral = Double.parseDouble(scanner.nextLine());
                filtrarPorMonto(conceptos, montos, categorias, umbral);
            } else if (opcion == 6) {
                System.out.print("coloque palabra clave o concepto a buscar: ");
                String palabraClave = scanner.nextLine();
                buscarPorConcepto(conceptos, montos, categorias, palabraClave);
            } else if (opcion == 7) {
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("que fué, asi no es. Intente de nuevo.");
            }
        }
    }
    // 1. acá Registrar gasto
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
    // 2. con esto se muestra listado de gastos
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
    // 3.Calcula y retorna el gasto total acum
    public static double calcularTotal(ArrayList<Double> montos) {
        double sumaTotal = 0; // esto acumula el total .. que luego se me olvida jaja salud
        for (int i = 0; i < montos.size(); i++) {
            sumaTotal = sumaTotal + montos.get(i);
        }
        return sumaTotal;
    }
    // 4. Identificar y desplegar el gasto de mayor valor
    public static void mostrarMayorGasto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias) {
        if (montos.isEmpty()) {
            System.out.println("No hubo gastos registrados para evaluar.");
            return;
        }

        int indiceMayor = 0;
        double montoMaximo = montos.get(0);

        for (int i = 1; i < montos.size(); i++) {
            if (montos.get(i) > montoMaximo) {
                montoMaximo = montos.get(i);
                indiceMayor = i;
            }
        }

        System.out.println("\n--- GASTO DE MAYOR VALOR ---");
        System.out.println("Concepto: " + conceptos.get(indiceMayor)
                + " | Monto: $" + montos.get(indiceMayor)
                + " | Categoría: " + categorias.get(indiceMayor));
    }

    // 5.Filtra y lista gastos que supera un umbral específico
    public static void filtrarPorMonto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias, double umbral) {
        boolean encontrados = false;
        System.out.println("\n--- GASTOS MAYORES A $" + umbral + " ---");

        for (int i = 0; i < montos.size(); i++) {
            if (montos.get(i) > umbral) {
                System.out.println("- Concepto: " + conceptos.get(i)
                        + " | Monto: $" + montos.get(i)
                        + " | Categoría: " + categorias.get(i));
                encontrados = true;
            }
        }

        if (!encontrados) {
            System.out.println("No se encontraron gastos que superen los $" + umbral);
        }
    }

    // Buscar gasto por coincidencia de subcadena en el concepto
    public static void buscarPorConcepto(ArrayList<String> conceptos, ArrayList<Double> montos, ArrayList<String> categorias, String palabraClave) {
        boolean encontrados = false;
        String busquedaMinuscula = palabraClave.toLowerCase();
        System.out.println("\n--- RESULTADOS DE BÚSQUEDA PARA: '" + palabraClave + "' ---");

        for (int i = 0; i < conceptos.size(); i++) {
            if (conceptos.get(i).toLowerCase().contains(busquedaMinuscula)) {
                System.out.println("- Concepto: " + conceptos.get(i)
                        + " | Monto: $" + montos.get(i)
                        + " | Categoría: " + categorias.get(i));
                encontrados = true;
            }
        }

        if (!encontrados) {
            System.out.println("No se encontraron coincidencias para: " + palabraClave);
        }
    }
}
