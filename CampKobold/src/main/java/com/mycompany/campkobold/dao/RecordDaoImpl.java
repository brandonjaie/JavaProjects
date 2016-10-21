/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.Authority;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brandon
 */
public class RecordDaoImpl implements RecordDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao uDao;
    private AssetDao aDao;
    private StatusDao sDao;
    private AuthorityDao xDao;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setUDao(UserDao uDao) {
        this.uDao = uDao;
    }

    public void setADao(AssetDao aDao) {
        this.aDao = aDao;
    }

    public void setSDao(StatusDao sDao) {
        this.sDao = sDao;
    }

    public void setXDao(AuthorityDao xDao) {
        this.xDao = xDao;
    }

    private static final String SQL_INSERT_ASSET_RECORD
            = "insert into asset_records (asset_id, employee_id, member_id, status_id, record_date, note) value (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ASSET_RECORD
            = "DELETE FROM asset_records WHERE asset_id = ?";

    private static final String SQL_DELETE_ASSET_RECORD_BY_MEMBER_ID
            = "DELETE FROM asset_records WHERE member_id = ?";

    private static final String SQL_DELETE_ASSET_RECORD_BY_EMPLOYEE_ID
            = "delete from asset_records where employee_id = ?";

    private static final String SQL_DELETE_ASSET
            = "DELETE FROM assets WHERE asset_id = ?";

    private static final String SQL_SELECT_ALL_ASSET_RECORDS
            = "select * from asset_records";

    private static final String SQL_SELECT_ASSET_RECORD_BY_ASSET_ID
            = "select arec.* "
            + "from asset_records arec "
            + "where arec.record_id = ( SELECT MAX(record_id) from asset_records b where arec.asset_id = b.asset_id) "
            + "and arec.asset_id = ?";

    private static final String SQL_SELECT_ASSET_RECORDS_BY_ASSET_ID
            = "select * from asset_records where asset_id = ?";

    private static final String SQL_SELECT_EMPLOYEE_ASSET_RECORDS_BY_EMPLOYEE_ID
            = "select arec.* "
            + "from asset_records arec "
            + "join users u "
            + "on u.user_id = arec.employee_id "
            + "where arec.employee_id = ? ";

    private static final String SQL_SELECT_MEMBER_ASSET_RECORDS_BY_MEMBER_ID
            = "select arec.* "
            + "from asset_records arec "
            + "join users u "
            + "on u.user_id = arec.member_id "
            + "where arec.member_id = ? ";

    private static final String SQL_SELECT_MEMBER_ASSET_RECORD_BY_MEMBER_ID
            = "select arec.* "
            + "from asset_records arec "
            + "join users u "
            + "on u.user_id = arec.member_id "
            + "where record_id = "
            + "(select max(record_id) "
            + "from asset_records b "
            + "where arec.asset_id = b.asset_id) "
            + "and arec.member_id = ?";

    private static final String SQL_SELECT_ASSET_RECORDS_BY_CURRENT_STATUS
            = "select * "
            + "from asset_records a "
            + "where record_id = "
            + "(select max(record_id) "
            + "from asset_records b "
            + "where a.asset_id = b.asset_id) "
            + "order by a.status_id ASC";

    private static final String SQL_SELECT_ASSET_RECORD_CURRENT_STATUS_BY_ASSET_ID
            = "select arec.* "
            + "from asset_records arec "
            + "where arec.record_id = ( SELECT MAX(record_id) from asset_records b where arec.asset_id = b.asset_id) "
            + "and arec.asset_id = ?";

