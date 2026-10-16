package poo;

import java.math.BigDecimal;

public class Gasto {

    private final String concepto;
    private final BigDecimal monto;
    private final String categoria;

    public Gasto(String concepto, BigDecimal monto, String categoria) {
        if (concepto == null || concepto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El concepto no puede estar vacío.");
        }

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor que cero.");
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La categoría no puede estar vacía.");
        }

        this.concepto = concepto.trim();
        this.monto = monto;
        this.categoria = categoria.trim();
    }

    public String getConcepto() {
        return concepto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Concepto: " + concepto
                + " | Monto: $" + monto.toPlainString()
                + " | Categoría: " + categoria;
    }
}