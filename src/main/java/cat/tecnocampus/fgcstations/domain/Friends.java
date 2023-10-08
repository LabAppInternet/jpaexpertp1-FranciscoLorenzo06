package cat.tecnocampus.fgcstations.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity(name = "Friends")
@Table(name = "friends")
public class Friends {
    @Id
    private String username;

    private List<String> friends = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
