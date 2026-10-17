package com.gastos.eventos;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParadigmaEventos extends JFrame {
    private final JTextField campoConcepto = new JTextField();
    private final JTextField campoMonto = new JTextField();
    private final JComboBox<String> campoCategoria = new JComboBox<>(new String[]{
            "Alimentación", "Transporte", "Servicios", "Entretenimiento"
    });
    private final JTextField campoMontoMinimo = new JTextField();
    private final JTextField campoBusqueda = new JTextField();
    private final JTextArea areaResultados = new JTextArea();
    private final List<Gasto> gastos = new ArrayList<>();

    public ParadigmaEventos() {
        setTitle("Sistema de Control de Gastos Personales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        panelCampos.add(new JLabel("Concepto:"));
        panelCampos.add(campoConcepto);
        panelCampos.add(new JLabel("Monto:"));
        panelCampos.add(campoMonto);
        panelCampos.add(new JLabel("Categoría:"));
        panelCampos.add(campoCategoria);
        panelCampos.add(new JLabel("Monto mínimo:"));
        panelCampos.add(campoMontoMinimo);
        panelCampos.add(new JLabel("Buscar concepto:"));
        panelCampos.add(campoBusqueda);

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 5, 5));
        JButton botonRegistrar = new JButton("Registrar Gasto");
        JButton botonTotal = new JButton("Calcular Total");
        JButton botonFiltrar = new JButton("Filtrar por Monto Mínimo");
        JButton botonBuscar = new JButton("Buscar por Concepto");
        JButton botonMostrar = new JButton("Mostrar Todos");
        JButton botonMayor = new JButton("Gasto Mayor");
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonTotal);
        panelBotones.add(botonFiltrar);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonMostrar);
        panelBotones.add(botonMayor);

        areaResultados.setEditable(false);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);

        panelPrincipal.add(panelCampos, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        botonRegistrar.addActionListener(evento -> registrarGasto());
        botonTotal.addActionListener(evento -> calcularTotal());
        botonFiltrar.addActionListener(evento -> filtrarPorMontoMinimo());
        botonBuscar.addActionListener(evento -> buscarPorConcepto());
        botonMostrar.addActionListener(evento -> mostrarTodos());
        botonMayor.addActionListener(evento -> mostrarGastoMayor());
        campoBusqueda.addActionListener(evento -> buscarPorConcepto());
    }

    private void registrarGasto() {
        String concepto = campoConcepto.getText().trim();
        String textoMonto = campoMonto.getText().trim();

        if (concepto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El concepto no puede estar vacío.");
            return;
        }

        try {
            double monto = Double.parseDouble(textoMonto);
            if (monto < 0) {
                JOptionPane.showMessageDialog(this, "El monto no puede ser negativo.");
                return;
            }

            String categoria = (String) campoCategoria.getSelectedItem();
            gastos.add(new Gasto(concepto, monto, categoria));
            campoConcepto.setText("");
            campoMonto.setText("");
            mostrarTodos();
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.");
        }
    }

    private void calcularTotal() {
        double total = 0;
        for (Gasto gasto : gastos) {
            total += gasto.monto;
        }
        areaResultados.setText(String.format(Locale.US, "Total acumulado: %.2f%n", total));
    }

    private void filtrarPorMontoMinimo() {
        try {
            double montoMinimo = Double.parseDouble(campoMontoMinimo.getText().trim());
            List<Gasto> gastosFiltrados = new ArrayList<>();
            for (Gasto gasto : gastos) {
                if (gasto.monto > montoMinimo) {
                    gastosFiltrados.add(gasto);
                }
            }
            mostrarGastos(gastosFiltrados, "Gastos con monto mayor a " + montoMinimo);
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "El monto mínimo debe ser un número válido.");
        }
    }

    private void buscarPorConcepto() {
        String texto = campoBusqueda.getText().trim().toLowerCase(Locale.ROOT);
        List<Gasto> gastosEncontrados = new ArrayList<>();
        for (Gasto gasto : gastos) {
            if (gasto.concepto.toLowerCase(Locale.ROOT).contains(texto)) {
                gastosEncontrados.add(gasto);
            }
        }
        mostrarGastos(gastosEncontrados, "Resultados de búsqueda");
    }

    private void mostrarTodos() {
        mostrarGastos(gastos, "Todos los gastos");
    }

    private void mostrarGastoMayor() {
        if (gastos.isEmpty()) {
            areaResultados.setText("No hay gastos registrados.");
            return;
        }

        Gasto gastoMayor = gastos.get(0);
        for (Gasto gasto : gastos) {
            if (gasto.monto > gastoMayor.monto) {
                gastoMayor = gasto;
            }
        }
        mostrarGastos(List.of(gastoMayor), "Gasto de mayor valor");
    }

    private void mostrarGastos(List<Gasto> lista, String titulo) {
        StringBuilder resultado = new StringBuilder(titulo).append(":\n");
        if (lista.isEmpty()) {
            resultado.append("No hay gastos para mostrar.\n");
        } else {
            for (Gasto gasto : lista) {
                resultado.append(String.format(
                        Locale.US, "Concepto: %s | Monto: %.2f | Categoría: %s%n",
                        gasto.concepto, gasto.monto, gasto.categoria));
            }
        }
        areaResultados.setText(resultado.toString());
    }

    private static class Gasto {
        private final String concepto;
        private final double monto;
        private final String categoria;

        private Gasto(String concepto, double monto, String categoria) {
            this.concepto = concepto;
            this.monto = monto;
            this.categoria = categoria;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ParadigmaEventos ventana = new ParadigmaEventos();
            ventana.setVisible(true);
        });
    }
}