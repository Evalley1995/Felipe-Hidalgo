/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pipe-
 */
public class VentaDAO {
   public int crear(String clienteRut, int equipoId, int precioFinal) throws Exception {
        String sql = "{CALL sp_venta_insert(?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, clienteRut);
            cs.setInt(2, equipoId);
            cs.setInt(3, precioFinal);
            cs.registerOutParameter(4, Types.INTEGER); // OUT id
            cs.executeUpdate();
            return cs.getInt(4);
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "{CALL sp_venta_delete(?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() == 1;
        }
    }

    public List<Venta> listar() throws Exception {
        String sql = "{CALL sp_venta_list()}";
        List<Venta> out = new ArrayList<>();
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                out.add(new Venta(
                    rs.getInt("id"),
                    rs.getTimestamp("fecha"),
                    rs.getString("cliente_rut"),
                    rs.getInt("equipo_id"),
                    rs.getInt("precio_final")
                ));
            }
        }
        return out;
    }
}
