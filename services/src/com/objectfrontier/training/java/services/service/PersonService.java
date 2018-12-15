package com.objectfrontier.training.java.services.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.objectfrontier.training.java.services.exception.AppException;
import com.objectfrontier.training.java.services.exception.ExceptionCodes;
import com.objectfrontier.training.java.services.pojo.Address;
import com.objectfrontier.training.java.services.pojo.Person;

public class PersonService implements Query{

    private void checkAutoInc(Connection connection) throws SQLException {

        String checkQuery = checkAutoIncrement;
        Statement checkAutoInc = connection.createStatement();
        ResultSet checkRes = checkAutoInc.executeQuery(checkQuery);
        ResultSetMetaData checkResMd = checkRes.getMetaData();

        if (checkResMd.isAutoIncrement(1) == false ) {
            throw new AppException(ExceptionCodes.AUTO_INC_FAIL);
        }

    }

    private void isValidEmail (Connection connection,Person person) throws SQLException {

        String duplicateCheckQuery = duplicateEmailCheck;
        PreparedStatement duplicateCheck = connection.prepareStatement(duplicateCheckQuery);
        duplicateCheck.setString(1, person.getEmail());

        ResultSet readRes = duplicateCheck.executeQuery();
        if (readRes.next()) {
            throw new AppException(ExceptionCodes.DUPLICATE_EMAIL);
        }
    }

    private void isDuplicateName(Connection connection , Person person) throws Exception{

        String duplicateNameQuery = duplicateNameCheck;
        PreparedStatement duplicateCheck = connection.prepareStatement(duplicateNameQuery);
        duplicateCheck.setString(1, person.getFirstName());
        duplicateCheck.setString(2, person.getLastName());

        ResultSet readRes = duplicateCheck.executeQuery();
        if(readRes.next()) {
            throw new AppException(ExceptionCodes.DUPLICATE_NAME);
        }
    }

    private boolean isBlank(String param) {

        if (Objects.nonNull(param)) {
            return "".equals(param.trim());
        }
        return true;
    }

    private void isValid(Person person) {

             if(isBlank(person.getFirstName()))  { throw new AppException(ExceptionCodes.FIRST_NAME_NULL); }
        else if(isBlank(person.getLastName()))   { throw new AppException(ExceptionCodes.LAST_NAME_NULL); }
        else if(isBlank(person.getBirth_date())) {throw new AppException(ExceptionCodes.DOB_NULL); }
        else if(isBlank(person.getEmail()))      { throw new AppException (ExceptionCodes.MAIL_NULL); }

    }

    private long checkBirthDate(Person person) throws ParseException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false);
            java.util.Date date = dateFormat.parse(person.getBirth_date());
            long birthDate = date.toInstant().toEpochMilli();
            return birthDate;
        } catch (ParseException e) {
            throw new AppException(ExceptionCodes.INVALID_ENTRY);
        }
    }

    private Address getAddress(Connection connection, ResultSet readAllRes) throws Exception {

        AddressService addressServ = new AddressService();
        long addressId = readAllRes.getLong("address_id");
        Address readAddr = addressServ.read(connection, addressId);

        return readAddr;
    }

    private Person constructPerson(ResultSet readResult) throws SQLException {

        Person person = new Person();
        person.setId(readResult.getLong("id"));
        person.setFirstName(readResult.getString("firstname"));
        person.setLastName(readResult.getString("lastname"));
        person.setEmail(readResult.getString("email"));
        person.setBirth_date(readResult.getString("birth_date"));
        person.setCreated_date(readResult.getTimestamp("created_date"));

        return person;

    }

    public Person create(Connection connection, Person person) throws Exception {

        checkAutoInc(connection);
        isValid(person);
        isDuplicateName(connection, person);
        isValidEmail(connection, person);
        long birthDate = checkBirthDate(person);
        Timestamp createdDate = new Timestamp (System.currentTimeMillis());

        AddressService addrServ = new AddressService();
        Address personAdd = addrServ.create(connection, person.getAddress());

        String createQuery = createPerson;

        PreparedStatement create = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
        create.setString(1, person.getFirstName());
        create.setString(2, person.getLastName());
        create.setString(3, person.getEmail());
        create.setLong(4, personAdd.getId());
        create.setDate(5, new Date(birthDate));
        create.setTimestamp(6, createdDate);
        create.execute();

        ResultSet createRes = create.getGeneratedKeys();
        createRes.next();

        person.setId(createRes.getLong(1));
        person.setAddress(personAdd);
        return person;
    }

    public Person update(Connection connection, Person person) throws Exception {

        checkAutoInc(connection);
        isValid(person);
        isDuplicateName(connection, person);
        isValidEmail(connection, person);
        long birthDate = checkBirthDate(person);

        AddressService addressServ = new AddressService();
        Address address = addressServ.update(connection, person.getAddress());

        String updateQuery = updatePerson;
        PreparedStatement update = connection.prepareStatement(updateQuery);
        update.setString(1, person.getFirstName());
        update.setString(2, person.getLastName());
        update.setString(3, person.getEmail());
        update.setDate(4, new Date (birthDate));
        update.setLong(5, person.getId());

        int rowsAffected = update.executeUpdate();
        if (rowsAffected == 0) {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        }

        person.setAddress(address);
        return person;
    }

    public Person read(Connection connection, long personId, boolean includeAddress) throws Exception {

        String readQuery = readPerson;
        PreparedStatement read = connection.prepareStatement(readQuery);
        read.setLong(1, personId);

        ResultSet readResult = read.executeQuery();
        if(readResult.next() ) {

            Person person = constructPerson(readResult);
            if(includeAddress == true) {

                Address readAddr = getAddress(connection, readResult);
                person.setAddress(readAddr);
            }
            return person;
        } else {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        }
    }

    public List<Person> readAll(Connection connection) throws Exception {

        String readAllQuery = readAllPerson;
        Statement readAll = connection.createStatement();
        ResultSet readAllRes = readAll.executeQuery(readAllQuery);
        List<Person> personList = new ArrayList<>();

        while(readAllRes.next()) {

            Address readAddr = getAddress(connection, readAllRes);
            Person person = constructPerson(readAllRes);
            person.setAddress(readAddr);
            personList.add(person);
        }
        return personList;
    }

    public void delete(Connection connection, long personId, long addressId) throws Exception {

        String deleteQuery = deletePerson;
        PreparedStatement delete = connection.prepareStatement(deleteQuery);
        delete.setLong(1, personId);

        int rowsAffected = delete.executeUpdate();
        if(rowsAffected == 0) {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        } else {

            AddressService addrServ = new AddressService();
            addrServ.delete(connection, addressId);
        }
    }

}

