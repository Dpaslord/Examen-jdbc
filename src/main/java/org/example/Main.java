package org.example;

import org.example.dao.PeliculaDAO;
import org.example.database.DataProvider;
import org.example.model.Pelicula;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource ds = DataProvider.getDataSource(
                "jdbc:mysql://localhost:3307/peliculas",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASSWORD")
        );
        PeliculaDAO peliculaDAO = new PeliculaDAO(ds);

        int contador = 0;
        int opcion = 0;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("---------Gestor Peliculas---------");
            System.out.println("1. Crear Pelicula");
            System.out.println("2. Cantidad de peliculas");
            System.out.println("3. Actualizar genero todas las peliculas");
            System.out.println("4. Salir");
            System.out.print("Escribe la opcion: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1 -> {
                    Pelicula pelicula = new Pelicula();
                    System.out.println("Ingrese el titulo de la pelicula");
                    String titulo = sc.next();
                    System.out.println("Ingrese el genero de la pelicula");
                    String genero = sc.next();
                    System.out.println("Ingrese el año de la pelicula");
                    Integer año = sc.nextInt();
                    System.out.println("Ingrese el director de la pelicula");
                    String director = sc.next();
                    pelicula.setTitulo(titulo);
                    pelicula.setGenero(genero);
                    pelicula.setAño(año);
                    pelicula.setDirector(director);
                    peliculaDAO.save(pelicula);
                }
                case 2 -> {
                    List<Pelicula> peliculas = peliculaDAO.findAll();
                    for (Pelicula pelicula : peliculas) {
                        contador++;
                    }
                    System.out.println("Numero de peliculas en la base de datos: " + contador);
                    contador = 0;
                }
                case 3 -> {

                    System.out.println("A que genero quieres cambiarle el genero: ");
                    String genero = sc.next();
                    System.out.println("Por cual lo quieres cambiar");
                    String genero2 = sc.next();


                    List<Pelicula> peliculas = peliculaDAO.findByGenero(genero);

                    for (Pelicula pelicula : peliculas) {
                        pelicula.setGenero(genero2);
                        int idpeli = pelicula.getId();
                        Pelicula pelicula1 = (peliculaDAO.findById(idpeli)).get();
                        peliculaDAO.update(pelicula1);
                    }
                }
                case 4 -> {
                    System.exit(0);
                }
                default -> System.out.println("Opcion incorrecta");
            }
        }
    }
}
