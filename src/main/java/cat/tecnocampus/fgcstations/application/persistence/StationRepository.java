package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station,String> {
    List<Station> findAll();

    Station findByNom(String nom);
}
