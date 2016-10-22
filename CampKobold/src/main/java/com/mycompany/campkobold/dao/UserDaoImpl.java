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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brandon
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private RecordDao rDao;
    private AuthorityDao xDao;
    private StatusDao sDao;
    private AssetDao aDao;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setRDao(RecordDao rDao) {
        this.rDao = rDao;
    }

    public void setXDao(AuthorityDao xDao) {
        this.xDao = xDao;
    }

    public void setSDao(StatusDao sDao) {
        this.sDao = sDao;
    }

    public void setADao(AssetDao aDao) {
        this.aDao = aDao;
    }

    private static final String SQL_INSERT_USER
            = "insert into users (username, password, enabled) value (?, ?, ?)";

    private static final String SQL_INSERT_USER_PROFILE
            = "insert into user_profiles (user_id, first_name, last_name, email, phone) value (?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_AUTHORITY
            = "insert into authorities (username, authority) VALUES(?, ?)";

    private static final String SQL_UPDATE_USER
            = "update users set username = ?, password =?, enabled = ? where user_id =?";

    private static final String SQL_UPDATE_USER_PROFILE
            = "update user_profiles set first_name = ?, last_name =?, email = ?, phone = ? where user_id =?";

    private static final String SQL_DELETE_USER
            = "delete from users where user_id = ?";

    private static final String SQL_DELETE_USER_PROFILE
            = "delete from user_profiles where user_id = ?";

    private static final String SQL_DELETE_AUTHORITIES_USERNAME
            = "delete from authorities where username = ?";

    private static final String SQL_SELECT_ALL_USER_USER_PROFILES
            = "select * from users "
            + "join user_profiles "
            + "on users.user_id = user_profiles.user_id ";

