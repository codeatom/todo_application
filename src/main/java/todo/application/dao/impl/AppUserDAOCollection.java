package todo.application.dao.impl;

import todo.application.dao.AppUserDAO;
import todo.application.model.AppUser;

import java.util.ArrayList;
import java.util.List;

public class AppUserDAOCollection implements AppUserDAO {
    List<AppUser> appUserList;
    List<String> userNameList;

    public AppUserDAOCollection() {
        this.appUserList = new ArrayList<>();
        this.userNameList = new ArrayList<>();
    }

    @Override
    public AppUser persist(AppUser appUser) {
        if(appUser == null){
            throw new IllegalArgumentException("AppUser is null");
        }

        if(userNameList.contains(appUser.getUsername())){
            throw new IllegalArgumentException(appUser.getUsername() + " is already taken");
        }

        appUserList.add(appUser);
        userNameList.add(appUser.getUsername());
        return appUser;
    }

    @Override
    public AppUser findByUsername(String userName) {
        if (userName == null) throw new IllegalArgumentException("Username is null");

        return appUserList.stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(userName))
                .findFirst().orElse(null);
    }

    @Override
    public List<AppUser> findAll() {
        return new ArrayList<>(appUserList);
    }

    @Override
    public void remove(String userName) {
        AppUser appUser = findByUsername(userName);

        if(appUser != null){
            userNameList.remove(appUser.getUsername());
            appUserList.remove(appUser);
        }
    }
}
