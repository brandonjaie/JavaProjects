/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.Authority;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Brandon
 */
public class UserDaoTest {

    private UserDao uDao;
    private RecordDao rDao;
    private AuthorityDao xDao;
    private StatusDao sDao;
    private AssetDao aDao;
    
    private Asset a1;
    private Asset a2;
    
    private AssetRecord r1;
    
    private UserUserProfile u1;
    private UserUserProfile u2;
    private UserUserProfile u3;

    public UserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        uDao = ctx.getBean("UserDao", UserDao.class);
        rDao = ctx.getBean("RecordDao", RecordDao.class);
        xDao = ctx.getBean("AuthorityDao", AuthorityDao.class);
        sDao = ctx.getBean("StatusDao", StatusDao.class);
        aDao = ctx.getBean("AssetDao", AssetDao.class);

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("delete from asset_records");
        cleaner.execute("delete from assets");

        cleaner.execute("delete from authorities");
        cleaner.execute("delete from user_profiles");
        cleaner.execute("delete from users");

        u1 = new UserUserProfile();
        u1.setUserName("test_admin");
        u1.setPassword("kobolds-are-great!");
        u1.setEnabled(1);

        u1.setIsAdmin("isAdmin");
        u1.addAuthority("ROLE_ADMIN");
        u1.addAuthority("ROLE_EMPLOYEE");
        u1.addAuthority("ROLE_USER");

        u1.setFirstName("TEST");
        u1.setLastName("ADMIN");
        u1.setEmail("admn@gmail.com");
        u1.setPhone("555-ADMN");

        u2 = new UserUserProfile();
        u2.setUserName("test_employee");
        u2.setPassword("kobolds-are-great!");
        u2.setEnabled(1);

        u2.setIsEmployee("isEmployee");
        u2.addAuthority("ROLE_EMPLOYEE");
        u2.addAuthority("ROLE_USER");

        u2.setFirstName("TEST");
        u2.setLastName("EMPLOYEE");
        u2.setEmail("empl@koboldcamp.com");
        u2.setPhone("555-EMPL");

        u3 = new UserUserProfile();
        u3.setUserName("test_user");
        u3.setPassword("password");
        u3.setEnabled(1);

        u3.addAuthority("ROLE_USER");

        u3.setFirstName("TEST");
        u3.setLastName("USER");
        u3.setEmail("user@gmail.com");
        u3.setPhone("555-USER");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUserUserProfile method, of class UserDao.
     */
    @Test
    public void testAddUserUserProfile() {
        uDao.addUserUserProfile(u1);

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u1.getUserId());
        List<UserUserProfile> uList = uDao.getAllUserUserProfiles();

