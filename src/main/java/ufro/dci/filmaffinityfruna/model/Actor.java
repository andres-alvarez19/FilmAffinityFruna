package ufro.dci.filmaffinityfruna.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Actor {
    private String nombre;
    private Date FechaNacimiento;
    private String nacionalidad;
    private Map<String, String> urlsRedesSociales;
    private String wiki;
    private Set<Pelicula> filmografia;

    public Actor(String nombre, Date fechaNacimiento, String nacionalidad, Map<String, String> urlsRedesSociales, String wiki, Set<Pelicula> filmografia) {
        nombre = nombre;
        FechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.urlsRedesSociales = urlsRedesSociales;
        this.wiki = wiki;
        this.filmografia = filmografia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Map<String, String> getUrlsRedesSociales() {
        return urlsRedesSociales;
    }

    public void setUrlsRedesSociales(Map<String, String> urlsRedesSociales) {
        this.urlsRedesSociales = urlsRedesSociales;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public Set<Pelicula> getFilmografia() {
        return filmografia;
    }

    public void setFilmografia(Set<Pelicula> filmografia) {
        this.filmografia = filmografia;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "nombre='" + nombre + '\'' +
                ", FechaNacimiento=" + FechaNacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", urlsRedesSociales=" + urlsRedesSociales +
                ", wiki='" + wiki + '\'' +
                ", filmografia=" + filmografia +
                '}';
    }
}
