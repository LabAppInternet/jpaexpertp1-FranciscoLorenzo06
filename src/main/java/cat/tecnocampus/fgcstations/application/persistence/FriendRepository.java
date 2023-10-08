package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friends,String> {

    List<Friends> getFriends();

    Friends getFriends(String username);

}
