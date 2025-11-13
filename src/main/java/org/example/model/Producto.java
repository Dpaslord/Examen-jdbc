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
public class Producto implements Serializable {
    /** Identificador único del producto. */
    private Integer id;

    /** Nombre del videojuego. */
    private String nombre;

    /** Desarrollador del videojuego. */
    private String desarrollador;

    /** Año de lanzamiento. */
    private Integer año;

    /** Género del videojuego. */
    private String genero;

    /** Plataforma en la que se lanzó. */
    private String plataforma;
}
