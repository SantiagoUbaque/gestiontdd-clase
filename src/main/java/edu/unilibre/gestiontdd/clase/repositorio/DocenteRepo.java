package edu.unilibre.gestiontdd.clase.repositorio;

import edu.unilibre.gestiontdd.clase.entidades.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepo extends JpaRepository<Docente, Long> {
}
