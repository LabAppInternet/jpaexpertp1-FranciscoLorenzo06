package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    List<User> getUsers();

    boolean existsUserByUsername(String username);
}
