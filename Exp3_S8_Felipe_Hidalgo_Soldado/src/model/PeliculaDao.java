/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.util.*;
/**
 *
 * @author pipe-
 */
public class PeliculaDao {
    public List<Pelicula> listar(Connection cn) throws SQLException {
        String sql = "SELECT id,titulo,director,anio,duracion,genero FROM Cartelera ORDER BY id";
        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Pelicula> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }

    public Optional<Pelicula> buscarPorId(Connection cn, int id) throws SQLException {
        String sql = "SELECT id,titulo,director,anio,duracion,genero FROM Cartelera WHERE id=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    public List<Pelicula> buscarPorTitulo(Connection cn, String patron) throws SQLException {
        String sql = "SELECT id,titulo,director,anio,duracion,genero FROM Cartelera WHERE titulo LIKE ? ORDER BY id";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + patron + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Pelicula> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    public List<Pelicula> filtrarPorGeneroYAnio(Connection cn, String genero, Integer anioDesde, Integer anioHasta)
            throws SQLException {
        StringBuilder sb = new StringBuilder(
            "SELECT id,titulo,director,anio,duracion,genero FROM Cartelera WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (genero != null && !genero.isBlank()) { sb.append(" AND genero=?");   params.add(genero); }
        if (anioDesde != null)                   { sb.append(" AND anio>=?");     params.add(anioDesde); }
        if (anioHasta != null)                   { sb.append(" AND anio<=?");     params.add(anioHasta); }
        sb.append(" ORDER BY id");

        try (PreparedStatement ps = cn.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                List<Pelicula> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    public int insertar(Connection cn, Pelicula p) throws SQLException {
        String sql = "INSERT INTO Cartelera(titulo,director,anio,duracion,genero) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setInt(3, p.getAnio());
            ps.setInt(4, p.getDuracion());
            ps.setString(5, p.getGenero());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) p.setId(keys.getInt(1));
                }
            }
            return rows;
        }
    }

    public int actualizar(Connection cn, Pelicula p) throws SQLException {
        String sql = "UPDATE Cartelera SET titulo=?,director=?,anio=?,duracion=?,genero=? WHERE id=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setInt(3, p.getAnio());
            ps.setInt(4, p.getDuracion());
            ps.setString(5, p.getGenero());
            ps.setInt(6, p.getId());
            return ps.executeUpdate();
        }
    }

    public int eliminarPorId(Connection cn, int id) throws SQLException {
        String sql = "DELETE FROM Cartelera WHERE id=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    private Pelicula map(ResultSet rs) throws SQLException {
        return new Pelicula(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("director"),
            rs.getInt("anio"),
            rs.getInt("duracion"),
            rs.getString("genero")
        );
    }
}
