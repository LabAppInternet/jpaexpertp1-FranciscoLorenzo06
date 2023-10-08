package cat.tecnocampus.fgcstations.application;


import cat.tecnocampus.fgcstations.application.DTOs.DayTimeStartDTO;
import cat.tecnocampus.fgcstations.application.DTOs.FavoriteJourneyDTO;
import cat.tecnocampus.fgcstations.application.DTOs.FriendsDTO;
import cat.tecnocampus.fgcstations.application.exception.UserDoesNotExistsException;
import cat.tecnocampus.fgcstations.application.persistence.*;
import cat.tecnocampus.fgcstations.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FgcController {
    private final StationRepository stationRepo;
    private final UserRepository userRepo;
    private final FavoriteJourneyRepository favoriteJourneyRepo;
    private final JourneyRepository journeyRepo;
    private final FriendRepository friendRepo;

    public FgcController(StationRepository stationRepo, UserRepository userRepo,
                         FavoriteJourneyRepository favoriteJourneyRepo, JourneyRepository journeyRepo,
                         FriendRepository friendRepo) {
        this.stationRepo = stationRepo;
        this.userRepo = userRepo;
        this.favoriteJourneyRepo =favoriteJourneyRepo;
        this.journeyRepo = journeyRepo;
        this.friendRepo = friendRepo;
    }

    public List<Station> getStations() {
        return stationRepo.findAll();
    } 

    public Station getStation(String nom) {
        return stationRepo.findByNom(nom);
    } 

    public User getUser(String username) {
        //get the user
        User user = userRepo.findByUsername(username);

        //get the user's favorite journey
        user.setFavoriteJourneyList(favoriteJourneyRepo.findFavoriteJourneys(username));

        return user;
    }

    public List<User> getUsers() { 
        //get the users
        List<User> users = userRepo.getUsers();

        //get the users' favorite journeys
        users.forEach(u -> u.setFavoriteJourneyList(favoriteJourneyRepo.findFavoriteJourneys(u.getUsername())));

        return users;
    }

    public boolean existsUser(String username) {
        return userRepo.existsUserByUsername(username);
    } 

    public void addUserFavoriteJourney(String username, FavoriteJourneyDTO favoriteJourneyDTO) {
        FavoriteJourney favoriteJourney = convertFavoriteJourneyDTO(favoriteJourneyDTO);
        saveFavoriteJourney(favoriteJourney, username);
    }

    private void saveFavoriteJourney(FavoriteJourney favoriteJourney, String username) { 
        String journeyId = saveJourneyIfDoesNotExist(favoriteJourney.getJourney());
        favoriteJourney.getJourney().setId(journeyId);
        favoriteJourney.addUser(userRepo.findByUsername(username));
        favoriteJourneyRepo.save(favoriteJourney);
    }

    private String saveJourneyIfDoesNotExist(Journey journey) { 
        String journeyId = String.valueOf(journeyRepo.findById(journey.getId()));
        if (journeyId.equals("-1")) {
            journeyId = UUID.randomUUID().toString();
            journey.setId(journeyId);
            journeyRepo.save(journey);
        }
        return journeyId;
    }

    public List<FavoriteJourney> getFavoriteJourneys(String username) { 
        if (!existsUser(username)) {
            UserDoesNotExistsException e = new UserDoesNotExistsException("user " + username + " doesn't exist");
            e.setUsername(username);
            throw e;
        }
        return favoriteJourneyRepo.findFavoriteJourneys(username);
    }

    private FavoriteJourney convertFavoriteJourneyDTO(FavoriteJourneyDTO favoriteJourneyDTO) { 
        FavoriteJourney favoriteJourney = new FavoriteJourney();
        favoriteJourney.setId(UUID.randomUUID().toString());
        Journey journey = new Journey(stationRepo.findByNom(favoriteJourneyDTO.getOrigin()),
                stationRepo.findByNom(favoriteJourneyDTO.getDestination()),
                "empty id");
        favoriteJourney.setJourney(journey);

        List<DayTimeStart> dayTimeStarts = favoriteJourneyDTO.getDayTimes().stream().map(this::convertDayTimeStartDTO).collect(Collectors.toList());
        favoriteJourney.setDateTimeStarts(dayTimeStarts);

        return favoriteJourney;
    }

    private DayTimeStart convertDayTimeStartDTO(DayTimeStartDTO dayTimeStartDTO) { 
        return new DayTimeStart(dayTimeStartDTO.getDayOfWeek(), dayTimeStartDTO.getTime(), UUID.randomUUID().toString());
    }

    public Friends getUserFriends(String username) {
        return friendRepo.getFriends(username);
    } 

    public List<Friends> getAllUserFriends() {
        return friendRepo.getFriends();
    } 

    public void saveFriends(FriendsDTO friendsDTO) {
        if (!existsUser(friendsDTO.getUsername())) {
            UserDoesNotExistsException e = new UserDoesNotExistsException("User " + friendsDTO.getUsername() + " doesn't exist");
            e.setUsername(friendsDTO.getUsername());
            throw e;
        }

        Friends friends = convertFriendsDTO(friendsDTO);
        friendRepo.save(friends);
    }

    private Friends convertFriendsDTO(FriendsDTO friendsDTO) {
        Friends friends = new Friends();
        List<String> friendsList = new ArrayList<>();
        friends.setUsername(friendsDTO.getUsername());
        friends.setFriends(friendsDTO.getFriends());
        return friends;
    }
}