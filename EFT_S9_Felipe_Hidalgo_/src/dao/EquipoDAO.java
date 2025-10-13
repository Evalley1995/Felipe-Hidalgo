/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.EquipoDesktop;
import model.EquipoLaptop;
import model.EquipoListado;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

  public int insertarDesktop(EquipoDesktop d) throws Exception {
        String sql = "{CALL sp_equipo_insert_desktop(?,?,?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, d.getModelo());
            cs.setString(2, d.getCpu());
            cs.setInt(3, d.getDiscoMb());
            cs.setInt(4, d.getRamGb());
            cs.setInt(5, d.getPrecio());
            cs.setInt(6, d.getFuenteW());
            cs.setString(7, d.getFactor());
            cs.registerOutParameter(8, Types.INTEGER); // OUT id
            cs.executeUpdate();
            return cs.getInt(8);
        }
    }

    public boolean actualizarDesktop(EquipoDesktop d) throws Exception {
        String sql = "{CALL sp_equipo_update_desktop(?,?,?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, d.getId());
            cs.setString(2, d.getModelo());
            cs.setString(3, d.getCpu());
            cs.setInt(4, d.getDiscoMb());
            cs.setInt(5, d.getRamGb());
            cs.setInt(6, d.getPrecio());
            cs.setInt(7, d.getFuenteW());
            cs.setString(8, d.getFactor());
            return cs.executeUpdate() == 1;
        }
    }

    public int insertarLaptop(EquipoLaptop l) throws Exception {
        String sql = "{CALL sp_equipo_insert_laptop(?,?,?,?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, l.getModelo());
            cs.setString(2, l.getCpu());
            cs.setInt(3, l.getDiscoMb());
            cs.setInt(4, l.getRamGb());
            cs.setInt(5, l.getPrecio());
            cs.setBigDecimal(6, new BigDecimal(String.valueOf(l.getPantallaIn())));
            cs.setBoolean(7, l.isEsTouch());
            cs.setInt(8, l.getUsbCount());
            cs.registerOutParameter(9, Types.INTEGER); // OUT id
            cs.executeUpdate();
            return cs.getInt(9);
        }
    }

    public boolean actualizarLaptop(EquipoLaptop l) throws Exception {
        String sql = "{CALL sp_equipo_update_laptop(?,?,?,?,?,?,?,?,?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, l.getId());
            cs.setString(2, l.getModelo());
            cs.setString(3, l.getCpu());
            cs.setInt(4, l.getDiscoMb());
            cs.setInt(5, l.getRamGb());
            cs.setInt(6, l.getPrecio());
            cs.setBigDecimal(7, new BigDecimal(String.valueOf(l.getPantallaIn())));
            cs.setBoolean(8, l.isEsTouch());
            cs.setInt(9, l.getUsbCount());
            return cs.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "{CALL sp_equipo_delete(?)}";
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() == 1;
        }
    }

    public List<EquipoListado> listarTodos() throws Exception {
        String sql = "{CALL sp_equipo_list()}";
        List<EquipoListado> out = new ArrayList<>();
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                EquipoListado x = new EquipoListado();
                x.id      = rs.getInt("id");
                x.tipo    = rs.getString("tipo");
                x.modelo  = rs.getString("modelo");
                x.cpu     = rs.getString("cpu");
                x.discoMb = rs.getInt("disco_mb");
                x.ramGb   = rs.getInt("ram_gb");
                x.precio  = rs.getInt("precio");

                int fuente = rs.getInt("fuente_w");
                x.fuenteW  = rs.wasNull() ? null : fuente;
                x.factor   = rs.getString("factor");

                double pant = rs.getDouble("pantalla_in");
                x.pantallaIn = rs.wasNull() ? null : pant;
                boolean touch = rs.getBoolean("es_touch");
                x.esTouch = rs.wasNull() ? null : touch;
                int usb = rs.getInt("usb_count");
                x.usbCount = rs.wasNull() ? null : usb;

                out.add(x);
            }
        }
        return out;
    }
    
}
        
    

