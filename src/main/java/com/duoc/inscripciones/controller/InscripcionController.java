package com.duoc.inscripciones.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.duoc.inscripciones.dto.InscripcionRequest;
import com.duoc.inscripciones.dto.InscripcionResponse;
import com.duoc.inscripciones.mapper.InscripcionResponseMapper;
import com.duoc.inscripciones.service.InscripcionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionResponseMapper mapperResponse;

    @GetMapping
    public ResponseEntity<List<InscripcionResponse>> listarInscripciones(){
        List<InscripcionResponse> inscripciones = inscripcionService.listarInscripciones()
            .stream()
            .map(mapperResponse::toInscripcionResponse)
            .toList();
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponse> buscarInscripcion(@PathVariable @PositiveOrZero int id){
        return inscripcionService.buscarInscripcion(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> registrarInscripcion(@Valid @RequestBody InscripcionRequest request){
        InscripcionResponse creado = inscripcionService.registrarInscripcion(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(creado.getId())
            .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable @PositiveOrZero int id){
        return inscripcionService.eliminarInscripcion(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }


}
