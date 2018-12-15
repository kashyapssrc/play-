package com.objectfrontier.training.java.jdbc.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.jdbc.service.Address;
import com.objectfrontier.training.java.jdbc.service.ConnectionManager;
import com.objectfrontier.training.java.jdbc.service.Person;
import com.objectfrontier.training.java.jdbc.service.PersonService;

public class PersonServiceTest {

    private ConnectionManager connectionManager;
    private PersonService personServ;

    @BeforeClass
    private void init() {

        connectionManager = new ConnectionManager();
        personServ = new PersonService();
    }

    @Test(dataProvider = "createPositive", priority =1)
    public void personCreate_Positive(Connection connection,
                                      Person person,
                                      Person expectedPerson) throws Exception {

        try {

            connection.setAutoCommit(false);
            Person actualPerson = personServ.create(connection, person);
            Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("person was not created");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "createPositive")
    public Object[][] createPositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("persons street");
                address.setCity("persons city");
                address.setPostal_code(600020);

        Address expectedAddress = new Address();
                expectedAddress.setId(1);
                expectedAddress.setStreet("persons street");
                expectedAddress.setCity("persons city");
                expectedAddress.setPostal_code(600020);

        Date birthDate = Date.valueOf("1996-04-09");
        java.util.Date current = new java.util.Date();
        Timestamp createdDate = new Timestamp (current.getTime());
        Person person = new Person();
               person.setName("abhishek");
               person.setEmail("abhishek@something1.com");
               person.setBirth_date(birthDate);
               person.setCreated_date(createdDate);
               person.setAddress(address);

        Person expectedPerson = new Person();
               expectedPerson.setId(1);
               expectedPerson.setName("abhishek");
               expectedPerson.setEmail("abhishek@something1.com");
               expectedPerson.setBirth_date(birthDate);
               expectedPerson.setCreated_date(createdDate);
               expectedPerson.setAddress(expectedAddress);

        return new Object[][] {
            {connection, person, expectedPerson}
        };
    }

    @Test(dataProvider = "createNegative", priority = 2)
    public void personCreate_Negative(Connection connection,
                                      Person person,
                                      String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            personServ.create(connection, person);
            connection.commit();
        } catch (Exception e) {

            Assert.assertEquals(e.getClass().getSimpleName(), expectedException);
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "createNegative")
    public Object[][] createNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("persons street2");
                address.setCity("persons city2");
                address.setPostal_code(600020);

        Date birthDate = Date.valueOf("1996-05-09");
        java.util.Date current = new java.util.Date();
        Timestamp createdDate = new Timestamp (current.getTime());
        Person person = new Person();
               person.setName("abhishek2");
               person.setEmail("abhishek@something1.com");
               person.setBirth_date(birthDate);
               person.setCreated_date(createdDate);
               person.setAddress(address);

        String expectedException = "MySQLIntegrityConstraintViolationException";

