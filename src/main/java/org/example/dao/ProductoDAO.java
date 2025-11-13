package org.example.dao;

import org.example.model.Producto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * DAO para la entidad {@link Producto}.
 * <p>
 * Gestiona operaciones CRUD sobre la tabla <b>videojuegos</b>.
 * </p>
 * @author David
 */
public class ProductoDAO implements DAO<Producto>{

    private final DataSource ds;
    private final Logger log = Logger.getLogger(ProductoDAO.class.getName());

    /**
     * Crea el DAO con un {@link DataSource}.
     *
     * @param ds fuente de datos
     */
    public ProductoDAO(DataSource ds) {
        this.ds = ds;
    }

    /** Inserta un producto en la base de datos. */
    @Override
    public Optional<Producto> save(Producto producto) {
        try(Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO videojuegos VALUES (0,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDesarrollador());
            stmt.setInt(3, producto.getAño());
            stmt.setString(4, producto.getGenero());
            stmt.setString(5, producto.getPlataforma());

            int res = stmt.executeUpdate();
            if (res > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    producto.setId(keys.getInt(1));
                }
                log.info("Inserccion hecha correctamente");
                return Optional.of(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Actualiza un producto existente. */
    @Override
    public Optional<Producto> update(Producto producto) {
        try (Connection connection = ds.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE videojuegos SET nombre = ?, desarrollador = ?, anio_lanzamiento = ?, genero = ?, plataforma = ? WHERE id = ?")) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDesarrollador());
            stmt.setInt(3, producto.getAño());
            stmt.setString(4, producto.getGenero());
            stmt.setString(5, producto.getPlataforma());
            stmt.setInt(6, producto.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                log.info("Producto modificado correctamente");
                return Optional.of(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Elimina un producto por su ID. */
    @Override
    public Optional<Producto> delete(Producto producto) {
        try(Connection connection = ds.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM videojuegos WHERE id = ?")) {

            stmt.setInt(1, producto.getId());
            int res = stmt.executeUpdate();
            if (res > 0) {
                log.info("Producto eliminado correctamente");
                return Optional.of(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Devuelve todos los productos. */
    @Override
    public List<Producto> findAll() {
        try (Connection connection = ds.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM videojuegos")){

            List<Producto> listado = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDesarrollador(rs.getString("desarrollador"));
                producto.setAño(rs.getInt("anio_lanzamiento"));
                producto.setGenero(rs.getString("genero"));
                producto.setPlataforma(rs.getString("plataforma"));
                log.info("Listando productos");
                listado.add(producto);
            }
            return listado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Busca un producto por ID. */
    @Override
    public Optional<Producto> findById(Integer id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM videojuegos WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDesarrollador(rs.getString("desarrollador"));
                producto.setAño(rs.getInt("anio_lanzamiento"));
                producto.setGenero(rs.getString("genero"));
                producto.setPlataforma(rs.getString("plataforma"));
                log.info(producto.toString());
                return Optional.of(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Comprueba si un producto existe en la base de datos. */
    public Boolean exists(Producto producto){
        log.info("Comprobando el producto existente");
        return findById(producto.getId()).isPresent();
    }
}
