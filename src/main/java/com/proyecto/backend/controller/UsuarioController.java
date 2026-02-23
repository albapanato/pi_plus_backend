package com.proyecto.backend.controller;

import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Permite CORS desde cualquier origen
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // http://localhost:8080/bdproyecto/h2-console          = Consola de H2
    // http://localhost:8080/bdproyecto                     = /static/index.html
    // http://localhost:8080/bdproyecto/static-noexiste     = gestionado por GlobalExceptionHandler
    // http://localhost:8080/bdproyecto/rest/api-noexiste   = gestionado por GlobalExceptionHandler
    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/bdproyecto/api/usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> showAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findAll());
    }

    // http://localhost:8080/bdproyecto/api/usuarios/2
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> showById(@PathVariable int id) {
        Usuario usu = usuarioService.findById(id);

        if (usu == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usu);
        }
    }

    // http://localhost:8080/bdproyecto/api/usuarios/nombre?contiene=pa
    @GetMapping("/usuarios/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> showByNombre(@RequestParam("contiene") String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombre(nombre);

        if (usuarios == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarios);
        }
    }

    // http://localhost:8080/bdproyecto/api/usuarios/count
    @GetMapping("/usuarios/count")
    public ResponseEntity<Map<String, Object>> count() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("count", usuarioService.count());

        response = ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(map);

        return response;
    }

    // ***************************************************************************
    // ACTUALIZACIONES
    // ***************************************************************************
    // ****************************************************************************
    // INSERT (POST)    
    // http://localhost:8080/bdproyecto/api/usuarios
    @PostMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> create(
            @Valid @RequestBody Usuario usuario) {

        ResponseEntity<Map<String, Object>> response;

        if (usuario == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()
                    || usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()
                    || usuario.getNombreUsuario()== null || usuario.getNombreUsuario().trim().isEmpty()
                    || usuario.getHashPassword()== null || usuario.getHashPassword().trim().isEmpty()
                    || usuario.getRol()== null) {

                Map<String, Object> map = new HashMap<>();
                String error = "";
                if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'Nombre' es obligatorio";
                }
                if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'Apeliido' es obligatorio";
                }
                if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'nombre usuario' es obligatorio";
                }
                if (usuario.getHashPassword() == null || usuario.getHashPassword().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'contraseña' es obligatorio";
                }
                if (usuario.getRol() == null) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'rol' es obligatorio";
                }
                
                map.put("error", error);

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(usuario);
                Usuario objPost = usuarioService.save(usuario);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Usuario creado con éxito");
                map.put("insertRealizado", objPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }

        return response;
    }

    // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/bdproyecto/api/usuarios
    @PutMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> update(
            @Valid @RequestBody Usuario usuario) {

        ResponseEntity<Map<String, Object>> response;

        if (usuario == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = usuario.getId();
            Usuario existingObj = usuarioService.findById(id);

            if (existingObj == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Usuario no encontrado");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (usuario.getNombre() != null) {
                    existingObj.setNombre(usuario.getNombre());
                }
                if (usuario.getApellido()!= null) {
                    existingObj.setApellido(usuario.getApellido());
                }
                if (usuario.getNombreUsuario()!= null) {
                    existingObj.setNombreUsuario(usuario.getNombreUsuario());
                }
                if (usuario.getHashPassword()!= null) {
                    existingObj.setHashPassword(usuario.getHashPassword());
                }
                if (usuario.getRol()!= null) {
                    existingObj.setRol(usuario.getRol());
                }      
                if (usuario.getLugarTrabajo()!= null) {
                    existingObj.setLugarTrabajo(usuario.getLugarTrabajo());
                }

                //existingObj.setPresupuesto(usuario.getPresupuesto());

                Usuario objPut = usuarioService.save(existingObj);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Usuario actualizado con éxito");
                map.put("updateRealizado", objPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }

    // ****************************************************************************
    // DELETE
    // http://localhost:8080/bdproyecto/api/usuarios/2
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Usuario existingObj = usuarioService.findById(id);
        if (existingObj == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Usuario no encontrado");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            usuarioService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Usuario eliminado con éxito");
            map.put("deletedRealizado", existingObj);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }

}
