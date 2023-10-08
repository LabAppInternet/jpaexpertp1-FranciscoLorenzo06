package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.FavoriteJourney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteJourneyRepository extends JpaRepository<FavoriteJourney,String> {

    List<FavoriteJourney> findFavoriteJourneys(String username);
}
