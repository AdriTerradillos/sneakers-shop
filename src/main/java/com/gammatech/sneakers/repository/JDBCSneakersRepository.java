package com.gammatech.sneakers.repository;

import com.gammatech.sneakers.entity.Sneaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbcSneakersRepository")
public class JDBCSneakersRepository implements SneakersRespository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCSneakersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sneaker save(Sneaker sneaker) {
        // Generamos un ID único en la aplicación (aunque no es lo ideal en muchos casos)
        Long generatedId = System.currentTimeMillis(); // O puedes usar UUID.randomUUID().toString()

        String sql = "INSERT INTO sneaker (id, name, brand, size, price) VALUES (?, ?, ?, ?, ?)";

        // Ejecutamos la inserción en la base de datos
        jdbcTemplate.update(sql, generatedId, sneaker.getName(), sneaker.getBrand(), sneaker.getSize(), sneaker.getPrice());

        // Asignamos el ID generado al objeto
        sneaker.setId(generatedId);

        return sneaker;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM sneaker WHERE id = ?";

        // Ejecutamos la eliminación en la base de datos
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Sneaker> findById(Long id) {
        String sql = "SELECT * FROM sneaker WHERE id = ?";

        // Usamos un RowMapper para mapear el resultado de la consulta a un objeto Sneaker
        RowMapper<Sneaker> rowMapper = (rs, rowNum) -> new Sneaker(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("color"),
                rs.getString("size"),
                rs.getDouble("price")
        );

        // Ejecutamos la consulta y obtenemos el resultado
        Sneaker sneaker = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.ofNullable(sneaker);
    }

    @Override
    public Iterable<Sneaker> findAll() {
        String sql = "SELECT * FROM sneaker";

        // Usamos el mismo RowMapper para mapear cada fila a un objeto Sneaker
        RowMapper<Sneaker> rowMapper = (rs, rowNum) -> new Sneaker(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("color"),
                rs.getString("size"),
                rs.getDouble("price")
        );

        // Ejecutamos la consulta y devolvemos los resultados
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Page<Sneaker> findAll(Pageable pageable) {
        String sql = "SELECT * FROM sneaker LIMIT ? OFFSET ?";

        // Calculamos el límite y el desplazamiento
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        // Usamos un RowMapper para mapear el resultado de la consulta a un objeto Sneaker
        RowMapper<Sneaker> rowMapper = (rs, rowNum) -> new Sneaker(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("color"),
                rs.getString("size"),
                rs.getDouble("price")
        );

        // Ejecutamos la consulta y obtenemos los resultados
        Iterable<Sneaker> sneakers = jdbcTemplate.query(sql, rowMapper, limit, offset);

        // Aquí deberías implementar la lógica para convertir Iterable<Sneaker> a Page<Sneaker>
        // Esto es solo un ejemplo y necesitarás ajustar según tu implementación de Page
        return new PageImpl<Sneaker>(List.copyOf((java.util.Collection<? extends Sneaker>) sneakers), pageable, count());
    }

    private long count() {
        String sql = "SELECT COUNT(*) FROM sneaker";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
