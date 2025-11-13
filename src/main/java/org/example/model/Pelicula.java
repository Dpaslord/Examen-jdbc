package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Modelo que representa un producto de tipo videojuego.
 * <p>
 * Contiene los campos básicos: id, nombre, desarrollador, año, género y plataforma.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula implements Serializable {
    /** Identificador único de la pelicula. */
    private Integer id;

    /** Titulo del pelicula. */
    private String titulo;

    /** Genero pelicula */
    private String genero;

    /** Año de lanzamiento. */
    private Integer año;

    /** Director de pelicula */
    private String director;

}
