import dao.ActorDAO;
import dao.PeliculaDAO;
import pojo.Actor;
import pojo.Pelicula;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/peliculas";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión establecida");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
        }

        PeliculaDAO peliculaDAO = new PeliculaDAO();
        ActorDAO actorDAO = new ActorDAO();

        // --- PELICULA DAO ---

        System.out.println("\nInsertar pelicula:");
        Pelicula p = new Pelicula("Luz de Medianoche", "Thriller", 118, new BigDecimal("22000000"));
        peliculaDAO.insertarPelicula(p);
        System.out.println(p);

        System.out.println("\nActualizar pelicula:");
        p.setTitulo("Luz de Medianoche 2");
        p.setPresupuesto(new BigDecimal("28000000"));
        peliculaDAO.actualizarPelicula(p.getId(), p);
        System.out.println(p);

        System.out.println("\nBorrar pelicula:");
        peliculaDAO.borrarPelicula(p.getId());
        System.out.println("Pelicula con id " + p.getId() + " borrada");

        System.out.println("\nTodas las peliculas con numero de actores:");
        List<String> peliculasConActores = peliculaDAO.obtenerTodasConNumeroActores();
        for (String linea : peliculasConActores) {
            System.out.println(linea);
        }

        System.out.println("\nActores de la pelicula con id 1:");
        List<Actor> actoresPelicula = peliculaDAO.obtenerActoresDePelicula(1);
        for (Actor a : actoresPelicula) {
            System.out.println(a);
        }

        System.out.println("\nPeliculas con mas de 3 actores:");
        List<Pelicula> masde3 = peliculaDAO.obtenerPeliculasConMasDe3Actores();
        for (Pelicula pelicula : masde3) {
            System.out.println(pelicula);
        }

        System.out.println("\nTop 3 peliculas con mayor presupuesto:");
        List<Pelicula> top3 = peliculaDAO.obtener3PeliculasMayorPresupuesto();
        for (Pelicula pelicula : top3) {
            System.out.println(pelicula);
        }

        System.out.println("\nPelicula mas larga del genero Accion:");
        Pelicula masLarga = peliculaDAO.obtenerPeliculaMasLargaDeGenero("Acción");
        System.out.println(masLarga);

        // --- ACTOR DAO ---

        System.out.println("\nInsertar actor:");
        Actor a = new Actor("Laura Garcia", "España", 33);
        actorDAO.insertarActor(a);
        System.out.println(a);

        System.out.println("\nActualizar actor:");
        a.setNombre("Laura Garcia Lopez");
        a.setEdad(34);
        actorDAO.actualizarActor(a.getId(), a);
        System.out.println(a);

        System.out.println("\nBorrar actor:");
        actorDAO.borrarActor(a.getId());
        System.out.println("Actor con id " + a.getId() + " borrado");

        System.out.println("\nAsignar actor 1 a pelicula 5:");
        actorDAO.asignarActorAPelicula(1, 5, "Guerrero Oscuro");
        System.out.println("Actor 1 asignado a pelicula 5");

        System.out.println("\nEliminar actor 1 de pelicula 5:");
        actorDAO.eliminarActorDePelicula(1, 5);
        System.out.println("Actor 1 eliminado de pelicula 5");

        System.out.println("\nNumero de actores por nacionalidad:");
        Map<String, Integer> porNacionalidad = actorDAO.obtenerNumeroActoresPorNacionalidad();
        for (Map.Entry<String, Integer> entry : porNacionalidad.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nEdad media de los actores:");
        double edadMedia = actorDAO.obtenerEdadMedia();
        System.out.println(edadMedia);

        System.out.println("\nActores sin pelicula:");
        List<Actor> sinPelicula = actorDAO.obtenerActoresSinPelicula();
        if (sinPelicula.isEmpty()) {
            System.out.println("Todos los actores tienen pelicula");
        } else {
            for (Actor actor : sinPelicula) {
                System.out.println(actor);
            }
        }
    }
}