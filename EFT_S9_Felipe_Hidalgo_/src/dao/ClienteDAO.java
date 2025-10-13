/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pipe-
 */
public class ClienteDAO {
     public boolean crear(Cliente c) throws Exception {
        String sql = "{CALL sp_cliente_insert(?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, c.getRut());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getDireccion());
            cs.setString(4, c.getComuna());
            cs.setString(5, c.getCorreo());
            cs.setString(6, c.getTelefono());
            return cs.executeUpdate() == 1;
        }
    }

    public boolean actualizar(Cliente c) throws Exception {
        String sql = "{CALL sp_cliente_update(?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, c.getRut());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getDireccion());
            cs.setString(4, c.getComuna());
            cs.setString(5, c.getCorreo());
            cs.setString(6, c.getTelefono());
            return cs.executeUpdate() == 1;
        }
    }

    public boolean eliminar(String rut) throws Exception {
        String sql = "{CALL sp_cliente_delete(?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, rut);
            return cs.executeUpdate() == 1;
        }
    }

    public List<Cliente> listar() throws Exception {
        String sql = "{CALL sp_cliente_list()}";
        List<Cliente> out = new ArrayList<>();
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                out.add(new Cliente(
                    rs.getString("rut"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("comuna"),
                    rs.getString("correo"),
                    rs.getString("telefono")
                ));
            }
        }
        return out;
    }
}
