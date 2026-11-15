package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        this.userList = loadUsers();
    }

    public UserBookingService() throws IOException {
        this.userList = loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        if (user == null || userList == null || user.getName() == null) {
            return false;
        }
        Optional<User> foundUser = userList.stream().filter(savedUser ->
                savedUser.getName() != null
                        && savedUser.getName().equalsIgnoreCase(user.getName())
                        && UserServiceUtil.checkPassword(user.getPassword(), savedUser.getHashedPassword())
        ).findFirst();
        if (foundUser.isPresent()) {
            user = foundUser.get();
            return true;
        }
        return false;
    }

    public Boolean signUp(User user1){
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

//Serialization-Java-->Json
    public void saveUserListToFile()throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile,userList);
    }


    public void fetchBooking(){
        if (user == null) {
            System.out.println("Please log in to fetch bookings.");
            return;
        }
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
        //COMPLETEthisFUNCTION
        return Boolean.FALSE;
    }
}
