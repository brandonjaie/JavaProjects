/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold;

import com.mycompany.campkobold.dao.RecordDao;
import com.mycompany.campkobold.dao.SearchTerm;
import com.mycompany.campkobold.dao.UserDao;
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
public class AssetRecordController {

    private final RecordDao rDao;
    private final UserDao uDao;

    @Inject
    public AssetRecordController(RecordDao rDao, UserDao uDao) {
        this.rDao = rDao;
        this.uDao = uDao;
    }

    @RequestMapping(value = "/assetRecord", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AssetRecord createAssetRecord(@Valid @RequestBody AssetRecord assetRecord) {
        return rDao.addAssetRecord(assetRecord);
    }

    @RequestMapping(value = "/assetRecordUpdate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAssetRecord(@Valid @RequestBody AssetRecord assetRecord, Principal principal) {
        String username = principal.getName();
        UserUserProfile user = uDao.getUserUserProfileByUsername(username);
        rDao.updateAssetRecord(assetRecord, user);
    }

    @RequestMapping(value = "/assetRecordDelete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssetRecord(@PathVariable("id") int id) {
        rDao.deleteAssetRecordAndAsset(id);
    }

    @RequestMapping(value = "/checkAssetAvailability", method = RequestMethod.POST)
    @ResponseBody
    public AssetRecord checkAssetAvailability(@RequestBody AssetRecord assetRecord) {

        assetRecord.setAvailable(rDao.assetAvailability(assetRecord.getAsset().getAssetId()));

        return assetRecord;
    }

//    @RequestMapping(value = "/checkMemberExists", method = RequestMethod.POST)
//    @ResponseBody
//    public AssetRecord checkMemberExists(@RequestBody AssetRecord assetRecord) {
//
//        assetRecord.setAvailable(rDao.memberExists(assetRecord.getMember().getUserId()));
//
//        return assetRecord;
//    }
    @RequestMapping(value = "/checkDuplicateStatus", method = RequestMethod.POST)
    @ResponseBody
    public AssetRecord checkDuplicateStatus(@RequestBody AssetRecord assetRecord) {

        assetRecord.setAvailable(rDao.checkDuplicateStatus(assetRecord.getAsset().getAssetId(), assetRecord.getStatus().getStatusId()));

        return assetRecord;
    }

    @RequestMapping(value = "/assetRecord/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AssetRecord getAssetRecordByAssetId(@PathVariable("id") int id) {
        return rDao.getAssetRecordByAssetId(id);
    }

    @RequestMapping(value = "/assetRecordUsers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AssetRecord> getEmployeeAssetRecordsByUserId(@PathVariable("id") int id) {
        return rDao.getEmployeeAssetRecordsByEmployeeId(id);
    }

    @RequestMapping(value = "/assetRecords", method = RequestMethod.GET)
    @ResponseBody
    public List<AssetRecord> getAssetRecordsByCurrentStatus() {
        List<AssetRecord> rList = rDao.getCurrentAssetRecords();
        return rList;
    }

    @RequestMapping(value = "/assetRecordsCurrentDate", method = RequestMethod.GET)
    @ResponseBody
    public List<AssetRecord> getAssetRecordsByCurrentDate() {
        List<AssetRecord> rList = rDao.getAssetRecordsByCurrentDate();
        return rList;
    }

    @RequestMapping(value = "search/assetRecords", method = RequestMethod.POST)
    @ResponseBody
    public List<AssetRecord> searchAssetRecords(@RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        String currentTerm = searchMap.get("asset");
        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.ASSET_ID, currentTerm);
        }
        currentTerm = searchMap.get("category");

        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.CATEGORY_ID, currentTerm);
        }
        currentTerm = searchMap.get("status");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.STATUS_ID, currentTerm);
        }
        currentTerm = searchMap.get("description");
        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.DESCRIPTION, currentTerm.toLowerCase());
        }

        currentTerm = searchMap.get("lastName");
        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.LAST_NAME, currentTerm.toLowerCase());
        }

        return rDao.searchAssetRecords(criteriaMap);
    }

    @RequestMapping(value = "search/records", method = RequestMethod.POST)
    @ResponseBody
    public List<AssetRecord> searchRecords(@RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        String currentTerm = searchMap.get("recordDate");
        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.RECORD_DATE, currentTerm);
        }
        currentTerm = searchMap.get("employeeId");

        if (currentTerm != null && !currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.EMPLOYEE_ID, currentTerm);
        }
        currentTerm = searchMap.get("memberId");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.MEMBER_ID, currentTerm);
        }

        return rDao.searchRecords(criteriaMap);
    }

}
