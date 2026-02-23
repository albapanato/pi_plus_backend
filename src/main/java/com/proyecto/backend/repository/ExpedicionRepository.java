package com.proyecto.backend.repository;

import com.proyecto.backend.model.Expedicion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpedicionRepository extends JpaRepository<Expedicion, Integer> {

    // **********************************************************
    // Obtener datos (find y count)
    // **********************************************************
    // ****************************
    // Métodos HEREDADOS
    // ****************************
    /*
        findAll()
        findById(id)

        count()

        equals(User)
        exist(User)
        existById(id)
     */
    // Consulta con SQL mapeado
    @Query(value = "SELECT * FROM expediciones", nativeQuery = true)
    List<Expedicion> findSqlAll();

    // Consulta con SQL mapeado
    @Query(value = "SELECT * FROM expediciones WHERE id = :id", nativeQuery = true)
    Expedicion findSqlById(@Param("id") int empleadoId);

    // Consulta con SQL mapeado
    @Query(value = "SELECT expediciones.* FROM expediciones, usuarios WHERE expediciones.usuario_id=usuarios.id AND LOWER(usuarios.nombre_usuario) = LOWER(:nombreUsuario)", nativeQuery = true)
    List<Expedicion> findSqlByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    // Consulta con SQL mapeado
    @Query(value = " SELECT * FROM expediciones WHERE LOWER(direccion_destino) LIKE LOWER(CONCAT('%', :contiene, '%'))", nativeQuery = true)
    List<Expedicion> findSqlLikeDireccion(@Param("contiene") String contiene);

    // Consulta con SQL mapeado
    @Query(value = "SELECT COUNT(*) as empleados FROM empleados", nativeQuery = true)
    Long countSql();

    // **********************************************************
    // Actualizaciones
    // **********************************************************
    // ****************************
    // Métodos HEREDADOS
    // ****************************
    /*
        delete(User)
        deleteById(id)
        deleteAll()

        save(User)
     */
}
