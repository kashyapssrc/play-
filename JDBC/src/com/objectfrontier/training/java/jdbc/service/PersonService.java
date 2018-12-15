package com.objectfrontier.training.java.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    public Person create(Connection connection, Person person) throws Exception {

        AddressService addressServ = new AddressService();
        Address personAdd = addressServ.create(connection, person.getAddress());

        StringBuilder createSql = new StringBuilder()
                .append("INSERT person (name, email, address_id, birth_date, created_date) ")
                .append("VALUES (?,?,?,?,?)                                                ");

        PreparedStatement create = connection.prepareStatement(createSql.toString(),
                                                               Statement.RETURN_GENERATED_KEYS);
                          create.setString(1, person.getName());
                          create.setString(2, person.getEmail());
                          create.setLong(3, personAdd.getId());
                          create.setDate(4, person.getBirth_date());
                          create.setTimestamp(5, person.getCreated_date());
                          create.execute();

        ResultSet createRes = create.getGeneratedKeys();
                  createRes.next();

        person.setId(createRes.getLong(1));
        person.setAddress(personAdd);

        return person;
    }

    public Person update(Connection connection, Person person) throws Exception {

        AddressService addressServ = new AddressService();
        Address address = addressServ.update(connection, person.getAddress());

        StringBuilder updateSql = new StringBuilder()
                .append("UPDATE person                              ")
                .append("   SET name = ?, email = ?, birth_date = ? ")
                .append(" WHERE id = ?                              ");

        PreparedStatement update = connection.prepareStatement(updateSql.toString());
                          update.setString(1, person.getName());
                          update.setString(2, person.getEmail());
                          update.setDate(3, person.getBirth_date());
                          update.setLong(4, person.getId());
                          update.executeUpdate();

        person.setAddress(address);
        return person;
    }

    public Person read(Connection connection, long personId, boolean includeAddress) throws Exception {

        StringBuilder readSql = new StringBuilder()
                .append("SELECT id, name, email, address_id, birth_date, created_date  ")
                .append("  FROM person                                                 ")
                .append(" WHERE id = ?                                                 ");

        PreparedStatement read = connection.prepareStatement(readSql.toString());
                          read.setLong(1, personId);

        ResultSet readResult = read.executeQuery();
                  readResult.next();

        Person person = constructPerson(readResult);
        if(includeAddress == true) {

            Address readAddr = constructAddress(connection, readResult);
            person.setAddress(readAddr);
            return person;
        } else {
            return person;
        }
    }

    private Person constructPerson(ResultSet readResult) throws SQLException {

        Person person = new Person();
               person.setId(readResult.getLong("id"));
               person.setName(readResult.getString("name"));
               person.setEmail(readResult.getString("email"));
               person.setBirth_date(readResult.getDate("birth_date"));
               person.setCreated_date(readResult.getTimestamp("created_date"));

        return person;
    }

    public List<Person> readAll(Connection connection) throws Exception {

        StringBuilder readAllSql = new StringBuilder()
                .append("SELECT id, name, address_id, email, birth_date, created_date ")
                .append("  FROM person                                                ");

        Statement readAll = connection.createStatement();
        ResultSet readAllRes = readAll.executeQuery(readAllSql.toString());
        List<Person> personList = new ArrayList<>();

        while(readAllRes.next()) {

            Address readAddr = constructAddress(connection, readAllRes);
            Person person = constructPerson(readAllRes);
                   person.setAddress(readAddr);
            personList.add(person);
        }
        return personList;
    }

    private Address constructAddress(Connection connection,
                                     ResultSet readAllRes) throws Exception {

        AddressService addressServ = new AddressService();
        long addressId = readAllRes.getLong("address_id");
        Address readAddr = addressServ.read(connection, addressId);

        return readAddr;
    }

    public void delete(Connection connection, long personId, long addressId) throws Exception {

        StringBuilder deleteSql = new StringBuilder()
                .append("DELETE         ")
                .append("  FROM person  ")
                .append(" WHERE id = ?  ");

        PreparedStatement delete = connection.prepareStatement(deleteSql.toString());
                          delete.setLong(1, personId);
        int rowsAffected = delete.executeUpdate();

        if(rowsAffected == 0) {
            throw new Exception("nothing was deleted");
        } else {

        AddressService addressServ = new AddressService();
        addressServ.delete(connection, addressId);
        }
    }
}
