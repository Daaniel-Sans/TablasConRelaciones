package dao;

import pojo.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActorDAO {

    private static final String URL = "jdbc:mysql://localhost:3307/peliculas";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void insertarActor(Actor actor) {
        String sql = "INSERT INTO actores (nombre, nacionalidad, edad) VALUES (?, ?, ?)";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, actor.getNombre());
            ps.setString(2, actor.getNacionalidad());
            ps.setInt(3, actor.getEdad());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                actor.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarActor(int id, Actor actor) {
        String sql = "UPDATE actores SET nombre = ?, nacionalidad = ?, edad = ? WHERE id = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, actor.getNombre());
            ps.setString(2, actor.getNacionalidad());
            ps.setInt(3, actor.getEdad());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarActor(int id) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM reparto WHERE actor_id = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM actores WHERE id = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void asignarActorAPelicula(int actorId, int peliculaId, String personaje) {
        String sql = "INSERT INTO reparto (actor_id, pelicula_id, personaje) VALUES (?, ?, ?)";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, actorId);
            ps.setInt(2, peliculaId);
            ps.setString(3, personaje);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarActorDePelicula(int actorId, int peliculaId) {
        String sql = "DELETE FROM reparto WHERE actor_id = ? AND pelicula_id = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, actorId);
            ps.setInt(2, peliculaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> obtenerNumeroActoresPorNacionalidad() {
        Map<String, Integer> mapa = new LinkedHashMap<>();
        String sql = "SELECT nacionalidad, COUNT(*) AS total FROM actores GROUP BY nacionalidad";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mapa.put(rs.getString("nacionalidad"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    public double obtenerEdadMedia() {
        double media = 0;
        String sql = "SELECT AVG(edad) AS media FROM actores";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                media = rs.getDouble("media");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return media;
    }

    public List<Actor> obtenerActoresSinPelicula() {
        List<Actor> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad FROM actores a LEFT JOIN reparto r ON a.id = r.actor_id WHERE r.actor_id IS NULL";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actor a = new Actor(rs.getInt("id"), rs.getString("nombre"), rs.getString("nacionalidad"), rs.getInt("edad"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}