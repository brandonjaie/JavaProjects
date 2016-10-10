/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold;

import com.mycompany.campkobold.dao.AuthorityDao;
import com.mycompany.campkobold.dao.RecordDao;
import com.mycompany.campkobold.dao.UserDao;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.Authority;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Brandon
 */
@Controller
public class HomeController {

    private final RecordDao rDao;
    private final UserDao uDao;
    private final AuthorityDao xDao;

    @Inject
    public HomeController(RecordDao rDao, UserDao uDao, AuthorityDao xDao) {
        this.rDao = rDao;
        this.uDao = uDao;
        this.xDao = xDao;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String displayHomePage() {

        return "home";
    }
    
        @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
    public String displayErrorPage() {

        return "error";
    }

    @RequestMapping(value = {"/rentals"}, method = RequestMethod.GET)
    public String displayRentalsPageNoAjax(HttpServletRequest req, Model model) {

        List<AssetRecord> record = rDao.getCurrentAssetRecords();
        model.addAttribute("record", record);

        return "rentals";
    }

    @RequestMapping(value = {"/assets"}, method = RequestMethod.GET)
    public String displayAssetsPage(Model model) {
        
        List<UserUserProfile> members = uDao.getAllMembers();
        model.addAttribute("members", members);
        
        return "assets";
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String displayAdminPage(Model model) {

        return "admin";
    }

    @RequestMapping(value = {"/members"}, method = RequestMethod.GET)
    public String displayMembersPageNoAjax(Model model) {

        UserUserProfile userUserProfile = new UserUserProfile();
        model.addAttribute("UserUserProfile", userUserProfile);

        List<UserUserProfile> userUserProfiles = uDao.getAllMembers();
        model.addAttribute("UserUserProfiles", userUserProfiles);

        return "members";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String displayProfilePageNoAjax(Model model, Principal principal, HttpServletRequest req) {

        String username = principal.getName();
        UserUserProfile user = uDao.getUserUserProfileByUsername(username);
        int userId = user.getUserId();
        model.addAttribute("UserUserProfile", user);
        
        Authority authority = xDao.getHighestAuthorityByUserName(user.getUserName());
        model.addAttribute("authority", authority);
        
        if (req.isUserInRole("ROLE_ADMIN") || req.isUserInRole("ROLE_EMPLOYEE")) {

            List<AssetRecord> record = rDao.getEmployeeAssetRecordsByEmployeeId(userId);
            model.addAttribute("erecords", record);

        } else {
            List<AssetRecord> record = rDao.getMemberAssetRecordsByMemberId(userId);
            model.addAttribute("mrecords", record);
           
        }

        return "profile";
    }

    @RequestMapping(value = "/addUserUserProfileNoAjax", method = RequestMethod.POST)
    public String addUserUserProfileNoAjax(@Valid @ModelAttribute("UserUserProfile") UserUserProfile uup, BindingResult result, HttpServletRequest req) {

        uup.setUserName(uup.getUserName());
        uup.setPassword("kobolds-are-great!");
        uup.setEnabled(1);

        uup.addAuthority("ROLE_USER");

        if (req.isUserInRole("ROLE_ADMIN")) {
            if (null != uup.getIsAdmin()) {
                uup.addAuthority("ROLE_ADMIN");
                uup.addAuthority("ROLE_EMPLOYEE");
            }

            if (null != uup.getIsEmployee()) {
                uup.addAuthority("ROLE_EMPLOYEE");

            }
        }
        uup.getPassword();
        uup.setFirstName(uup.getFirstName());

        uup.setLastName(uup.getLastName());
        uup.setPhone(uup.getPhone());
        uup.setEmail(uup.getEmail());

        if (result.hasErrors()) {
            return "members";
        }

        uDao.addUserUserProfile(uup);

        return "redirect:members";
    }

    @RequestMapping(value = "/editUserUserProfileNoAjax", method = RequestMethod.POST)
    public String editUserUserProfileNoAjax(@Valid @ModelAttribute("UserUserProfile") UserUserProfile uup, BindingResult result) {

        if (result.hasErrors()) {

            return "profile";
        }

        uDao.updateUserUserProfile(uup);

        return "redirect:profile";
    }

}