        return new Object[][] {
            {connection, person, expectedException}
        };
    }

    @Test (dataProvider = "updatePositive", priority = 3)
    public void personUpdate_Positive(Connection connection,
                                     Person person,
                                     Person expectedPerson) throws Exception {

        try {

            connection.setAutoCommit(false);
            Person actualPerson = personServ.update(connection, expectedPerson);
            Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("person was not updated");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "updatePositive")
    public Object[][] updatePositve() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("updated person street");
                address.setCity("updated pcity");
                address.setPostal_code(600021);
                address.setId(1);

        Date birthDate = Date.valueOf("1996-05-05");
        Person person = new Person();
               person.setName("updated abhishek");
               person.setEmail("updatedemail@email.com");
               person.setBirth_date(birthDate);
               person.setId(1);
               person.setAddress(address);

        Person expectedPerson = new Person();
               expectedPerson.setName("updated abhishek");
               expectedPerson.setEmail("updatedemail@email.com");
               expectedPerson.setBirth_date(birthDate);
               expectedPerson.setId(1);
               expectedPerson.setAddress(address);

       return new Object[][] {
           {connection, person, expectedPerson}
       };
    }

    @Test (dataProvider = "updateNegative", priority = 4)
    public void personUpdate_Negative(Connection connection,
                                      Person person,
                                      String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            personServ.create(connection, person);
            connection.commit();
        } catch (Exception e) {

            Assert.assertEquals(e.getClass()
                                 .getSimpleName(), expectedException);
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "updateNegative")
    public Object[][] updateNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        Address address = new Address();
                address.setStreet("updated person street");
                address.setCity("updated pcity");
                address.setPostal_code(600021);
                address.setId(1);

        Person person = new Person();
               person.setName("updated abhishek");
               person.setEmail("updatedemail@email.com");
               person.setId(1);
               person.setAddress(address);

        String expectedException = "MySQLIntegrityConstraintViolationException";

       return new Object[][] {
           {connection, person, expectedException}
       };
    }

    @Test (dataProvider = "readPositive", priority = 5)
    public void personRead_Positive(Connection connection,
                                    long personId,
                                    Boolean includeAddress,
                                    Person expectedPerson) throws Exception {

        try {

            connection.setAutoCommit(false);
            Person actualPerson = personServ.read(connection, personId, includeAddress);
            Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("person can't be read");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readPositive")
    public Object[][] readPositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        long personId = 1;
        Boolean includeAddress = true;
        Date birthDate = Date.valueOf("1996-05-05");

        Address expectedAddress = new Address();
                expectedAddress.setId(1);
                expectedAddress.setStreet("updated person street");
                expectedAddress.setCity("updated pcity");
                expectedAddress.setPostal_code(600021);

        Person expectedPerson = new Person();
               expectedPerson.setName("updated abhishek");
               expectedPerson.setEmail("updatedemail@email.com");
               expectedPerson.setBirth_date(birthDate);
               expectedPerson.setAddress(expectedAddress);
               expectedPerson.setId(1);

        return new Object[][] {
            {connection, personId, includeAddress, expectedPerson}
        };
    }

    @Test (dataProvider = "readNegative", priority = 6)
    public void personRead_Negative(Connection connection,
                                    long personId,
                                    Boolean includeAddress,
                                    String expectedException) throws Exception {

        try {

            connection.setAutoCommit(false);
            personServ.read(connection, personId, includeAddress);
            connection.commit();
        } catch (Exception e) {

            Assert.assertEquals(e.getClass()
                                 .getName(), expectedException);
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readNegative")
    public Object[][] readNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        long personId = 0;
        Boolean includeAddress = true;
        String expectedException = "java.sql.SQLException";

        return new Object[][] {
            {connection, personId, includeAddress, expectedException}
        };
    }

    @Test(dataProvider = "readAllPerson", priority = 7)
    public void personReadAll_Positive(Connection connection,
                                       List<Person> expectedPersonList) throws Exception {

        try {

            connection.setAutoCommit(false);
            List<Person> actualPersonList = personServ.readAll(connection);
            Assert.assertEquals(actualPersonList.toString(), expectedPersonList.toString());
            connection.commit();
        } catch (Exception e) {
            Assert.fail("person was not read");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "readAllPerson")
    public Object[][] readAllPerson() throws Exception {

        Connection connection = connectionManager.getConnection();
        Date birthDate = Date.valueOf("1996-05-05");
        Address expectedAddress = new Address();
                expectedAddress.setId(1);
                expectedAddress.setStreet("updated person street");
                expectedAddress.setCity("updated pcity");
                expectedAddress.setPostal_code(600021);

        Person expectedPerson = new Person();
               expectedPerson.setName("updated abhishek");
               expectedPerson.setEmail("updatedemail@email.com");
               expectedPerson.setBirth_date(birthDate);
               expectedPerson.setAddress(expectedAddress);
               expectedPerson.setId(1);

               List<Person> expectedPersonList = new ArrayList<>();
                            expectedPersonList.add(expectedPerson);

        return new Object[][] {
            {connection, expectedPersonList}
        };
    }

    @Test(dataProvider = "deletePositive", priority = 8)
    public void personDelete_Positive(Connection connection,
                                      long personId,
                                      long addressId) throws Exception{

        try {

            connection.setAutoCommit(false);
            personServ.delete(connection, personId, addressId);
            connection.commit();
        } catch (Exception e) {
            Assert.fail("person was not deleted");
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "deletePositive")
    public Object[][] deletePositive() throws Exception {

        Connection connection = connectionManager.getConnection();
        long personId = 1;
        long addressId = 1;

        return new Object[][] {
            {connection, personId, addressId}
        };
    }

    @Test(dataProvider = "deleteNegative", priority = 9)
    public void personDelete_Negative(Connection connection,
                                      long personId,
                                      long addressId,
                                      String expectedException) throws Exception{

        try {

            connection.setAutoCommit(false);
            personServ.delete(connection, personId, addressId);
            connection.commit();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), expectedException);
        } finally {
            connection.close();
        }
    }

    @DataProvider(name = "deleteNegative")
    public Object[][] deleteNegative() throws Exception {

        Connection connection = connectionManager.getConnection();
        long personId = 1;
        long addressId = 1;
        String expectedException = "nothing was deleted";

        return new Object[][] {
            {connection, personId, addressId, expectedException}
        };
    }
}




