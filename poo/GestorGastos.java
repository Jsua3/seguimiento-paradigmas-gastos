package poo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GestorGastos {

    private final List<Gasto> gastos;

    public GestorGastos() {
        gastos = new ArrayList<>();
    }

    public void agregarGasto(
            String concepto, BigDecimal monto, String categoria) {

        Gasto gasto = new Gasto(concepto, monto, categoria);
        gastos.add(gasto);
    }

    public List<Gasto> listarGastos() {
        // Devuelve una copia para proteger la colección interna.
        return new ArrayList<>(gastos);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Gasto gasto : gastos) {
            total = total.add(gasto.getMonto());
        }

        return total;
    }

    public Gasto obtenerGastoMayor() {
        if (gastos.isEmpty()) {
            return null;
        }

        Gasto mayor = gastos.get(0);

        for (Gasto gasto : gastos) {
            if (gasto.getMonto().compareTo(mayor.getMonto()) > 0) {
                mayor = gasto;
            }
        }

        return mayor;
    }

    public List<Gasto> filtrarPorMonto(BigDecimal limite) {
        if (limite == null) {
            throw new IllegalArgumentException(
                    "Debes indicar un monto de referencia.");
        }

        List<Gasto> resultado = new ArrayList<>();

        for (Gasto gasto : gastos) {
            if (gasto.getMonto().compareTo(limite) > 0) {
                resultado.add(gasto);
            }
        }

        return resultado;
    }

    public List<Gasto> buscarPorConcepto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La búsqueda no puede estar vacía.");
        }

        String consulta = texto.trim().toLowerCase(Locale.ROOT);
        List<Gasto> resultado = new ArrayList<>();

        for (Gasto gasto : gastos) {
            String concepto = gasto.getConcepto()
                    .toLowerCase(Locale.ROOT);

            if (concepto.contains(consulta)) {
                resultado.add(gasto);
            }
        }

        return resultado;
    }
}