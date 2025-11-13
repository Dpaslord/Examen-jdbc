package org.example;

import org.example.dao.ProductoDAO;
import org.example.database.DataProvider;
import org.example.model.Producto;

import javax.sql.DataSource;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource ds = DataProvider.getDataSource(
                "jdbc:mysql://localhost:3307/ad",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASSWORD")
        );
        ProductoDAO productoDAO = new ProductoDAO(ds);
        Scanner sc = new Scanner(System.in);
        System.out.println("Gestor Producto");
        System.out.println("Elige una opcion");
        System.out.println("1. Crear Producto");
        System.out.println("2. Actualizar Producto");
        System.out.println("3. Eliminar Producto");
        System.out.println("4. Buscar Producto");
        System.out.println("5. Listar Productos");
        System.out.println("6. Salir");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1->{
                Producto producto = new Producto();

                productoDAO.save(producto);
            }
            case 2->{
                Producto producto = new Producto();

                productoDAO.update(producto);
            }
            case 3->{
                System.out.print("Escribe el id del producto: ");
                int id = sc.nextInt();

                if (productoDAO.exists(productoDAO.findById(id).get() )==true) {
                    Producto producto = productoDAO.findById(id).get();
                    productoDAO.delete(producto);
                } else {
                    System.out.println("No existe el producto");
                }
            }
            case 4->{

            }
            case 5->{

            }
            case 6->{
                System.exit(0);
            }
            default -> System.out.println("Opcion incorrecta");
        }

        sc.close();


    }
}
