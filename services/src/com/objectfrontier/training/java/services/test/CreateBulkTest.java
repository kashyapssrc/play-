package com.objectfrontier.training.java.services.test;

import static com.objectfrontier.training.java.services.service.Query.deleteAllAddress;
import static com.objectfrontier.training.java.services.service.Query.resetAddressId;
import static com.objectfrontier.training.java.services.service.Query.truncatePerson;

import java.sql.Connection;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.services.connection.ConnectionManager;
import com.objectfrontier.training.java.services.pojo.Person;
import com.objectfrontier.training.java.services.service.DataManager;
import com.objectfrontier.training.java.services.service.PersonService;

@Test
public class CreateBulkTest {

    private ConnectionManager connectionManager;
    private PersonService personServ;
    private boolean flag;
    private DataManager dataManager;

    public void truncateTable() throws Exception {

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

    @Test(dataProvider = "createPositive", priority = 1, enabled = true)
    public void testCreate_Positive(Person person, Person ExpectedPerson) throws Exception {

        Connection connection = connectionManager.getConnection();
        Person actualPerson = personServ.create(connection, person);
        Assert.assertEquals(actualPerson.toString(), ExpectedPerson.toString());
        flag = true;
        connectionManager.manageTransaction(connection, flag);
    }

    @DataProvider(parallel = true)
    public Object[][] createPositive() throws Exception {
        return dataManager.getCreatePositiveData();
    }
}
