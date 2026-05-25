package com.duoc.inscripciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.inscripciones.repository.InscripcionRepository;
import com.duoc.inscripciones.dto.InscripcionRequest;
import com.duoc.inscripciones.dto.InscripcionResponse;
import com.duoc.inscripciones.mapper.InscripcionRequestMapper;
import com.duoc.inscripciones.mapper.InscripcionResponseMapper;
import com.duoc.inscripciones.model.Inscripcion;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepo;

    @Autowired
    private InscripcionRequestMapper mapperRequest;

    @Autowired
    private InscripcionResponseMapper mapperResponse;

    public List<Inscripcion> listarInscripciones(){
        return inscripcionRepo.findAll();
    }

    public Optional<Inscripcion> buscarInscripcion(int id){
        return inscripcionRepo.findById(id);
    }

    public InscripcionResponse registrarInscripcion(InscripcionRequest request){
        Inscripcion inscripcion = mapperRequest.toInscripcion(request);
        return mapperResponse.toInscripcionResponse(inscripcionRepo.save(inscripcion));
    }

    public boolean eliminarInscripcion(int id){
        if (inscripcionRepo.existsById(id)){
            inscripcionRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
