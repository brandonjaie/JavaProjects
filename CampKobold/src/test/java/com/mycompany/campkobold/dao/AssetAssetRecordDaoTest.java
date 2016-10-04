/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Asset;
import com.mycompany.campkobold.dto.AssetRecord;
import com.mycompany.campkobold.dto.Authority;
import com.mycompany.campkobold.dto.Category;
import com.mycompany.campkobold.dto.Status;
import com.mycompany.campkobold.dto.UserUserProfile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
public class AssetAssetRecordDaoTest {

    private AssetDao aDao;
    private RecordDao rDao;
    private CategoryDao cDao;
    private StatusDao sDao;
    private UserDao uDao;
    private AuthorityDao xDao;

    private Asset a1;
    private Asset a2;
    private Asset a3;
    private Asset a4;
    private Asset a5;

    private AssetRecord r1;

    private UserUserProfile u1;
    private UserUserProfile u2;
    private UserUserProfile u3;

    public AssetAssetRecordDaoTest() {
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
        aDao = ctx.getBean("AssetDao", AssetDao.class);
        rDao = ctx.getBean("RecordDao", RecordDao.class);
        cDao = ctx.getBean("CategoryDao", CategoryDao.class);
        sDao = ctx.getBean("StatusDao", StatusDao.class);
        uDao = ctx.getBean("UserDao", UserDao.class);
        xDao = ctx.getBean("AuthorityDao", AuthorityDao.class);

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

        a1 = new Asset();
        a1.setBrand("Jansport");
        a1.setCategory(cDao.getCategoryById(1));
        a1.setDescription("Blue Backpack");

        a2 = new Asset();
        a2.setBrand("Coleman");
        a2.setCategory(cDao.getCategoryById(2));
        a2.setDescription("Green Sleeping Bag");

        a3 = new Asset();
        a3.setBrand("Camp Lite");
        a3.setCategory(cDao.getCategoryById(3));
        a3.setDescription("2 Burner Stove");

        a4 = new Asset();
        a4.setBrand("Jerimiah's Paddles");
        a4.setCategory(cDao.getCategoryById(4));
        a4.setDescription("Yellow Kayak Paddle");

        a5 = new Asset();
        a5.setBrand("The North Face");
        a5.setCategory(cDao.getCategoryById(5));
        a5.setDescription("Two Person Tent");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addAssetRecord method, of class RecordDao.
     */
    @Test
    public void testAddAssetAddAssetRecord() {
        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);

        Asset fromDb = aDao.getAssetByAssetId(a1.getAssetId());

        List<AssetRecord> rList = rDao.getAllAssetRecords();

        assertEquals(fromDb.getAssetId(), a1.getAssetId());
        assertEquals(1, rList.size());

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());

        assertEquals(fromDb2.getAsset(), a1);
    }

    /**
     * Test of updateAssetRecord method, of class RecordDao.
     */
    @Test
    public void testUpdateAssetRecord() {
        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a2, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a2.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u1);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

    }

    /**
     * Test of deleteAssetRecordAndAsset method, of class RecordDao.
     */
    @Test
    public void testDeleteAssetRecordAndAsset() {

        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);

        List<AssetRecord> rList = rDao.getAllAssetRecords();

        assertEquals(2, rList.size());

        rDao.deleteAssetRecordAndAsset(a1.getAssetId());

        List<AssetRecord> rList2 = rDao.getAllAssetRecords();

        assertEquals(1, rList2.size());

        Asset fromDb = aDao.getAssetByAssetId(a1.getAssetId());
        assertNull(fromDb);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertNull(fromDb2);

    }

    /**
     * Test of deleteAssetRecordsByMemberId method, of class RecordDao.
     */
    @Test
    public void testDeleteAssetRecordsByMemberId() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a2, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a2.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u1);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        List<AssetRecord> rList2 = rDao.getMemberAssetRecordsByMemberId(u3.getUserId());
        assertEquals(1, rList2.size());

        rDao.deleteAssetRecordsByMemberId(u3.getUserId());

        List<AssetRecord> rList = rDao.getAllAssetRecords();
        boolean deleted = false;
        for (AssetRecord assetRecord : rList) {
            if (assetRecord.getMember() != u3) {
                deleted = true;
            }
        }
        assertTrue(deleted);

    }

    /**
     * Test of deleteAssetRecordsByEmployeeId method, of class RecordDao.
     */
    @Test
    public void testDeleteAssetRecordsByEmployeeId() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u2);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        //Employee checked out an asset, which added 1 record - total records = 2
        List<AssetRecord> eList = rDao.getEmployeeAssetRecordsByEmployeeId(u2.getUserId());
        assertEquals(1, eList.size());

        rDao.deleteAssetRecordsByEmployeeId(u2.getUserId());

        List<AssetRecord> rList = rDao.getAllAssetRecords();
        assertEquals(1, rList.size());
        boolean deleted = false;
        for (AssetRecord assetRecord : rList) {
            if (assetRecord.getEmployee() != u2) {
                deleted = true;
            }
        }
        assertTrue(deleted);

    }

    /**
     * Test of assetAvailability method, of class RecordDao.
     */
    @Test
    public void testAssetAvailability() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u1);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        boolean assetAvailable = rDao.assetAvailability(a1.getAssetId());

        assertFalse(assetAvailable);

        boolean assetAvailable2 = rDao.assetAvailability(a2.getAssetId());

        assertTrue(assetAvailable2);

    }

    /**
     * Test of memberExists method, of class RecordDao.
     */
