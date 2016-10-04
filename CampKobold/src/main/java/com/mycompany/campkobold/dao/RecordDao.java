/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandon
 */
public interface RecordDao {

    public AssetRecord addAssetRecord(AssetRecord record);

    public AssetRecord updateAssetRecord(AssetRecord record, UserUserProfile user);

    public void deleteAssetRecordAndAsset(int assetId);
    
    public void deleteAssetRecordsByMemberId(int userId);
    
    public void deleteAssetRecordsByEmployeeId(int userId);
    
    public boolean assetAvailability(int assetId);
    
    public boolean memberExists(int userId);
    
    public boolean checkDuplicateStatus(int assetId, int statusId);
    
    public List<AssetRecord> getAllAssetRecords();

    public AssetRecord getAssetRecordByAssetId(int assetId);

    public List<AssetRecord> getAssetRecordsByAssetId(int assetId);

    public List<AssetRecord> getEmployeeAssetRecordsByEmployeeId(int userId);

    public List<AssetRecord> getMemberAssetRecordsByMemberId(int userId);
    
    public List<AssetRecord> getMemberAssetRecordByMemberId(int userId);

    public List<AssetRecord> getCurrentAssetRecords();

    public List<AssetRecord> getCurrentAssetRecordByAssetId(int assetId);
    
    public List<AssetRecord> getCurrentAssetRecordByStatusId(int statusId);

    public List<AssetRecord> searchAssetRecords(Map<SearchTerm, String> criteria);

}