        assertEquals(fromDb.getUserId(), u1.getUserId());
        assertEquals(1, uList.size());
        assertNotNull(fromDb);

    }

    /**
     * Test of updateUserUserProfile method, of class UserDao.
     */
    @Test
    public void testUpdateUserUserProfile() {
        uDao.addUserUserProfile(u3);

        u3.setFirstName("Brandon");
        u3.setLastName("Mathis");

        uDao.updateUserUserProfile(u3);
        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u3.getUserId());

        assertEquals(fromDb.getFirstName(), u3.getFirstName());
        assertNotEquals(fromDb.getLastName(), "USER");

    }


    /**
     * Test of getAllUserUserProfiles method, of class UserDao.
     */
    @Test
    public void testGetAllUserUserProfiles() {

        List<UserUserProfile> uList0 = uDao.getAllUserUserProfiles();
        assertEquals(0, uList0.size());

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);
        uDao.addUserUserProfile(u3);

        List<UserUserProfile> uList3 = uDao.getAllUserUserProfiles();
        assertEquals(3, uList3.size());
        assertFalse(uList3.isEmpty());

    }

    /**
     * Test of getUserUserProfileByIdB method, of class UserDao.
     */
    @Test
    public void testGetUserUserProfileByIdB() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u1.getUserId());

        assertEquals(fromDb.getUserId(), u1.getUserId());

        UserUserProfile fromDb2 = uDao.getUserUserProfileByIdB(u2.getUserId());

        assertFalse(fromDb2 == null);
    }

    /**
     * Test of getUserUserProfileByUsername method, of class UserDao.
     */
    @Test
    public void testGetUserUserProfileByUsername() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);

        UserUserProfile fromDb = uDao.getUserUserProfileByUsername(u3.getUserName());

        assertEquals(fromDb.getUserName(), u3.getUserName());
        assertEquals(fromDb.getUserName(), "test_user");
        assertFalse(fromDb.getUserName().equals("test_admin"));

        UserUserProfile fromDb2 = uDao.getUserUserProfileByUsername(u1.getUserName());

        assertEquals(fromDb2.getUserName(), u1.getUserName());
        assertEquals(fromDb2.getUserName(), "test_admin");
        assertFalse(fromDb2.getUserName().equals("test_user"));

    }

    /**
     * Test of resetPassword method, of class UserDao.
     */
    @Test
    public void testResetPassword() {

        UserUserProfile testUser = new UserUserProfile();
        testUser.setUserName("test_user");
        testUser.setPassword("password");
        testUser.setEnabled(1);

        testUser.addAuthority("ROLE_USER");

        testUser.setFirstName("TEST");
        testUser.setLastName("USER");
        testUser.setEmail("user@gmail.com");
        testUser.setPhone("555-USER");

        uDao.addUserUserProfile(testUser);

        uDao.resetPassword(testUser.getUserId());

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(testUser.getUserId());

        assertEquals(fromDb.getPassword(), "kobolds-are-great!");
        assertFalse(fromDb.getPassword().equals("password"));

    }

    /**
     * Test of updatePassword method, of class UserDao.
     */
    @Test
    public void testUpdatePassword() {

        uDao.addUserUserProfile(u1);

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u1.getUserId());

        assertTrue(fromDb.getPassword().equals("kobolds-are-great!"));

        u1.setPassword("password");

        uDao.updatePassword(u1);

        UserUserProfile fromDb2 = uDao.getUserUserProfileByIdB(u1.getUserId());

        assertEquals(fromDb2.getPassword(), "password");
        assertFalse(fromDb2.getPassword().equals("kobolds-are-great!"));

    }

    /**
     * Test of userNameAvailability method, of class UserDao.
     */
    @Test
    public void testUserNameAvailability() {

        uDao.addUserUserProfile(u1);

        Boolean availability = uDao.userNameAvailability(u1.getUserName());

        assertFalse(availability);

        UserUserProfile u4 = new UserUserProfile();
        u4.setUserName("new_user");

        Boolean availability2 = uDao.userNameAvailability(u4.getUserName());

        assertTrue(availability2);

    }

    /**
     * Test of searchUserUserProfiles method, of class UserDao.
     */
    @Test
    public void testSearchUserUserProfiles() {

        uDao.addUserUserProfile(u1);

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.LAST_NAME, "ADMIN");

        List<UserUserProfile> uList = uDao.searchUserUserProfiles(criteria);

        assertEquals(1, uList.size());

        Boolean exists = false;

        for (UserUserProfile user : uList) {
            if (user.getLastName().equals("ADMIN")) {
                exists = true;
            }
        }

        assertTrue(exists);
    }

   /**
     * Test of getHighestAuthorityByUserName method, of class AuthorityDao.
     */
    @Test
    public void testGetHighestAuthorityByUserName() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u1.getUserId());

        UserUserProfile fromDb2 = uDao.getUserUserProfileByIdB(u2.getUserId());

        Authority highestAuthority = xDao.getHighestAuthorityByUserName(fromDb.getUserName());

        Authority highestAuthority2 = xDao.getHighestAuthorityByUserName(fromDb2.getUserName());

        assertEquals(highestAuthority.getAuthority(), "ROLE_ADMIN");

        assertEquals(highestAuthority2.getAuthority(), "ROLE_EMPLOYEE");

    }

    /**
     * Test of getAuthorityByName method, of class AuthorityDao.
     */
    @Test
    public void testGetAuthorityByName() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u1.getUserId());

        Authority highestAuthority = xDao.getHighestAuthorityByUserName(fromDb.getUserName());

        Authority result = xDao.getAuthorityByName(highestAuthority.getAuthority());

        assertEquals(result.getAuthority(), "ROLE_ADMIN");
        

        UserUserProfile fromDb2 = uDao.getUserUserProfileByIdB(u2.getUserId());

        Authority highestAuthority2 = xDao.getHighestAuthorityByUserName(fromDb2.getUserName());

        Authority result2 = xDao.getAuthorityByName(highestAuthority2.getAuthority());

        assertEquals(result2.getAuthority(), "ROLE_EMPLOYEE");

    }

}
