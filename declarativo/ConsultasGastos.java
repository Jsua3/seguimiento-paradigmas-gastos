package declarativo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Paradigma Declarativo - Las 6 funcionalidades expresadas como consultas SQL.
 *
 * Aquí se declara QUÉ datos se quieren obtener; el motor de base de datos
 * decide CÓMO recorrerlos. Ningún cálculo (suma, máximo, filtro, búsqueda)
 * se hace con ciclos en Java: todo se delega a sentencias SQL.
 * Todas las consultas son parametrizadas (?) para evitar inyección SQL.
 */
public class ConsultasGastos {

    private final Connection conexion;

    public ConsultasGastos(Connection conexion) {
        this.conexion = conexion;
    }

    /** 1. Registrar gasto (INSERT). */
    public void registrarGasto(String concepto, double monto, String categoria) throws SQLException {
        String sql = "INSERT INTO gastos (concepto, monto, categoria) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, concepto);
            ps.setDouble(2, monto);
            ps.setString(3, categoria);
            ps.executeUpdate();
        }
    }

    /** 2. Listado de todos los gastos ordenados por categoría y monto. */
    public List<Map<String, Object>> listarGastos() throws SQLException {
        return consultar("""
                SELECT id, concepto, monto, categoria
                FROM gastos
                ORDER BY categoria, monto DESC
                """);
    }

    /** 3. Total acumulado (SUM). COALESCE devuelve 0 si no hay registros. */
    public BigDecimal calcularTotal() throws SQLException {
        List<Map<String, Object>> filas =
                consultar("SELECT COALESCE(SUM(monto), 0) AS total FROM gastos");
        return new BigDecimal(filas.get(0).get("total").toString());
    }

    /** 4. Gasto(s) de mayor valor (subconsulta con MAX). Incluye empates. */
    public List<Map<String, Object>> gastoMayor() throws SQLException {
        return consultar("""
                SELECT id, concepto, monto, categoria
                FROM gastos
                WHERE monto = (SELECT MAX(monto) FROM gastos)
                """);
    }

    /** 5. Gastos cuyo monto supera un umbral (WHERE monto > ?). */
    public List<Map<String, Object>> gastosMayoresA(double umbral) throws SQLException {
        return consultar("""
                SELECT id, concepto, monto, categoria
                FROM gastos
                WHERE monto > ?
                ORDER BY monto DESC
                """, umbral);
    }

    /** 6. Búsqueda por subcadena en el concepto (LIKE '%palabra%'). */
    public List<Map<String, Object>> buscarPorConcepto(String palabra) throws SQLException {
        return consultar("""
                SELECT id, concepto, monto, categoria
                FROM gastos
                WHERE concepto LIKE ?
                ORDER BY concepto
                """, "%" + palabra + "%");
    }

    /** Ejecuta un SELECT parametrizado y devuelve cada fila como un mapa columna -> valor. */
    private List<Map<String, Object>> consultar(String sql, Object... parametros) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                List<Map<String, Object>> filas = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> fila = new LinkedHashMap<>();
                    for (int c = 1; c <= meta.getColumnCount(); c++) {
                        fila.put(meta.getColumnLabel(c), rs.getObject(c));
                    }
                    filas.add(fila);
                }
                return filas;
            }
        }
    }
}
