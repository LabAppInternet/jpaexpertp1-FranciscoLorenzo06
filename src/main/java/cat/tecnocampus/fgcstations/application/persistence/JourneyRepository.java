package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyRepository extends JpaRepository<Journey, String > {

    String getJourneyId(Journey journey);

    Journey findJourney(String journeyId);
}
