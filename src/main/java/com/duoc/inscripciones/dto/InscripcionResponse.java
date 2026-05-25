package com.duoc.inscripciones.dto;

import java.util.List;

import com.duoc.inscripciones.model.Curso;
import com.duoc.inscripciones.model.Estudiante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionResponse {

    private int id;
    private Estudiante estudiante;
    private List<Curso> cursos;
    private int total;

}
