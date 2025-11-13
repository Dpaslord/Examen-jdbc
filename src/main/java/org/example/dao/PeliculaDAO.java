package org.example.dao;

import org.example.model.Pelicula;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * DAO para la entidad {@link Pelicula}.
 * <p>
 * Gestiona operaciones CRUD sobre la tabla <b>videojuegos</b>.
 * </p>
 * @author David
 */
public class PeliculaDAO implements DAO<Pelicula>{

    private final DataSource ds;
    private final Logger log = Logger.getLogger(PeliculaDAO.class.getName());

    /**
     * Crea el DAO con un {@link DataSource}.
     *
     * @param ds fuente de datos
     */
    public PeliculaDAO(DataSource ds) {
        this.ds = ds;
    }

    /** Inserta un producto en la base de datos. */
    @Override
    public Optional<Pelicula> save(Pelicula pelicula) {
        try(Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO pelicula VALUES (0,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setInt(2, pelicula.getAño());

            stmt.setString(3, pelicula.getGenero());
            stmt.setString(4, pelicula.getDirector());

            int res = stmt.executeUpdate();
            if (res > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    pelicula.setId(keys.getInt(1));
                }
                log.info("Inserccion hecha correctamente");
                return Optional.of(pelicula);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Actualiza un producto existente. */
    @Override
    public Optional<Pelicula> update(Pelicula pelicula) {
        try (Connection connection = ds.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE pelicula SET titulo = ?, genero = ?, año = ? WHERE id = ?")) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getGenero());
            stmt.setInt(3, pelicula.getAño());
            stmt.setInt(4, pelicula.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                log.info("Modificacion pelicula");
                return Optional.of(pelicula);
            }
            log.info("Producto modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /** Elimina un producto por su ID. */
    @Override
    public Optional<Pelicula> delete(Pelicula pelicula) {
        return Optional.empty();
    }

    /** Devuelve todos los productos. */
    @Override
    public List<Pelicula> findAll() {
        try (Connection connection = ds.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pelicula")){

            List<Pelicula> listado = new ArrayList<>();
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDirector(rs.getString("director"));
                listado.add(pelicula);
            }
            log.info("Listando productos");
            return listado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Busca un producto por ID. */
    @Override
    public Optional<Pelicula> findById(Integer id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pelicula WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDirector(rs.getString("director"));
                log.info(pelicula.toString());
                return Optional.of(pelicula);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Pelicula> findByGenero(String genero) {

        try (Connection connection = ds.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pelicula where genero = ?")) {


            stmt.setString(1,genero);
            ResultSet rs = stmt.executeQuery();

            List<Pelicula> listado = new ArrayList<>();
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDirector(rs.getString("director"));
                listado.add(pelicula);

            }
            log.info("Listando productos");
            return listado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
