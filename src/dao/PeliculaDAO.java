package dao;

import pojo.Actor;
import pojo.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    private static final String URL = "jdbc:mysql://localhost:3307/peliculas";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void insertarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO peliculas (titulo, genero, duracion, presupuesto) VALUES (?, ?, ?, ?)";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getDuracion());
            ps.setBigDecimal(4, pelicula.getPresupuesto());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pelicula.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPelicula(int id, Pelicula pelicula) {
        String sql = "UPDATE peliculas SET titulo = ?, genero = ?, duracion = ?, presupuesto = ? WHERE id = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getDuracion());
            ps.setBigDecimal(4, pelicula.getPresupuesto());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarPelicula(int id) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM reparto WHERE pelicula_id = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM peliculas WHERE id = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> obtenerTodasConNumeroActores() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.titulo, COUNT(r.actor_id) AS num_actores FROM peliculas p LEFT JOIN reparto r ON p.id = r.pelicula_id GROUP BY p.id, p.titulo";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String linea = "ID: " + rs.getInt("id") + " | " + rs.getString("titulo") + " | Actores: " + rs.getInt("num_actores");
                lista.add(linea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Actor> obtenerActoresDePelicula(int peliculaId) {
        List<Actor> actores = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad FROM actores a JOIN reparto r ON a.id = r.actor_id WHERE r.pelicula_id = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, peliculaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actor a = new Actor(rs.getInt("id"), rs.getString("nombre"), rs.getString("nacionalidad"), rs.getInt("edad"));
                actores.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actores;
    }

    public List<Pelicula> obtenerPeliculasConMasDe3Actores() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.titulo, p.genero, p.duracion, p.presupuesto FROM peliculas p JOIN reparto r ON p.id = r.pelicula_id GROUP BY p.id, p.titulo, p.genero, p.duracion, p.presupuesto HAVING COUNT(r.actor_id) > 3";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pelicula p = new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"), rs.getInt("duracion"), rs.getBigDecimal("presupuesto"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Pelicula> obtener3PeliculasMayorPresupuesto() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, genero, duracion, presupuesto FROM peliculas ORDER BY presupuesto DESC LIMIT 3";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pelicula p = new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"), rs.getInt("duracion"), rs.getBigDecimal("presupuesto"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Pelicula obtenerPeliculaMasLargaDeGenero(String genero) {
        Pelicula pelicula = null;
        String sql = "SELECT id, titulo, genero, duracion, presupuesto FROM peliculas WHERE genero = ? ORDER BY duracion DESC LIMIT 1";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pelicula = new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"), rs.getInt("duracion"), rs.getBigDecimal("presupuesto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pelicula;
    }
}