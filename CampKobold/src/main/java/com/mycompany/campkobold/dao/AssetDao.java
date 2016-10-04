/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandon
 */
public interface AssetDao {
    
    public Asset addAsset(Asset asset, UserUserProfile user);

    public Asset getAssetByAssetId(int assetId);
    
    public List<Asset> searchAssets(Map<SearchTerm, String> criteria);
}