//    @Test
//    public void testMemberExists() {
//
//        uDao.addUserUserProfile(u1);
//        uDao.addUserUserProfile(u3);
//        
////        Authority authority = xDao.getHighestAuthorityByUserName(u3.getUserName());
//        
//        List<UserUserProfile> uList = uDao.getAllUserUserProfiles();
//        assertEquals(2, uList.size());
//        
////        u3.addAuthority("ROLE_USER");
////        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u3.getUserId());
//
//
//        //Causes a NullPointerException: User's Authority is passed to the method as null
//        boolean memberExists = rDao.memberExists(u3.getUserId());
//
//        assertTrue(memberExists);
//        
////        u1.addAuthority("ROLE_ADMIN");
////        UserUserProfile fromDb2 = uDao.getUserUserProfileByIdB(u1.getUserId());
//
//        boolean memberExists2 = rDao.memberExists(u1.getUserId());
//
//        assertFalse(memberExists2);
//
//    }

    /**
     * Test of checkDuplicateStatus method, of class RecordDao.
     */
    @Test
    public void testCheckDuplicateStatus() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb.getStatus().getStatus(), "AVAILABLE");

        fromDb.getEmployee();
        fromDb.setMember(u3);
        fromDb.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb, u1);
        assertEquals(fromDb.getStatus().getStatus(), "CHECKED OUT");

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        boolean duplicateStatus = rDao.checkDuplicateStatus(fromDb2.getAsset().getAssetId(), fromDb2.getStatus().getStatusId());
        assertTrue(duplicateStatus);

        AssetRecord fromDb3 = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb3.getStatus().getStatus(), "CHECKED OUT");

        fromDb3.getEmployee();
        fromDb3.setMember(u3);
        fromDb3.setStatus(sDao.getStatusById(3));

        boolean duplicateStatus2 = rDao.checkDuplicateStatus(fromDb3.getAsset().getAssetId(), fromDb3.getStatus().getStatusId());
        assertFalse(duplicateStatus2);

    }

    /**
     * Test of deleteUserUserProfile method, of class UserDao.
     */
    @Test
    public void testDeleteUserUserProfile() {
        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u2);
        uDao.addUserUserProfile(u3);

        aDao.addAsset(a2, u1);
        aDao.addAsset(a3, u2);
        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a2.getAssetId());
        assertEquals(fromDb2.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u1);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        AssetRecord fromDb4 = rDao.getAssetRecordByAssetId(a3.getAssetId());
        assertEquals(fromDb4.getStatus().getStatus(), "AVAILABLE");

        fromDb2.getEmployee();
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u2);
        assertEquals(fromDb2.getStatus().getStatus(), "CHECKED OUT");

        //when an asset is created, a record is created.
        //when that asset is updated, another record is created.
        List<AssetRecord> rList = rDao.getAllAssetRecords();
        assertEquals(4, rList.size());

        List<UserUserProfile> uList3 = uDao.getAllUserUserProfiles();
        assertEquals(3, uList3.size());

        uDao.deleteUserUserProfile(u3.getUserId(), u1);
        List<UserUserProfile> uList2 = uDao.getAllUserUserProfiles();
        assertEquals(2, uList2.size());

        uDao.deleteUserUserProfile(u2.getUserId(), u1);
        List<UserUserProfile> uList1 = uDao.getAllUserUserProfiles();
        assertEquals(1, uList1.size());

        UserUserProfile fromDb = uDao.getUserUserProfileByIdB(u3.getUserId());
        assertNull(fromDb);

        UserUserProfile fromDb3 = uDao.getUserUserProfileByIdB(u2.getUserId());
        assertNull(fromDb3);

        List<AssetRecord> rList2 = rDao.getAllAssetRecords();
        assertEquals(2, rList2.size());

    }

    /**
     * Test of getAllAssetRecords method, of class RecordDao.
     */
    @Test
    public void testGetAllAssetRecords() {
        uDao.addUserUserProfile(u1);
        List<AssetRecord> rList0 = rDao.getAllAssetRecords();
        assertEquals(0, rList0.size());

        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);
        aDao.addAsset(a3, u1);
        aDao.addAsset(a4, u1);
        aDao.addAsset(a5, u1);

        List<AssetRecord> rList5 = rDao.getAllAssetRecords();
        assertEquals(5, rList5.size());
        assertFalse(rList5.isEmpty());

    }

    /**
     * Test of getAssetById method, of class AssetDao.
     */
    @Test
    public void testGetAssetByAssetId() {
        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);

        Asset fromDb = aDao.getAssetByAssetId(a1.getAssetId());
        assertEquals(fromDb.getAssetId(), a1.getAssetId());
        assertFalse(fromDb.getAssetId() == a2.getAssetId());

    }

    /**
     * Test of getAssetRecordByAssetId method, of class RecordDao.
     */
    @Test
    public void testGetAssetRecordByAssetId() {
        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        assertEquals(fromDb.getAsset().getAssetId(), a1.getAssetId());

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a2.getAssetId());
        assertFalse(fromDb2 == null);
    }

    /**
     * Test of getAssetRecordsByAssetId method, of class RecordDao.
     */
    @Test
    public void testGetAssetRecordsByAssetId() {
        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);

        List<AssetRecord> rList = rDao.getAssetRecordsByAssetId(a1.getAssetId());

        assertEquals(1, rList.size());

        Boolean exists = false;
        int count = 0;

        for (AssetRecord assetRecord : rList) {
            if (assetRecord.getAsset().getAssetId() == a1.getAssetId()) {
                exists = true;
                count++;
            }
        }

        assertTrue(exists);
        assertEquals(count, 1);

    }

    /**
     * Test of getEmployeeAssetRecordsByEmployeeId method, of class RecordDao.
     */
    @Test
    public void testGetEmployeeAssetRecordsByEmployeeId() {
        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);
        aDao.addAsset(a3, u1);

        AssetRecord e1 = rDao.getAssetRecordByAssetId(a1.getAssetId());

        List<AssetRecord> eList = rDao.getEmployeeAssetRecordsByEmployeeId(e1.getEmployee().getUserId());

        assertEquals(3, eList.size());

        Boolean exists = false;
        int count = 0;

        for (AssetRecord assetRecord : eList) {
            if (assetRecord.getEmployee().getUserId() == e1.getEmployee().getUserId()) {
                exists = true;
                count++;
            }
        }
        assertTrue(exists);
        assertEquals(count, 3);
    }

    /**
     * Test of getMemberAssetRecordsByMemberId method, of class RecordDao.
     */
    @Test
    public void testGetMemberAssetRecordsByMemberId() {
        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);
        aDao.addAsset(a2, u1);
        aDao.addAsset(a3, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        fromDb.setMember(u3);
        fromDb.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a2.getAssetId());
        fromDb2.setMember(u3);
        fromDb2.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb2, u1);

        AssetRecord fromDb3 = rDao.getAssetRecordByAssetId(a3.getAssetId());
        fromDb3.setMember(u3);
        fromDb3.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb3, u1);

        List<AssetRecord> mList = rDao.getMemberAssetRecordsByMemberId(fromDb.getMember().getUserId());

        assertEquals(3, mList.size());

        Boolean exists = false;
        int count = 0;

        for (AssetRecord assetRecord : mList) {
            if (assetRecord.getMember().getUserId() == fromDb.getMember().getUserId()) {
                exists = true;
                count++;
            }
        }
        assertTrue(exists);
        assertEquals(count, 3);
    }

    /**
     * Test of getAssetRecordsByCurrentStatus method, of class RecordDao.
     */
    @Test
    public void testGetCurrentAssetRecords() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        fromDb.setMember(u3);
        fromDb.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());

        List<AssetRecord> rList = rDao.getAllAssetRecords();

        assertEquals(2, rList.size());

        List<AssetRecord> cList = rDao.getCurrentAssetRecords();

        assertEquals(1, cList.size());

        Boolean currentRecord = false;

        for (AssetRecord assetRecord : cList) {

            if (assetRecord.getRecordId() == fromDb2.getRecordId()) {
                currentRecord = true;
            }
        }

        assertTrue(currentRecord);

    }

    /**
     * Test of getAssetRecordCurrentStatusByAssetId method, of class RecordDao.
     */
    @Test
    public void testGetCurrentAssetRecordByAssetId() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        fromDb.setMember(u3);
        fromDb.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb, u1);

        AssetRecord fromDb2 = rDao.getAssetRecordByAssetId(a1.getAssetId());

        List<AssetRecord> rList = rDao.getAllAssetRecords();

        assertEquals(2, rList.size());

        List<AssetRecord> cList = rDao.getCurrentAssetRecordByAssetId(a1.getAssetId());

        assertEquals(1, cList.size());

        Boolean currentRecord = false;

        for (AssetRecord assetRecord : cList) {

            if (assetRecord.getAsset().getAssetId() == fromDb2.getAsset().getAssetId()
                    && assetRecord.getRecordId() == fromDb2.getRecordId()) {
                currentRecord = true;
            }
        }

        assertTrue(currentRecord);

    }

    /**
     * Test of searchAssetRecords method, of class RecordDao.
     */
    @Test
    public void testSearchAssetRecords() {

        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());
        fromDb.setMember(u3);
        fromDb.setStatus(sDao.getStatusById(2));

        rDao.updateAssetRecord(fromDb, u1);

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.LAST_NAME, "USER");

        List<AssetRecord> rList = rDao.searchAssetRecords(criteria);

        assertEquals(1, rList.size());

        Boolean exists = false;

        for (AssetRecord assetRecord : rList) {
            if (assetRecord.getMember().getLastName().equals("USER")) {
                exists = true;
            }
        }

        assertTrue(exists);
    }

    @Test
    public void testSearchAssets() {
        uDao.addUserUserProfile(u1);
        uDao.addUserUserProfile(u3);
        aDao.addAsset(a1, u1);

        Asset fromDb = aDao.getAssetByAssetId(a1.getAssetId());

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.CATEGORY_ID, "1");

        List<Asset> aList = aDao.searchAssets(criteria);

        assertEquals(1, aList.size());

        Boolean exists = false;

        for (Asset asset : aList) {
            if (asset.getCategory().getCategoryId() == fromDb.getCategory().getCategoryId()) {
                exists = true;
            }
        }

        assertTrue(exists);
    }

    /**
     * Test of getCategoryById method, of class CategoryDao.
     */
    @Test
    public void testGetCategoryById() {

        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);

        assertEquals(1, a1.getCategory().getCategoryId());

        Category fromDb = cDao.getCategoryById(a1.getCategory().getCategoryId());

        assertEquals(fromDb.getCategoryId(), a1.getCategory().getCategoryId());

        assertTrue(fromDb.getCategoryId() == 1);

    }

    /**
     * Test of getStatusById method, of class StatusDao.
     */
    @Test
    public void testGetStatusById() {

        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());

        assertEquals(1, fromDb.getStatus().getStatusId());

        Status fromDb2 = sDao.getStatusById(fromDb.getStatus().getStatusId());

        assertEquals(fromDb2.getStatusId(), fromDb.getStatus().getStatusId());

        assertTrue(fromDb2.getStatusId() == 1);

    }

    /**
     * Test of getStatusByName method, of class StatusDao.
     */
    @Test
    public void testGetStatusByName() {

        uDao.addUserUserProfile(u1);
        aDao.addAsset(a1, u1);

        AssetRecord fromDb = rDao.getAssetRecordByAssetId(a1.getAssetId());

        assertEquals(fromDb.getStatus().getStatus(), "AVAILABLE");

        Status fromDb2 = sDao.getStatusByName(fromDb.getStatus().getStatus());

        assertEquals(fromDb2.getStatus(), fromDb.getStatus().getStatus());

        assertTrue(fromDb2.getStatus().equals("AVAILABLE"));

    }

}
