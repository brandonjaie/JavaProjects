/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold;



import com.mycompany.campkobold.co.CheckUserName;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import com.mycompany.campkobold.dao.UserDao;
import com.mycompany.campkobold.dao.SearchTerm;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author apprentice
 */
@Controller
public class UserController {

    private final UserDao uDao;

    @Inject
    public UserController(UserDao uDao) {
        this.uDao = uDao;
    }
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteUserUserUserProfile(@RequestBody @PathVariable("id") int userId, Principal principal) {
        
        String username = principal.getName();
        UserUserProfile user = uDao.getUserUserProfileByUsername(username);
        uDao.deleteUserUserProfile(userId, user);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserUserProfile> getAllUserUserProfiles() {
        List<UserUserProfile> users = uDao.getAllUserUserProfiles();   
        return users;
    }
    
    @RequestMapping(value = "/checkUserNameAvailability", method = RequestMethod.POST)
    @ResponseBody
    public CheckUserName checkUserNameAvailability(@RequestBody CheckUserName checkUserName){
        
        checkUserName.setAvailable(uDao.userNameAvailability(checkUserName.getUserName()));
        
        return checkUserName;
    } 
    
    @RequestMapping(value = "/resetPassword/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@PathVariable("id") int id) {
        uDao.resetPassword(id);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody UserUserProfile user) {
        uDao.updatePassword(user);
    }

    @RequestMapping(value = "search/users", method = RequestMethod.POST)
    @ResponseBody
    public List<UserUserProfile> searchUserUserProfiles(@RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        String currentTerm = searchMap.get("userId");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.USER_ID, currentTerm);
        }

        currentTerm = searchMap.get("lastName");

        if (!currentTerm.isEmpty()) {

            criteriaMap.put(SearchTerm.LAST_NAME, currentTerm.toLowerCase());

        }

        return uDao.searchUserUserProfiles(criteriaMap);
    }
    

}

   

