package com.objectfrontier.training.java.services.test;

import static com.objectfrontier.training.java.services.service.Query.deleteAllAddress;
import static com.objectfrontier.training.java.services.service.Query.resetAddressId;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.services.connection.ConnectionManager;
import com.objectfrontier.training.java.services.exception.AppException;
import com.objectfrontier.training.java.services.pojo.Address;
import com.objectfrontier.training.java.services.service.AddressService;

public class AddressServiceTest {

    private ConnectionManager connectionManager;
    private AddressService addrServ;
    private boolean flag;

    public void truncateTable() throws Exception {

        Connection connection = connectionManager.getConnection();
        Statement addressDel = connection.createStatement();
        addressDel.executeUpdate(deleteAllAddress);

        Statement addressReset = connection.createStatement();
        addressReset.executeUpdate(resetAddressId);
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @BeforeTest
    public void init() throws Exception {

        addrServ = new AddressService();
        connectionManager = new ConnectionManager();
    }

    @AfterTest
    public void truncate() throws Exception {
        truncateTable();
    }

    @Test(dataProvider = "createPositive",priority = 1,enabled = true)
    public void testCreate_Positive(Address address, Address expectedAddress) throws Exception {

        Connection connection = connectionManager.getConnection();
        Address actualAddress = addrServ.create(connection, address);
        Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] createPositive() throws Exception {

        Address address = new Address("Delhi street", "chennai", 800020);
        Address address2 = new Address("chennai street", "delhi", 901243);
        Address address3 = new Address("punjab street", "pondicherry", 801243);
        Address address4 = new Address("chennai street", "chennai", 800020);

        Address expectedAddress = new Address(1, "Delhi street", "chennai", 800020);
        Address expectedAddress2 = new Address(2, "chennai street", "delhi", 901243);
        Address expectedAddress3 = new Address(3, "punjab street", "pondicherry", 801243);
        Address expectedAddress4 = new Address(4, "chennai street", "chennai", 800020);

        return new Object[][] {

            { address, expectedAddress},
            { address2, expectedAddress2},
            { address3, expectedAddress3},
            { address4, expectedAddress4}

        };
    }

    @Test(dataProvider = "createNegative", priority = 2, enabled = true)
    public void testCreate_Negative(Address address, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            addrServ.create(connection, address);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] createNegative() throws Exception {

        Address address = new Address(null, "city", 800020);
        Address address2 = new Address("street", null, 800020);
        Address address3 = new Address("street", "city", 0);

        int streetException = 301;
        int cityException = 300;
        int postalCodeException = 302;

        return new Object[][] {

            { address, streetException},
            { address2, cityException},
            { address3, postalCodeException},

        };
    }

    @Test(dataProvider = "updatePositive", priority = 3, enabled = true)
    public void testUpdate_Positive(Address address, Address expectedAddress) throws Exception {

        Connection connection = connectionManager.getConnection();
        Address actualAddress = addrServ.update(connection, address);
        Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
        flag =true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] updatePositive() throws Exception {

        Address address = new Address(1, "new delhi street", "coimbatore", 600020);
        Address expectedAddress = new Address(1, "new delhi street", "coimbatore", 600020);

        return new Object[][] {
            { address, expectedAddress }
        };
    }

    @Test(dataProvider = "updateNegative", priority = 4, enabled = true)
    public void testUpdate_Negative(Address address, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            addrServ.update(connection, address);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] updateNegative() throws Exception {

        Address address = new Address(0, "dlehi street", "ch city", 856497);
        Address address2 = new Address(98, "dlehi street", "ch city", 856497);
        Address address3 = new Address(1, null, "ch city", 856497);
        Address address4 = new Address(1, "dlehi street", null, 856497);
        Address address5 = new Address(1, "dlehi street", "ch city", 0);
        Address address6 = new Address(0, null, null, 0);

        int idException = 101;
        int streetException = 301;
        int cityException = 300;
        int postalCodeException = 302;

        return new Object[][] {

            { address, idException},
            { address2, idException},
            { address3, streetException},
            { address4, cityException},
            { address5, postalCodeException},
            { address6, streetException}

        };
    }

    @Test(dataProvider = "readPositive", priority = 5, enabled = true)
    public void testRead_Positive(long addressId, Address expectedAddress) throws Exception {

        Connection connection = connectionManager.getConnection();
        Address actualAddress = addrServ.read(connection, addressId);
        Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider(name = "readPositive")
    public Object[][] readPositive() throws Exception {

        long addressId = 1;
        Address expectedAddress = new Address(1, "new delhi street", "coimbatore", 600020);

        return new Object[][] {
            { addressId, expectedAddress}
        };
    }

    @Test(dataProvider = "readNegative",priority = 6, enabled = true)
    public void testRead_Negative(long addressId, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            addrServ.read(connection, addressId);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] readNegative() throws Exception {

        long addressId = 0;
        int expectedException = 101;

        return new Object[][] {
            { addressId, expectedException}
        };
    }

    @Test(dataProvider = "readAllPositive", priority = 7, enabled = true)
    public void testReadAll_Positive(List<Address> expectedAddressList) throws Exception {

        Connection connection = connectionManager.getConnection();
        List<Address> actualAddressList = addrServ.readAll(connection);
        Assert.assertEquals(actualAddressList.toString(), expectedAddressList.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] readAllPositive() throws Exception {

        Address expectedAddress = new Address(1, "new delhi street", "coimbatore", 600020);
        Address expectedAddress2 = new Address(2, "chennai street", "delhi", 901243);
        Address expectedAddress3 = new Address(3, "punjab street", "pondicherry", 801243);
        Address expectedAddress4 = new Address(4, "chennai street", "chennai", 800020);

        List<Address> expectedAddressList = new ArrayList<>();
                      expectedAddressList.add(expectedAddress);
                      expectedAddressList.add(expectedAddress2);
                      expectedAddressList.add(expectedAddress3);
                      expectedAddressList.add(expectedAddress4);

        return new Object[][] {
            { expectedAddressList}
        };
    }

    @Test(dataProvider = "searchPositive", priority = 8, enabled = true)
    public void testSearch_Positive(String[] fieldName,
                                    String searchText,
                                    List<Address> expectedAddressList) throws Exception {

        Connection connection = connectionManager.getConnection();
        List<Address> actualAddressList = addrServ.search(connection, fieldName, searchText);
        Assert.assertEquals(actualAddressList.toString(), expectedAddressList.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] searchPositive() throws Exception {

        String[] fieldName = { "street", "city", "postalCode" };
        String[] fieldName2 = { "city" };
        String searchText = "80";
        String searchText2 = "c";
        String searchText3 = "pon";

        Address expectedAddress = new Address(1, "new delhi street", "coimbatore", 600020);
        Address expectedAddress2 = new Address(2, "chennai street", "delhi", 901243);
        Address expectedAddress3 = new Address(3, "punjab street", "pondicherry", 801243);
        Address expectedAddress4 = new Address(4, "chennai street", "chennai", 800020);

        List<Address> expectedAddressList = new ArrayList<>();
                      expectedAddressList.add(expectedAddress3);
                      expectedAddressList.add(expectedAddress4);

        List<Address> expectedAddressList2 = new ArrayList<>();
                      expectedAddressList2.add(expectedAddress);
                      expectedAddressList2.add(expectedAddress2);
                      expectedAddressList2.add(expectedAddress4);

        List<Address> expectedAddressList3 = new ArrayList<>();
                      expectedAddressList3.add(expectedAddress3);

        return new Object[][] {

            { fieldName, searchText, expectedAddressList},
            { fieldName, searchText2, expectedAddressList2},
            { fieldName2, searchText3, expectedAddressList3}

        };
    }

    @Test(dataProvider = "searchNegative", priority = 9, enabled = true)
    public void testSearch_Negative(String[] fieldName,
                                    String searchText,
                                    int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            addrServ.search(connection, fieldName, searchText);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] searchNegative() throws Exception {

        String[] fieldName = {""};
        String[] fieldName2 = {"street"};

        String searchText = "c";
        String searchText2 = null;

        int fieldException = 405;
        int searchTextException = 406;

        return new Object[][] {

            { fieldName, searchText, fieldException},
            { fieldName2, searchText2, searchTextException}

        };
    }

    @Test(dataProvider = "deletePositive", priority = 10, enabled = true)
    public void testDelete_Positive(long addressId) throws Exception {

        Connection connection = connectionManager.getConnection();
        addrServ.delete(connection, addressId);
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider(name = "deletePositive")
    public Object[][] deletePositive()throws Exception {

        long addressId = 1;

        return new Object[][] {
            { addressId}
        };
    }

    @Test(dataProvider = "deleteNegative", priority = 11, enabled = true)
    public void testDelete_Negative(long addressId, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            addrServ.delete(connection, addressId);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider(name = "deleteNegative")
    public Object[][] deleteNegative()throws Exception {

        int idException = 101;
        long addressId = 0;

        return new Object[][] {
            {addressId, idException}
        };
    }

}
