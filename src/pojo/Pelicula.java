package pojo;

import java.math.BigDecimal;

public class Pelicula {

    private int id;
    private String titulo;
    private String genero;
    private int duracion;
    private BigDecimal presupuesto;

    public Pelicula() {
    }

    public Pelicula(String titulo, String genero, int duracion, BigDecimal presupuesto) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.presupuesto = presupuesto;
    }

    public Pelicula(int id, String titulo, String genero, int duracion, BigDecimal presupuesto) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.presupuesto = presupuesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Override
    public String toString() {
        return "Pelicula [id=" + id + ", titulo=" + titulo + ", genero=" + genero + ", duracion=" + duracion + ", presupuesto=" + presupuesto + "]";
    }
}