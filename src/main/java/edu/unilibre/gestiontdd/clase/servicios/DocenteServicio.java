package edu.unilibre.gestiontdd.clase.servicios;

import edu.unilibre.gestiontdd.clase.entidades.Docente;
import edu.unilibre.gestiontdd.clase.repositorio.DocenteRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocenteServicio {
    @Autowired
    DocenteRepo docenteRepo;

    @Transactional
    public Docente registrarDocente(Docente docente){
        if (docenteRepo.existsByCorreo(docente.getCorreo())) {
            throw new IllegalArgumentException("El correo ya existe");
        }
        return docenteRepo.save(docente);
    }
}
