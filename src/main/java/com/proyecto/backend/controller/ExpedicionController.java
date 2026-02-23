package com.proyecto.backend.controller;

import com.proyecto.backend.model.EstadoExpedicion;
import com.proyecto.backend.model.Expedicion;
import com.proyecto.backend.service.ExpedicionService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
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
public class ExpedicionController {

    @Autowired
    private ExpedicionService expedicionService;

    // http://localhost:8080/bdproyecto/h2-console          = Consola de H2
    // http://localhost:8080/bdproyecto                     = /static/index.html
    // http://localhost:8080/bdproyecto/static-noexiste     = gestionado por GlobalExceptionHandler
    // http://localhost:8080/bdproyecto/api/api-noexiste   = gestionado por GlobalExceptionHandler
    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/bdproyecto/api/expediciones
    @GetMapping("/expediciones")
    public ResponseEntity<List<Expedicion>> showAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expedicionService.findAll());
    }

    // http://localhost:8080/bdproyecto/api/expediciones/2
    @GetMapping("/expediciones/{id}")
    public ResponseEntity<Expedicion> showById(@PathVariable int id) {
        Expedicion usu = expedicionService.findById(id);

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

    // http://localhost:8080/bdproyecto/api/expediciones/dep/Ventas
    @GetMapping("/expediciones/nombre/usuario/{name}")
    public ResponseEntity<List<Expedicion>> showByNombreUsuario(@PathVariable String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expedicionService.findLikeNombre(name));
    }

    // http://localhost:8080/bdproyecto/api/expediciones/nombre?contiene=pe
    @GetMapping("/expediciones/direccion/{direccion}")
    public ResponseEntity<List<Expedicion>> showBydireccion(@RequestParam("contiene") String direccion) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expedicionService.findLikeDireccion(direccion));
    }

    // http://localhost:8080/bdproyecto/api/expediciones/count
    @GetMapping("/expediciones/count")
    public ResponseEntity<Map<String, Object>> count() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("count", expedicionService.count());

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
    // http://localhost:8080/bdproyecto/api/empleados
    @PostMapping("/expediciones")
    public ResponseEntity<Map<String, Object>> create(
            @Valid @RequestBody Expedicion expedicion) {

        ResponseEntity<Map<String, Object>> response;

        if (expedicion == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (expedicion.getDireccionDestino() == null || expedicion.getDireccionDestino().trim().isEmpty()
                    || expedicion.getPaquetes() < 0
                    || expedicion.getPeso() < 0
                    || expedicion.getNotas() == null || expedicion.getDireccionDestino().trim().isEmpty()
                    || expedicion.getUsuario() == null) {

                Map<String, Object> map = new HashMap<>();
                String error = "";
                if (expedicion.getDireccionDestino() == null || expedicion.getDireccionDestino().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'direccion destino' es obligatorio";
                }
                if (expedicion.getNotas() == null || expedicion.getNotas().trim().isEmpty()) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'email' es obligatorio";
                }
                if (expedicion.getPaquetes() < 0) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'paquetes' debe ser positivo";
                }
                if (expedicion.getPeso() < 0) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'peso' debe ser positivo";
                }
                if (expedicion.getUsuario() == null) {
                    if (!error.equals("")) {
                        error += " - ";
                    }
                    error += "El campo 'usuario' es obligatorio";
                }
                map.put("error", error);

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(expedicion);
                //expedicion.setFechaCreacion(LocalDateTime.now());
                //expedicion.setFechaModificacion(LocalDateTime.now());
                //expedicion.setEstado(EstadoExpedicion.abierta);
                Expedicion objPost = expedicionService.save(expedicion);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Expedicion creado con éxito");
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
    // http://localhost:8080/bdproyecto/api/expediciones
    @PutMapping("/expediciones")
    public ResponseEntity<Map<String, Object>> update(
            @Valid @RequestBody Expedicion exp) {

        ResponseEntity<Map<String, Object>> response;

        if (exp == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = exp.getId();
            Expedicion existingObj = expedicionService.findById(id);

            if (existingObj == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Expedicion no encontrada");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (exp.getFechaRecepcion() != null) {
                    existingObj.setFechaRecepcion(exp.getFechaRecepcion());
                }

                if (exp.getDireccionDestino() != null) {
                    existingObj.setDireccionDestino(exp.getDireccionDestino());
                }

                if (exp.getPaquetes() >= 0) {
                    existingObj.setPaquetes(exp.getPaquetes());
                }

                if (exp.getPeso() >= 0) {
                    existingObj.setPeso(exp.getPeso());
                }

                if (exp.getNotas() != null) {
                    existingObj.setNotas(exp.getNotas());
                }
                //existingObj.setFechaModificacion(LocalDateTime.now());
                Expedicion objPut = expedicionService.save(existingObj);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Empleado actualizado con éxito");
                map.put("updateRealizado", objPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }

    // ****************************************************************************
    // UPDATE (PUT) - ESPECIALES
    // 
    @PutMapping("/expediciones/{id}/en-transito")
    public ResponseEntity<Expedicion> pasarAEnTransito(@PathVariable int id) {
        return ResponseEntity.ok(expedicionService.marcarEnTransito(id));
    }

    @PutMapping("/expediciones/{id}/recibida")
    public ResponseEntity<Expedicion> pasarARecibida(@PathVariable int id) {
        return ResponseEntity.ok(expedicionService.marcarRecibida(id));
    }

    // ****************************************************************************
    // DELETE
    // http://localhost:8080/bdproyecto/api/expediciones/16
    @DeleteMapping("/expediciones/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Expedicion existingObj = expedicionService.findById(id);
        if (existingObj == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Expedicion no encontrada");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            expedicionService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Expedicion eliminado con éxito");
            map.put("deletedRealizado", existingObj);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }

    // ***************************************************************************
    // OPERACIONES ESPECIALES DE ACTUALIZACIÓN
    // ***************************************************************************
    // http://localhost:8080/bdproyecto/api/expediciones/1/reasignar/usuario/3
    @PutMapping("/expediciones/{expId}/reasignar/usuario/{usuId}")
    public ResponseEntity<Map<String, Object>> asignarRole(
            @PathVariable int expId,
            @PathVariable int usuId) {

        ResponseEntity<Map<String, Object>> response;

        Expedicion emp = expedicionService.reasignarUsuario(usuId, expId);

        if (emp != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Usuario asignado con éxito");
            map.put("updateRealizado", emp);

            response = ResponseEntity.status(HttpStatus.OK).body(map);

        } else {

            Map<String, Object> map = new HashMap<>();
            map.put("error", "Expedicion o Usuario no existe");
            map.put("empId", expId);
            map.put("depId", usuId);

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        return response;
    }

    /*// http://localhost:8080/bdproyecto/api/empleados/1/desasignar/departamento
    @PutMapping("/empleados/{empId}/desasignar/departamento")
    public ResponseEntity<Map<String, Object>> desasignarRole(@PathVariable int empId) {
        
        ResponseEntity<Map<String, Object>> response;
        
       
        Expedicion emp = expedicionService.desasignarUsuario(empId);
            
        if (emp!=null) {
            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Departamento desasignado con éxito");
            map.put("updateRealizado", emp);
            
            response = ResponseEntity.status(HttpStatus.OK).body(map);
            
        } else {
            
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Empleado no existe");
            map.put("empId", empId);
            
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        
        return response;
    } */
}
