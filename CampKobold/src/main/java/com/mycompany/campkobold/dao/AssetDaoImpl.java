/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brandon
 */
public class AssetDaoImpl implements AssetDao {

    private JdbcTemplate jdbcTemplate;
    private CategoryDao cDao;
    private RecordDao rDao;
    private StatusDao sDao;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setCDao(CategoryDao cDao) {
        this.cDao = cDao;
    }

    public void setRDao(RecordDao rDao) {
        this.rDao = rDao;
    }

    public void setSDao(StatusDao sDao) {
        this.sDao = sDao;
    }

    private static final String SQL_INSERT_ASSET
            = "insert into assets (category_id, brand, description) value (?, ?, ?)";

    private static final String SQL_SELECT_ASSET_BY_ASSET_ID
            = "select * from assets where asset_id = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Asset addAsset(Asset asset, UserUserProfile user) {

        jdbcTemplate.update(SQL_INSERT_ASSET,
                asset.getCategory().getCategoryId(),
                asset.getBrand(),
                asset.getDescription());
        asset.setAssetId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        Date date = new Date();

        AssetRecord rec = new AssetRecord();
        rec.setAsset(asset);
        rec.setEmployee(user);
        rec.setStatus(sDao.getStatusByName("AVAILABLE"));
        rec.setAssetNote("New Asset " + asset.getAssetId());
        rec.setRecordDate(date);
        
        rDao.addAssetRecord(rec);

        return asset;
    }

    @Override
    public Asset getAssetByAssetId(int assetId) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ASSET_BY_ASSET_ID, new AssetMapper(), assetId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Asset> searchAssets(Map<SearchTerm, String> criteria) {

            StringBuilder sQuery = new StringBuilder("select a.*"
                    + " from assets a"
                    + " join asset_records arec"
                    + " on a.asset_id = arec.asset_id"
                    + " and arec.record_id = ( SELECT MAX(record_id) from asset_records b where arec.asset_id = b.asset_id)"
                    + " where arec.status_id = 1"
                    + " and ");

            int numParams = criteria.size();

            int paramPosition = 0;

            String[] paramVals = new String[numParams];

            Set<SearchTerm> keySet = criteria.keySet();

            Iterator<SearchTerm> iter = keySet.iterator();

            while (iter.hasNext()) {

                SearchTerm currentKey = iter.next();

                if (paramPosition > 0) {

                    sQuery.append(" and ");

                }

                sQuery.append(currentKey.getAlias()).append(currentKey);

                sQuery.append(" = ? ");

                paramVals[paramPosition] = criteria.get(currentKey);

                paramPosition++;

            }

            return jdbcTemplate.query(sQuery.toString(), new AssetDaoImpl.AssetMapper(), paramVals);

//        }
    }

    private final class AssetMapper implements RowMapper<Asset> {

        @Override
        public Asset mapRow(ResultSet rs, int i) throws SQLException {

            Asset asset = new Asset();
            asset.setAssetId(rs.getInt("asset_id"));
            asset.setCategory(cDao.getCategoryById(rs.getInt("category_id")));
            asset.setBrand(rs.getString("brand"));
            asset.setDescription(rs.getString("description"));

            return asset;
        }
    }

}
