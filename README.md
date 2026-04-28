# Actividad Entregable 2 - Gestión de Películas y Actores

Aplicación en Java con acceso a base de datos MySQL mediante JDBC. Permite gestionar películas y actores a través del patrón DAO.

## Tecnologías

- Java
- MySQL
- JDBC

## Estructura del proyecto

```
src/
├── Main.java
├── dao/
│   ├── PeliculaDAO.java
│   └── ActorDAO.java
└── pojo/
    ├── Pelicula.java
    └── Actor.java
```

## Base de datos

La base de datos se llama `peliculas` y tiene tres tablas:

- **peliculas** – id, titulo, genero, duracion, presupuesto
- **actores** – id, nombre, nacionalidad, edad
- **reparto** – actor_id, pelicula_id, personaje (tabla intermedia)

### Creación de tablas

```sql
CREATE TABLE peliculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    genero VARCHAR(50),
    duracion INT,
    presupuesto DECIMAL(10,2)
);

CREATE TABLE actores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    nacionalidad VARCHAR(50),
    edad INT
);

CREATE TABLE reparto (
    actor_id INT,
    pelicula_id INT,
    PRIMARY KEY (actor_id, pelicula_id),
    personaje VARCHAR(100),
    FOREIGN KEY (actor_id) REFERENCES actores(id),
    FOREIGN KEY (pelicula_id) REFERENCES peliculas(id)
);
```

### Datos de prueba

```sql
INSERT INTO peliculas (titulo, genero, duracion, presupuesto) VALUES
('Invasión Urbana', 'Acción', 125, 15000000),
('El Último Verano', 'Drama', 110, 25000000),
('Código Omega', 'Thriller', 130, 80000000),
('Risas en Manhattan', 'Comedia', 95, 30000000),
('Reino de Sombras', 'Fantasía', 145, 12000000),
('Horizonte Perdido', 'Aventura', 140, 90000000),
('Neón Nocturno', 'Ciencia Ficción', 135, 11000000),
('El Caso Blackwood', 'Misterio', 115, 40000000),
('Amor en París', 'Romance', 105, 35000000),
('Guerra Final', 'Bélica', 150, 20000000);

INSERT INTO actores (nombre, nacionalidad, edad) VALUES
('Daniel Pérez', 'España', 38),
('Lucía Fernández', 'España', 29),
('Michael Carter', 'EEUU', 45),
('Sophie Dubois', 'Francia', 34),
('Kenji Tanaka', 'Japón', 41),
('Carlos Méndez', 'México', 36),
('Emily Johnson', 'EEUU', 27),
('Marco Rossi', 'Italia', 50),
('Ana Silva', 'Brasil', 31),
('Oliver Brown', 'Reino Unido', 42);

INSERT INTO reparto (actor_id, pelicula_id, personaje) VALUES
(1, 1, 'Detective Ramírez'), (3, 1, 'Comandante Harris'), (7, 1, 'Hacktivista Zoe'),
(2, 2, 'Elena'), (4, 2, 'Claire'), (9, 2, 'Miguel'),
(3, 3, 'Agente Stone'), (5, 3, 'Dr. Nakamura'), (10, 3, 'Director Blake'),
(2, 4, 'Laura'), (7, 4, 'Amy'), (1, 4, 'Tom'),
(5, 5, 'Hechicero Kael'), (8, 5, 'Rey Aldric'), (4, 5, 'Maga Lys'),
(6, 6, 'Explorador Diego'), (9, 6, 'Aventurera Sofia'), (3, 6, 'Capitán Ward'),
(7, 7, 'Nora'), (10, 7, 'IA Nexus'), (1, 7, 'Detective Rook'),
(8, 8, 'Inspector Blackwood'), (4, 8, 'Emma Clarke'), (6, 8, 'Joven Testigo'),
(2, 9, 'Juliette'), (9, 9, 'Pierre'), (5, 9, 'Antoine'),
(3, 10, 'General Stone'), (10, 10, 'Coronel Wright'), (8, 10, 'Almirante Rossi');
```

## Funcionalidades

### PeliculaDAO

| Método | Descripción |
|---|---|
| `insertarPelicula` | Inserta una nueva película |
| `actualizarPelicula` | Actualiza los datos de una película por ID |
| `borrarPelicula` | Borra una película por ID |
| `obtenerTodasConNumeroActores` | Devuelve todas las películas con su número de actores |
| `obtenerActoresDePelicula` | Devuelve los actores de una película por ID |
| `obtenerPeliculasConMasDe3Actores` | Devuelve las películas con más de 3 actores |
| `obtener3PeliculasMayorPresupuesto` | Devuelve las 3 películas con mayor presupuesto |
| `obtenerPeliculaMasLargaDeGenero` | Devuelve la película más larga de un género dado |

### ActorDAO

| Método | Descripción |
|---|---|
| `insertarActor` | Inserta un nuevo actor |
| `actualizarActor` | Actualiza los datos de un actor por ID |
| `borrarActor` | Borra un actor por ID |
| `asignarActorAPelicula` | Asigna un actor a una película con su personaje |
| `eliminarActorDePelicula` | Elimina un actor de una película |
| `obtenerNumeroActoresPorNacionalidad` | Devuelve el número de actores agrupado por nacionalidad |
| `obtenerEdadMedia` | Devuelve la edad media de todos los actores |
| `obtenerActoresSinPelicula` | Devuelve los actores que no participan en ninguna película |
