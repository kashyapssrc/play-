package com.objectfrontier.training.java.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.jdbc.service.Address;
import com.objectfrontier.training.java.jdbc.service.AddressService;
import com.objectfrontier.training.java.jdbc.service.ConnectionManager;

public class AddressServiceTest {

    private AddressService addrServ;
    private ConnectionManager connectionManager;

    @BeforeClass
    private void init() {

        connectionManager = new ConnectionManager();
        addrServ = new AddressService();
    }

    @Test(dataProvider = "create_positive", priority =1)
    public void testCreate_Positive(Connection connection,
                                    Address address,
                                    Address expected) throws Exception {

            try {

                connection.setAutoCommit(false);
                Address create = addrServ.create(connection, address);
                Assert.assertEquals(create.toString(), expected.toString());
                connection.commit();
            } catch (Exception e) {
                Assert.fail("address not created");
            } finally {
                connection.close();
            }
    }

    @DataProvider(name = "create_positive")
    public Object[][] createPositive() throws SQLException {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("new street");
                address.setCity("new city");
                address.setPostal_code(6543213);

        Address expected = new Address();
                expected.setId(1);
                expected.setStreet("new street");
                expected.setCity("new city");
                expected.setPostal_code(6543213);

        return new Object[][] {
            {connection, address, expected }
        };
    }

    @Test(dataProvider = "create_negative", priority = 2)
    public void testCreate_Negative(Connection connection,
                                    Address address,
                                    String expected) throws Exception {

            try {

                connection.setAutoCommit(false);
                addrServ.create(connection, address);
                connection.commit();
            } catch (Exception e) {

                Assert.assertEquals(e.getClass()
                                     .getSimpleName(), expected);
            } finally {
                connection.close();
            }
    }

    @DataProvider(name = "create_negative")
    public Object[][] createNegative() throws SQLException {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("new street");
                address.setPostal_code(6543213);

        String expected = "MySQLIntegrityConstraintViolationException";

        return new Object[][] {
            {connection, address, expected }
        };
    }

    @Test(dataProvider = "updatePositive", priority =3)
    public void testUpdate_Positive(Connection connection,
                                    Address address,
                                    Address expectedAddress) throws Exception {

        try {

            connection.setAutoCommit(false);
            Address actualAddress = addrServ.update(connection, address);
            Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("update was not performed");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "updatePositive")
    public Object[][] updatePositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setId(1);
                address.setStreet("2 street");
                address.setCity("2 city");
                address.setPostal_code(600020);

        Address expectedAddress = new Address();
                expectedAddress.setId(1);
                expectedAddress.setStreet("2 street");
                expectedAddress.setCity("2 city");
                expectedAddress.setPostal_code(600020);

        return new Object[][] {
            {connection, address, expectedAddress }
        };
    }

    @Test(dataProvider = "updateNegative", priority = 4)
    public void testUpdate_Negative(Connection connection,
                                    Address address,
                                    String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            addrServ.update(connection, address);
            connection.commit();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), expectedException);
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "updateNegative")
    public Object[][] updateNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
        String expectedException = "nothing to be updated";

        return new Object[][] {
            {connection, address, expectedException}
        };
    }

    @Test(dataProvider = "readPositive",priority = 5)
    public void testRead_Positive(Connection connection,
                                  long addressId,
                                  Address expectedAddress) throws Exception {

        try {

            connection.setAutoCommit(false);
            Address actualAddress = addrServ.read(connection, addressId);
            Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("read was not performed");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readPositive")
    public Object[][] readPositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        long addressId = 1;

        Address expectedAddress = new Address();
                expectedAddress.setStreet("2 street");
                expectedAddress.setCity("2 city");
                expectedAddress.setPostal_code(600020);
                expectedAddress.setId(1);

        return new Object[][] {
            {connection, addressId, expectedAddress}
        };
    }

    @Test(dataProvider = "readNegative",priority = 6)
    public void testRead_Negative(Connection connection,
                                  long addressId,
                                  String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            addrServ.read(connection, addressId);
            connection.commit();
        } catch (Exception e) {

            Assert.assertEquals(e.getClass()
                                 .getSimpleName(), expectedException);
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readNegative")
    public Object[][] readNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        long addressId = 0;
        String expectedException = "SQLException";

        return new Object[][] {
            {connection, addressId, expectedException}
        };
    }

    @Test(dataProvider = "readAllPositive", priority = 7)
    public void testReadAll_Positive(Connection connection,
                                     List<Address> expectedAddressList) throws Exception {

        try {

            connection.setAutoCommit(false);
            List<Address> actualAddressList = addrServ.readAll(connection);
            Assert.assertEquals(actualAddressList.toString(), expectedAddressList.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("read all operation was not performed");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readAllPositive")
    public Object[][] readAllPositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address expectedAddress = new Address();
                expectedAddress.setId(1);
                expectedAddress.setStreet("2 street");
                expectedAddress.setCity("2 city");
                expectedAddress.setPostal_code(600020);

        List<Address> expectedAddressList = new ArrayList<>();
                      expectedAddressList.add(expectedAddress);

        return new Object[][] {
            {connection, expectedAddressList}
        };
    }

    @Test(dataProvider = "deletePositive", priority =8)
    public void testDelete_Positive(Connection connection,
                                    long addressId) throws Exception {

        try {

            connection.setAutoCommit(false);
            addrServ.delete(connection, addressId);
            connection.commit();
        } catch (Exception e) {
            Assert.fail("delete was not performed");
        }
    }

    @DataProvider(name = "deletePositive")
    public Object[][] deletePositive()throws Exception {

        Connection connection = connectionManager.getConnection();
        long addressId = 1;

        return new Object[][] {
            {connection, addressId}
        };
    }

    @Test(dataProvider = "deleteNegative", priority =9)
    public void testDelete_Negative(Connection connection,
                                    long addressId,
                                    String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            addrServ.delete(connection, addressId);
            connection.commit();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), expectedException);
        }
    }

    @DataProvider(name = "deleteNegative")
    public Object[][] deleteNegative()throws Exception {

        Connection connection = connectionManager.getConnection();
        String expectedException = "nothing to be deleted";
        long addressId = 0;

        return new Object[][] {
            {connection, addressId, expectedException}
        };
    }
}
