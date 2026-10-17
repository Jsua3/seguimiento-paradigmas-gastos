package funcional;
import java.math.BigDecimal;

public record Gasto(
        String concepto,
        BigDecimal monto,
        String categoria) {

    public Gasto {
        if (concepto == null || concepto.isBlank()) {
            throw new IllegalArgumentException(
                    "El concepto no puede estar vacío.");
        }

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor que cero.");
        }

        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException(
                    "La categoría no puede estar vacía.");
        }

        concepto = concepto.trim();
        categoria = categoria.trim();
    }

    @Override
    public String toString() {
        return "Concepto: " + concepto
                + " | Monto: $" + monto.toPlainString()
                + " | Categoría: " + categoria;
    }
}