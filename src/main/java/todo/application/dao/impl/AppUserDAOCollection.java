package todo.application.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import todo.application.dao.AppUserDAO;
import todo.application.model.AppUser;

import java.util.ArrayList;
import java.util.List;

import static todo.application.maintenance.StaticResources.APP_USER_FILE;
import static todo.application.maintenance.StaticResources.APP_USER_NAME_FILE;

public class AppUserDAOCollection implements AppUserDAO {

    private List<AppUser> appUserList = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();


    public void setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
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

        saveAsJsonToFile();

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
        return appUserList;
    }

    @Override
    public void remove(String userName) {
        AppUser appUser = findByUsername(userName);

        if(appUser != null){
            userNameList.remove(appUser.getUsername());
            appUserList.remove(appUser);
        }
    }


    //Loads Collection Data From File
    public void loadCollectionData(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            appUserList = objectMapper.readValue(APP_USER_FILE, new TypeReference<List<AppUser>>() {});
            userNameList = objectMapper.readValue(APP_USER_NAME_FILE, new TypeReference<List<String>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Saves Collection Data As JSON To File
    public void saveAsJsonToFile(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(APP_USER_FILE, appUserList);
            objectWriter.writeValue(APP_USER_NAME_FILE, userNameList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

