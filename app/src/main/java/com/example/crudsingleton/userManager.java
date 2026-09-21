package com.example.crudsingleton;

import java.util.ArrayList;
import java.util.List;

public class userManager {


    private Users CurUser;

    private static userManager instance;

    private final ArrayList<Users> userList = new ArrayList<>();

    private int nextId = 1;

    private userManager(){}

    public static synchronized userManager getInstance(){
        if(instance == null){
            instance = new userManager();
        }
        return instance;
    }



    public boolean registerUser(String name, String email, String password){
        for(Users CurUser : userList){
            if(CurUser.getEmail().equalsIgnoreCase(email)){
                return false;
            }

        }
        userList.add(new Users(nextId++, name, email, password));

        return true;
    }

    public Users loginUser(String email, String password){
        for(Users CurUser : userList){
            if(CurUser.getEmail().equalsIgnoreCase(email) && CurUser.getPassword().equals(password))
            {
                return CurUser;
            }
        }
    return null;
    }

    public List<Users> getAllUsers(){
        return new ArrayList<>(userList);
    }

    public Users getCurUser() {
        return CurUser;
    }

    public void setCurUser(Users curUser) {
        CurUser = curUser;
    }

    public void logout() {
        this.CurUser = null;
    }

}
