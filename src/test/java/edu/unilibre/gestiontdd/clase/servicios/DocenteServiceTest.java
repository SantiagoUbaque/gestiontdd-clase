package edu.unilibre.gestiontdd.clase.servicios;

import edu.unilibre.gestiontdd.clase.entidades.Docente;
import edu.unilibre.gestiontdd.clase.repositorio.DocenteRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocenteServiceTest {

    @Mock
    private DocenteRepo docenteRepo;

    @InjectMocks
    private DocenteServicio docenteServicio; // Esta clase aún no existe

    @Test
    void debeRegistrarUnDocenteExitosamente() {
        // GIVEN
        Docente docenteParaGuardar = Docente.builder()
                .codigo("DOC001")
                .nombre("Ana Martínez")
                .correo("ana@universidad.com")
                .clave("password123")
                .build();

        Docente docenteGuardado = Docente.builder()
                .id(1L)
                .codigo("DOC001")
                .nombre("Ana Martínez")
                .correo("ana@universidad.com")
                .clave("password123")
                .build();

        // Configuramos el comportamiento del Mock
        when(docenteRepo.save(any(Docente.class))).thenReturn(docenteGuardado);

        // WHEN
        Docente resultado = docenteServicio.registrarDocente(docenteParaGuardar);

        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Ana Martínez");

        // Verificamos que el repositorio fue invocado exactamente una vez
        verify(docenteRepo, times(1)).save(docenteParaGuardar);
    }
    @Test
    void debeLanzarExcepcionCuandoElCorreoYaExiste() {
        // GIVEN
        Docente docenteNuevo = Docente.builder()
                .correo("repetido@universidad.com")
                .build();

        // Simulamos que el repositorio encuentra que el correo ya existe
        when(docenteRepo.existsByCorreo("repetido@universidad.com")).thenReturn(true);

        // WHEN & THEN
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            docenteServicio.registrarDocente(docenteNuevo);
        });

        // Verificamos que NUNCA se guardó el docente por culpa del error
        verify(docenteRepo, never()).save(any(Docente.class));
    }
}