//    private static final String SQL_SELECT_USER_USER_PROFILE_BY_ID
//            = "select * "
//            + "from users u "
//            + "join user_profiles up "
//            + "on u.user_id = up.user_id "
//            + "join authorities a "
//            + "on u.username = a.username "
//            + "where u.user_id = ?";
    private static final String SQL_SELECT_USER_USER_PROFILE_BY_ID_B
            = "select * "
            + "from users u "
            + "join user_profiles up "
            + "on u.user_id = up.user_id "
            + "where u.user_id = ?";

    private static final String SQL_SELECT_USER_USER_PROFILE_BY_USERNAME
            = "select * "
            + "from users u "
            + "join user_profiles up "
            + "on u.user_id = up.user_id "
            + "where u.username = ?";

    private static final String SQL_RESET_PASSWORD
            = "UPDATE users SET password = 'kobolds-are-great!' WHERE user_id = ?";

    private static final String SQL_UPDATE_PASSWORD
            = "UPDATE users SET password = ? WHERE user_id = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public UserUserProfile addUserUserProfile(UserUserProfile userUserProfile) {

        jdbcTemplate.update(SQL_INSERT_USER,
                userUserProfile.getUserName(),
                userUserProfile.getPassword(),
                userUserProfile.getEnabled());

        userUserProfile.setUserId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        ArrayList<String> authorities = userUserProfile.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    userUserProfile.getUserName(),
                    authority);
        }

        jdbcTemplate.update(SQL_INSERT_USER_PROFILE,
                userUserProfile.getUserId(),
                userUserProfile.getFirstName(),
                userUserProfile.getLastName(),
                userUserProfile.getEmail(),
                userUserProfile.getPhone());

        return userUserProfile;
    }

    @Override
    public void updateUserUserProfile(UserUserProfile uup) {
        jdbcTemplate.update(SQL_UPDATE_USER,
                uup.getUserName(),
                uup.getPassword(),
                uup.getEnabled(),
                uup.getUserId());

        jdbcTemplate.update(SQL_UPDATE_USER_PROFILE,
                uup.getFirstName(),
                uup.getLastName(),
                uup.getEmail(),
                uup.getPhone(),
                uup.getUserId());

    }

    @Override
    public void deleteUserUserProfile(int userId, UserUserProfile user) {
        UserUserProfile userToDelete;
        userToDelete = getUserUserProfileByIdB(userId);
        String username = userToDelete.getUserName();
        List<AssetRecord> records = rDao.getMemberAssetRecordByMemberId(userId);

        Authority authority = xDao.getHighestAuthorityByUserName(username);

        if (authority.getAuthority().equals("ROLE_USER")) {

            for (AssetRecord record : records) {
                record.setStatus(sDao.getStatusByName("AVAILABLE"));
                record.setAssetNote("Member Deleted");
                rDao.updateAssetRecord(record, user);
            }
            rDao.deleteAssetRecordsByMemberId(userId);
        } else {
            rDao.deleteAssetRecordsByEmployeeId(userId);
        }

        jdbcTemplate.update(SQL_DELETE_AUTHORITIES_USERNAME, username);
        jdbcTemplate.update(SQL_DELETE_USER_PROFILE, userId);
        jdbcTemplate.update(SQL_DELETE_USER, userId);

    }

    @Override
    public List<UserUserProfile> getAllUserUserProfiles() {

        List<UserUserProfile> uList = jdbcTemplate.query(SQL_SELECT_ALL_USER_USER_PROFILES, new UserDaoImpl.UserUserProfileMapperB());
        for (UserUserProfile user : uList) {
            user.setAuthority(xDao.getHighestAuthorityByUserName(user.getUserName()));
        }
        return uList;
    }

    @Override
    public List<UserUserProfile> getAllMembers() {

        List<UserUserProfile> uList = getAllUserUserProfiles();
        List<UserUserProfile> mList = new ArrayList<>();

        for (UserUserProfile user : uList) {
            Authority authority = xDao.getHighestAuthorityByUserName(user.getUserName());
            if (user.getUserName().equals(authority.getUserName()) && authority.getAuthority().equals("ROLE_USER")) {
                mList.add(user);
            }
        }

        return mList;
    }
    
    @Override
    public List<UserUserProfile> getAllEmployees() {

        List<UserUserProfile> uList = getAllUserUserProfiles();
        List<UserUserProfile> eList = new ArrayList<>();

        for (UserUserProfile user : uList) {
            Authority authority = xDao.getHighestAuthorityByUserName(user.getUserName());
            if (user.getUserName().equals(authority.getUserName()) && authority.getAuthority().equals("ROLE_ADMIN") 
                    || user.getUserName().equals(authority.getUserName()) && authority.getAuthority().equals("ROLE_EMPLOYEE")) {
                eList.add(user);
            }
        }

        return eList;
    }

    @Override
    public UserUserProfile getUserUserProfileByIdB(int userId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_USER_PROFILE_BY_ID_B, new UserDaoImpl.UserUserProfileMapperB(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserUserProfile getUserUserProfileByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_USER_PROFILE_BY_USERNAME, new UserDaoImpl.UserUserProfileMapperB(), username);
        } catch (UncategorizedSQLException e) {
            return null;
        }
    }

    @Override
    public boolean userNameAvailability(String username) {

        boolean userNameAvailable = true;

        List<UserUserProfile> users = getAllUserUserProfiles();

        for (UserUserProfile uup : users) {
            if (username != null && username.equals(uup.getUserName())) {
                userNameAvailable = false;
            }
        }

        return userNameAvailable;
    }

    @Override
    public void resetPassword(int userId) {
        jdbcTemplate.update(SQL_RESET_PASSWORD, userId);
    }

    @Override
    public void updatePassword(UserUserProfile user) {
        jdbcTemplate.update(SQL_UPDATE_PASSWORD,
                user.getPassword(),
                user.getUserId());
    }

    @Override
    public List<UserUserProfile> searchUserUserProfiles(Map<SearchTerm, String> criteria) {

        String order = "order by "
                + "(case when authority = 'ROLE_ADMIN' then 1 "
                + "when authority = 'ROLE_EMPLOYEE' then 2 "
                + "else 3 end) LIMIT 1";

        StringBuilder sQuery = new StringBuilder("select * "
                + "from users u "
                + "left outer join user_profiles up "
                + "on u.user_id = up.user_id "
                + "join authorities a "
                + "on a.username = u.username "
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

            sQuery.append(" = ? " + order);

            paramVals[paramPosition] = criteria.get(currentKey);

            paramPosition++;

        }

        return jdbcTemplate.query(sQuery.toString(), new UserDaoImpl.UserUserProfileMapper(), paramVals);

    }

    @Override
    public List<UserUserProfile> searchMembers(Map<SearchTerm, String> criteria) {

        StringBuilder sQuery = new StringBuilder("select * "
                + "from authorities a "
                + "join users u "
                + "on a.username = u.username "
                + "join user_profiles up "
                + "on u.user_id = up.user_id "
                + "where a.username IN (SELECT a.username "
                + "from authorities a "
                + "group by username "
                + "HAVING COUNT(*) = 1) "
                + "and ");

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

        return jdbcTemplate.query(sQuery.toString(), new UserDaoImpl.UserUserProfileMapper(), paramVals);

    }

    private final class UserUserProfileMapper implements RowMapper<UserUserProfile> {

        @Override
        public UserUserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserUserProfile user = new UserUserProfile();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getInt("enabled"));

            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAuthority(xDao.getAuthorityByName(rs.getString("authority")));

            return user;
        }
    }

    private final class UserUserProfileMapperB implements RowMapper<UserUserProfile> {

        @Override
        public UserUserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserUserProfile user = new UserUserProfile();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getInt("enabled"));

            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));

            return user;
        }
    }

}
