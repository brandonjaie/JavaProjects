/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold;

import com.mycompany.campkobold.dao.AssetDao;
import com.mycompany.campkobold.dao.RecordDao;
import com.mycompany.campkobold.dao.SearchTerm;
import com.mycompany.campkobold.dao.UserDao;
import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Brandon
 */
@Controller
public class AssetController {

    // rename to full name assetDao for clarity
    private final AssetDao aDao;
    private final RecordDao rDao;
    private final UserDao uDao;

    @Inject
    public AssetController(AssetDao aDao, RecordDao rDao, UserDao uDao) {
        this.aDao = aDao;
        this.rDao = rDao;
        this.uDao = uDao;
    }

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Asset createAsset(@Valid @RequestBody Asset asset, Model model, Principal principal) {

           String username = principal.getName();
           UserUserProfile user = uDao.getUserUserProfileByUsername(username);
           model.addAttribute("UserUserProfile", user);

        return aDao.addAsset(asset, user);
        
    }

    @RequestMapping(value = "/assets/{id}", method = RequestMethod.GET)
    public String getAssetById(@PathVariable("id") int assetId, Model model) {

        List<AssetRecord> records = rDao.getAssetRecordsByAssetId(assetId);
        List<AssetRecord> record = rDao.getCurrentAssetRecordByAssetId(assetId);
        model.addAttribute("asset", record.size() > 0 ? record.get(0) : null);
        model.addAttribute("records", records);

        return "assetHistoryNoAjax";
    }

    @RequestMapping(value = "search/assets", method = RequestMethod.POST)
    @ResponseBody
    public List<Asset> searchAssets(@Valid @RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        String currentTerm = searchMap.get("asset");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.ASSET_ID, currentTerm);
        }
        currentTerm = searchMap.get("category");

        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.CATEGORY_ID, currentTerm);
        }
        
        return aDao.searchAssets(criteriaMap);
    }
    
    

}