//    private static final String SQL_SELECT_ASSET_RECORD_CURRENT_STATUS_BY_STATUS_ID
//            = "select arec.* "
//            + "from asset_records arec "
//            + "where arec.record_id = ( SELECT MAX(record_id) from asset_records b where arec.asset_id = b.asset_id) "
//            + "and (arec.asset_id) "
//            + "and arec.status_id = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AssetRecord addAssetRecord(AssetRecord record) {
        jdbcTemplate.update(SQL_INSERT_ASSET_RECORD,
                record.getAsset().getAssetId(),
                record.getEmployee().getUserId(),
                record.getMember() == null ? null : record.getMember().getUserId(),
                record.getStatus().getStatusId(),
                record.getRecordDate(),
                record.getAssetNote());
        record.setRecordId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        return record;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AssetRecord updateAssetRecord(AssetRecord record, UserUserProfile user) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date today;
        try {
            today = dateFormat.parse(dateFormat.format(new Date()));
            record.setRecordDate(today);
        } catch (ParseException ex) {
            Logger.getLogger(RecordDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        record.setEmployee(user);
//        record.setAssetNote("");
        if (record.getStatus().getStatusId() == 2) {

            record.setMember(record.getMember());
            

            jdbcTemplate.update(SQL_INSERT_ASSET_RECORD,
                    record.getAsset().getAssetId(),
                    record.getEmployee().getUserId(),
                    record.getMember().getUserId(),
                    record.getStatus().getStatusId(),
                    record.getRecordDate(),
                    record.getAssetNote());
            record.setRecordId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

            return record;

        } else {

            record.setMember(null);

        }

        return addAssetRecord(record);
    }

    @Override
    public void deleteAssetRecordAndAsset(int assetId) {

//        boolean checkedOut = false;
//
//        List<AssetRecord> aList = getCurrentAssetRecordByAssetId(assetId);
//
//        for (AssetRecord record : aList) {
//            if (record.getStatus().getStatusId() == 2) {
//                checkedOut = true;
//            } else {
                jdbcTemplate.update(SQL_DELETE_ASSET_RECORD, assetId);
                jdbcTemplate.update(SQL_DELETE_ASSET, assetId);
//            }
//        }
//
//        return checkedOut;
    }

    @Override
    public void deleteAssetRecordsByMemberId(int userId) {

        jdbcTemplate.update(SQL_DELETE_ASSET_RECORD_BY_MEMBER_ID, userId);

    }

    @Override
    public void deleteAssetRecordsByEmployeeId(int userId) {

        jdbcTemplate.update(SQL_DELETE_ASSET_RECORD_BY_EMPLOYEE_ID, userId);

    }

    @Override
    public boolean assetAvailability(int assetId) {

        boolean assetAvailable = true;

        AssetRecord record = getAssetRecordByAssetId(assetId);

        if (record.getStatus().getStatusId() != 1) {
            assetAvailable = false;

        }
        return assetAvailable;
    }

//    @Override
//    public boolean memberExists(int userId) {
//
//        boolean memberExists = false;
//
//        List<UserUserProfile> uList = uDao.getAllUserUserProfiles();
//
//        for (UserUserProfile user : uList) {
//            Authority authority = xDao.getHighestAuthorityByUserName(user.getUserName());
//            if (userId == user.getUserId() && authority.getAuthority().equals("ROLE_USER")) {
//                memberExists = true;
//            }
//        }
//
//        return memberExists;
//    }

    @Override
    public boolean checkDuplicateStatus(int assetId, int statusId) {

        boolean duplicate = true;

        List<AssetRecord> aList = getCurrentAssetRecordByAssetId(assetId);

        for (AssetRecord record : aList) {
            if (record.getStatus().getStatusId() != statusId) {
                duplicate = false;
            }
        }

        return duplicate;
    }

    @Override
    public List<AssetRecord> getAllAssetRecords() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ASSET_RECORDS, new RecordMapper());
    }

    @Override
    public AssetRecord getAssetRecordByAssetId(int assetId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ASSET_RECORD_BY_ASSET_ID, new RecordMapper(), assetId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<AssetRecord> getAssetRecordsByAssetId(int assetId) {
        List<AssetRecord> rList = jdbcTemplate.query(SQL_SELECT_ASSET_RECORDS_BY_ASSET_ID, new RecordMapper(), assetId);
        return rList;
    }

    @Override
    public List<AssetRecord> getEmployeeAssetRecordsByEmployeeId(int userId) {
        List<AssetRecord> rList = jdbcTemplate.query(SQL_SELECT_EMPLOYEE_ASSET_RECORDS_BY_EMPLOYEE_ID, new RecordMapper(), userId);
        return rList;
    }

    @Override
    public List<AssetRecord> getMemberAssetRecordsByMemberId(int userId) {
        List<AssetRecord> rList = jdbcTemplate.query(SQL_SELECT_MEMBER_ASSET_RECORDS_BY_MEMBER_ID, new RecordMapper(), userId);
        return rList;
    }

    @Override
    public List<AssetRecord> getMemberAssetRecordByMemberId(int userId) {
        List<AssetRecord> rList = jdbcTemplate.query(SQL_SELECT_MEMBER_ASSET_RECORD_BY_MEMBER_ID, new RecordMapper(), userId);
        return rList;
    }

    @Override
    public List<AssetRecord> getCurrentAssetRecords() {
        return jdbcTemplate.query(SQL_SELECT_ASSET_RECORDS_BY_CURRENT_STATUS, new RecordMapper());
    }

    @Override
    public List<AssetRecord> getCurrentAssetRecordByAssetId(int assetId) {
        return jdbcTemplate.query(SQL_SELECT_ASSET_RECORD_CURRENT_STATUS_BY_ASSET_ID, new RecordMapper(), assetId);
    }

//    @Override // Not used but may find this useful for other functionality
//    public List<AssetRecord> getCurrentAssetRecordByStatusId(int statusId) {
//        return jdbcTemplate.query(SQL_SELECT_ASSET_RECORD_CURRENT_STATUS_BY_STATUS_ID, new RecordMapper(), statusId);
//    }

    @Override
    public List<AssetRecord> searchAssetRecords(Map<SearchTerm, String> criteria) {

        StringBuilder sQuery = new StringBuilder("select arec.* "
                + "from asset_records arec "
                + "join assets a on a.asset_id = arec.asset_id "
                + "and arec.record_id = ( SELECT MAX(record_id) from asset_records b where arec.asset_id = b.asset_id) "
                + "left outer join user_profiles up on arec.member_id = up.user_id "
                + "where ");

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

        return jdbcTemplate.query(sQuery.toString(), new RecordMapper(), paramVals);

    }

    private final class RecordMapper implements RowMapper<AssetRecord> {

        @Override
        public AssetRecord mapRow(ResultSet rs, int i) throws SQLException {

            AssetRecord record = new AssetRecord();
            record.setRecordId(rs.getInt("record_id"));
            record.setAsset(aDao.getAssetByAssetId(rs.getInt("asset_id")));
            record.setEmployee(uDao.getUserUserProfileByIdB(rs.getInt("employee_id")));
            record.setMember(uDao.getUserUserProfileByIdB(rs.getInt("member_id")));
            record.setStatus(sDao.getStatusById(rs.getInt("status_id")));
            record.setRecordDate(rs.getDate("record_date"));
            record.setAssetNote(rs.getString("note"));

            return record;
        }
    }

}
