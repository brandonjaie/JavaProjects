/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.UserUserProfile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandon
 */
public interface UserDao {

    public UserUserProfile addUserUserProfile(UserUserProfile userUserProfile);

    public void updateUserUserProfile(UserUserProfile uup);

    public void deleteUserUserProfile(int userId, UserUserProfile user); 

    public List<UserUserProfile> getAllUserUserProfiles();
    
    public List<UserUserProfile> getAllMembers();

//    public UserUserProfile getUserUserProfileById(int userId);

    public UserUserProfile getUserUserProfileByIdB(int userId);

    public UserUserProfile getUserUserProfileByUsername(String username);

    public void resetPassword(int userId);

    public void updatePassword(UserUserProfile user);

    public boolean userNameAvailability(String username);

    public List<UserUserProfile> searchUserUserProfiles(Map<SearchTerm, String> criteria);
    
    public List<UserUserProfile> searchMembers(Map<SearchTerm, String> criteria);

}
