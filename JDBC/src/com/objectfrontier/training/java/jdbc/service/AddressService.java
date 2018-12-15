package com.objectfrontier.training.java.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressService {

    public Address create(Connection connection,Address address) throws Exception {

        StringBuilder createSql = new StringBuilder()
                .append("INSERT INTO address(street,city,postal_code) ")
                .append("VALUES (?,?,?)                               ");

        PreparedStatement create = connection.prepareStatement(createSql.toString(),
                                                               Statement.RETURN_GENERATED_KEYS);
                          create.setString(1, address.getStreet());
                          create.setString(2, address.getCity());
                          create.setInt(3, address.getPostal_code());
                          create.execute();

        ResultSet createRes = create.getGeneratedKeys();
                  createRes.next();

        address.setId(createRes.getLong(1));
        return address;
    }

    public Address update(Connection connection,Address address) throws Exception {

        StringBuilder updateSql = new StringBuilder()
                .append("UPDATE address                             ")
                .append("SET street = ?, city = ? , postal_code = ? ")
                .append("WHERE id = ?                               ");

        PreparedStatement update = connection.prepareStatement(updateSql.toString());
                          update.setString(1, address.getStreet());
                          update.setString(2, address.getCity());
                          update.setInt(3, address.getPostal_code());
                          update.setLong(4, address.getId());

        int affectedRows = update.executeUpdate();

        if(affectedRows == 0) {
            throw new Exception("nothing to be updated");
        }
        return address;
    }

    public Address read(Connection connection,long addressId) throws Exception {

        StringBuilder readSql = new StringBuilder()
                .append("SELECT id, street, city, postal_code  ")
                .append("FROM address                          ")
                .append("WHERE id = ?                          ");

        PreparedStatement read = connection.prepareStatement(readSql.toString());
                          read.setLong(1, addressId);

        ResultSet readRes = read.executeQuery();
                  readRes.next();

        Address address = constructAddress(readRes);
        return address;
    }

    private Address constructAddress(ResultSet readRes) throws SQLException {

        Address address = new Address();
                address.setId(readRes.getLong("id"));
                address.setStreet(readRes.getString("street"));
                address.setCity(readRes.getString("city"));
                address.setPostal_code(readRes.getInt("postal_code"));

        return address;
    }

    public List<Address> readAll(Connection connection) throws Exception {

        StringBuilder readAllSql = new StringBuilder()
                .append("SELECT id, street, city, postal_code ")
                .append("FROM address                         ");

        Statement readAll = connection.createStatement();
        ResultSet readAllResult = readAll.executeQuery(readAllSql.toString());
        List<Address> addressList = new ArrayList<>();

        while(readAllResult.next()) {

            Address address = constructAddress(readAllResult);
            addressList.add(address);
            readAllResult.next();
        }
        return addressList;
    }

    public void delete(Connection connection, long addressId) throws Exception {

        StringBuilder delSql = new StringBuilder()
                .append("DELETE       ")
                .append("FROM address ")
                .append("WHERE id = ? ");

        PreparedStatement delete = connection.prepareStatement(delSql.toString());
                          delete.setLong(1, addressId);

        int rowsAffected = delete.executeUpdate();
        if(rowsAffected == 0) {
            throw new Exception("nothing to be deleted");
        }
    }
}
