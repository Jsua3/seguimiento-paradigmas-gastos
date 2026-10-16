package funcional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public final class FuncionesGastos {

    private FuncionesGastos() {
        // Esta clase agrupa funciones; no necesita instancias.
    }

    public static List<Gasto> registrarGasto(
            List<Gasto> gastos,
            String concepto,
            BigDecimal monto,
            String categoria) {

        Gasto nuevo = new Gasto(concepto, monto, categoria);

        // Construye una nueva lista sin modificar la anterior.
        return Stream.concat(
                gastos.stream(),
                Stream.of(nuevo)
        ).toList();
    }

    public static List<Gasto> listarGastos(List<Gasto> gastos) {
        return List.copyOf(gastos);
    }

    public static BigDecimal calcularTotal(List<Gasto> gastos) {
        return gastos.stream()
                .map(Gasto::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Optional<Gasto> obtenerGastoMayor(
            List<Gasto> gastos) {

        return gastos.stream()
                .max(Comparator.comparing(Gasto::monto));
    }

    public static List<Gasto> filtrarPorMonto(
            List<Gasto> gastos, BigDecimal limite) {

        if (limite == null) {
            throw new IllegalArgumentException(
                    "Debes indicar un monto de referencia.");
        }

        return gastos.stream()
                .filter(gasto ->
                        gasto.monto().compareTo(limite) > 0)
                .toList();
    }

    public static List<Gasto> buscarPorConcepto(
            List<Gasto> gastos, String texto) {

        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "La búsqueda no puede estar vacía.");
        }

        String consulta = texto.trim().toLowerCase(Locale.ROOT);

        return gastos.stream()
                .filter(gasto ->
                        gasto.concepto()
                                .toLowerCase(Locale.ROOT)
                                .contains(consulta))
                .toList();
    }
}