package edu.unilibre.gestiontdd.clase.repositorios;

import edu.unilibre.gestiontdd.clase.entidades.Docente;
import edu.unilibre.gestiontdd.clase.entidades.Materia;
import edu.unilibre.gestiontdd.clase.repositorio.DocenteRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DocenteRepositorioTest {
    @Autowired
    private DocenteRepo docenteRepo;

    @Test
    void debeGuardarDocenteConMateriasAsignadas() {
        // GIVEN (Preparación)
        Materia calculo = Materia.builder()
                .codigo("MAT101")
                .nombre("Cálculo I")
                .creditos(4)
                .build();

        Materia fisica = Materia.builder()
                .codigo("FIS101")
                .nombre("Física Mecánica")
                .creditos(4)
                .build();

        Docente docente = Docente.builder()
                .codigo("DOC001")
                .nombre("Carlos Gómez")
                .correo("carlos@universidad.com")
                .clave("secreto123")
                .materias(List.of(calculo, fisica))
                .build();

        // Asignar el docente a las materias para la relación bidireccional
        calculo.setDocente(docente);
        fisica.setDocente(docente);

        // WHEN (Acción)
        Docente docenteGuardado = docenteRepo.save(docente);

        // THEN (Verificación)
        assertThat(docenteGuardado.getId()).isNotNull();
        assertThat(docenteGuardado.getMaterias()).hasSize(2);

        Optional<Docente> encontrado = docenteRepo.findById(docenteGuardado.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getMaterias().get(0).getNombre()).isEqualTo("Cálculo I");
    }
}
