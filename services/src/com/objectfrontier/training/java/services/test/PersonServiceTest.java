package com.objectfrontier.training.java.services.test;

import static com.objectfrontier.training.java.services.service.Query.deleteAllAddress;
import static com.objectfrontier.training.java.services.service.Query.resetAddressId;
import static com.objectfrontier.training.java.services.service.Query.truncatePerson;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.services.connection.ConnectionManager;
import com.objectfrontier.training.java.services.exception.AppException;
import com.objectfrontier.training.java.services.pojo.Address;
import com.objectfrontier.training.java.services.pojo.Person;
import com.objectfrontier.training.java.services.service.DataManager;
import com.objectfrontier.training.java.services.service.PersonService;

public class PersonServiceTest {

    private ConnectionManager connectionManager;
    private PersonService personServ;
    private boolean flag;
    private DataManager dataManager;

    public void truncateTable() throws Exception  {

        Connection connection = connectionManager.getConnection();

        Statement personTrunc = connection.createStatement();
        personTrunc.execute(truncatePerson);

        Statement deleteAddress = connection.createStatement();
        deleteAddress.execute(deleteAllAddress);

        Statement resetAddress = connection.createStatement();
        resetAddress.execute(resetAddressId);

        connection.close();
    }

    @BeforeTest
    public void init() throws Exception {

        personServ = new PersonService();
        connectionManager = new ConnectionManager();
        dataManager = new DataManager();
    }

    @AfterTest
    public void truncate() throws Exception {
        truncateTable();
    }

    @Test(dataProvider = "createPositive", priority =1,enabled = true)
    public void testCreate_Positive(Person person, Person ExpectedPerson) throws Exception {

        Connection connection = connectionManager.getConnection();
        Person actualPerson = personServ.create(connection, person);
        Assert.assertEquals(actualPerson.toString(), ExpectedPerson.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] createPositive() throws Exception {
       return dataManager.getCreatePositiveData();
    }

    @Test(dataProvider = "createNegative", priority = 2,enabled = true)
    public void testCreate_Negative(Person person, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            personServ.create(connection, person);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] createNegative() throws Exception {
        return dataManager.getCreateNegativeData();
    }

    @Test (dataProvider = "updatePositive", priority = 3,enabled = true)
    public void personUpdate_Positive(Person person, Person expectedPerson) throws Exception {

        Connection connection = connectionManager.getConnection();
        Person actualPerson = personServ.update(connection, expectedPerson);
        Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] updatePositive() throws Exception {

        Address address = new Address(1, "updated street", "updated city", 900020);
        Person person = new Person(1,
                                   "updated abhi",
                                   "updated shek",
                                   "updatedemail@email.com",
                                   "05-05-1996",
                                   address);

        Person expectedPerson = new Person(1,
                                           "updated abhi",
                                           "updated shek",
                                           "updatedemail@email.com",
                                           "05-05-1996",
                                           address);

       return new Object[][] {
           { person, expectedPerson}
       };
    }

    @Test (dataProvider = "updateNegative", priority = 4, enabled = true)
    public void personUpdate_Negative(Person person, int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            personServ.update(connection, person);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] updateNegative() throws Exception {
        return dataManager.getUpdateNegativeData();
    }

    @Test (dataProvider = "readPositive", priority = 5, enabled = true)
    public void personRead_Positive(long personId,
                                    Boolean includeAddress,
                                    Person expectedPerson) throws Exception {

        Connection connection = connectionManager.getConnection();
        Person actualPerson = personServ.read(connection, personId, includeAddress);
        Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] readPositive() throws Exception {

        long personId = 1;
        Boolean includeAddress = true;
        Boolean includeAddress2 = false;
        Address expectedAddress = new Address(1, "updated street", "updated city", 900020);
        String birthDate = "1996-05-05";
        Person expectedPerson = new Person(1,
                                           "updated abhi",
                                           "updated shek",
                                           "updatedemail@email.com",
                                           birthDate,
                                           expectedAddress);

        Person expectedPerson2 = new Person(1,
                                            "updated abhi",
                                            "updated shek",
                                            "updatedemail@email.com",
                                            birthDate);


        return new Object[][] {

            { personId, includeAddress, expectedPerson},
            { personId, includeAddress2, expectedPerson2}

        };
    }

    @Test (dataProvider = "readNegative", priority = 6,enabled = true)
    public void personRead_Negative(long personId,
                                    Boolean includeAddress,
                                    int expectedException) throws Exception {

        Connection connection = connectionManager.getConnection();
        try {
            personServ.read(connection, personId, includeAddress);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(),expectedException);
            connectionManager.manageTransaction(connection, flag);
        }
    }

    @DataProvider
    public Object[][] readNegative() throws Exception {

        long personId = 0;
        boolean includeAddressPos = true;
        boolean includeAddressNeg = false;
        int idException = 101;

        return new Object[][] {

            { personId, includeAddressPos, idException},
            { personId, includeAddressNeg, idException}

        };
    }

    @Test(dataProvider = "readAllPerson", priority = 7,enabled = true)
    public void personReadAll_Positive(List<Person> expectedPersonList) throws Exception {

        Connection connection = connectionManager.getConnection();
        List<Person> actualPersonList = personServ.readAll(connection);
        Assert.assertEquals(actualPersonList.toString(), expectedPersonList.toString());
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] readAllPerson() throws Exception {
        return dataManager.readAllData();
    }

    @Test(dataProvider = "deletePositive", priority = 8,enabled = true)
    public void personDelete_Positive(long personId, long addressId) throws Exception{

        Connection connection = connectionManager.getConnection();
        personServ.delete(connection, personId, addressId);
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] deletePositive() throws Exception {

        long personId = 1;
        long addressId = 1;

        return new Object[][] {
            { personId, addressId, },
        };

    }

    @Test(dataProvider = "deleteNegative", priority = 9 ,enabled = true)
    public void personDelete_Negative(long personId,
                                      long addressId,
                                      int expectedException) throws Exception{

        Connection connection = connectionManager.getConnection();
        try {
            personServ.delete(connection, personId, addressId);
        } catch (AppException e) {
            Assert.assertEquals(e.getErrorCode(), expectedException);
            flag = false;
        }
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider
    public Object[][] deleteNegative() throws Exception {

        long personId = 101;
        long addressId = 101;
        long personId2 = 1;
        long addressId2 = 1;
        int idException = 101;

        return new Object[][] {

            { personId, addressId, idException},
            { personId2, addressId2, idException}

        };
    }
}


