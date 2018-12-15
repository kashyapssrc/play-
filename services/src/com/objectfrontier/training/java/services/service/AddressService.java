package com.objectfrontier.training.java.services.service;

import static com.objectfrontier.training.java.services.service.Query.createAddress;
import static com.objectfrontier.training.java.services.service.Query.deleteAddress;
import static com.objectfrontier.training.java.services.service.Query.readAddress;
import static com.objectfrontier.training.java.services.service.Query.readAllAddress;
import static com.objectfrontier.training.java.services.service.Query.updateAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.objectfrontier.training.java.services.exception.AppException;
import com.objectfrontier.training.java.services.exception.ExceptionCodes;
import com.objectfrontier.training.java.services.pojo.Address;

public class AddressService {

    private boolean isBlank(String param) {
        if (Objects.nonNull(param)) {
            return "".equals(param.trim());
        }
        return true;
    }

    private void isValid(Address address) {

             if(isBlank(address.getStreet()))  { throw new AppException(ExceptionCodes.STREET_NULL); }
        else if(isBlank(address.getCity()))    { throw new AppException(ExceptionCodes.CITY_NULL); }
        else if(address.getPostal_code() == 0) { throw new AppException(ExceptionCodes.POSTAL_CODE_NULL); }
    }

    private Address constructAddress(ResultSet readRes) throws SQLException {

        Address address = new Address();
        address.setId(readRes.getLong("id"));
        address.setStreet(readRes.getString("street"));
        address.setCity(readRes.getString("city"));
        address.setPostal_code(readRes.getInt("postal_code"));

        return address;
    }

    public Address create(Connection connection,Address address) throws Exception {

        isValid(address);
        PreparedStatement create = connection.prepareStatement(createAddress,
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

        isValid(address);
        PreparedStatement update = connection.prepareStatement(updateAddress);
        update.setString(1, address.getStreet());
        update.setString(2, address.getCity());
        update.setInt(3, address.getPostal_code());
        update.setLong(4, address.getId());

        int affectedRows = update.executeUpdate();
        if (affectedRows == 0) {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        }
        return address;
    }

    public Address read(Connection connection,long addressId) throws Exception {

        PreparedStatement read = connection.prepareStatement(readAddress);
        read.setLong(1, addressId);

        ResultSet readRes = read.executeQuery();
        if(readRes.next()) {

            Address address = constructAddress(readRes);
            return address;
        } else {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        }
    }

    public List<Address> readAll(Connection connection) throws Exception {

        ResultSet readAll = connection.createStatement()
                                      .executeQuery(readAllAddress);

        List<Address> addressList = new ArrayList<>();
        while(readAll.next()) {

            Address address = constructAddress(readAll);
            addressList.add(address);
        }
        return addressList;
    }

    public void delete(Connection connection, long addressId) throws Exception {

        PreparedStatement delete = connection.prepareStatement(deleteAddress);
        delete.setLong(1, addressId);

        int rowsAffected = delete.executeUpdate();
        if(rowsAffected == 0) {
            throw new AppException(ExceptionCodes.ID_NOT_FOUND);
        }
    }

    public List<Address> search(Connection connection,
                                String[] fieldName,
                                String searchText) throws SQLException {

        StringBuilder searchQuery = new StringBuilder()
                .append("SELECT id           ")
                .append("      , street      ")
                .append("      , city        ")
                .append("      , postal_code ")
                .append("  FROM address      ")
                .append(" WHERE              ");

        for (int i = 0; i <= fieldName.length - 1; i++) {

            String db_column = null;
            switch(fieldName[i]) {

            case "street" :
                db_column = "street";
                break;

            case "city" :
                db_column = "city";
                break;

            case "postalCode" :
                db_column = "postal_code";
                break;

            default :
                throw new AppException(ExceptionCodes.FIELD_NULL);
            }

            if(i == 0) {

                searchQuery.append(db_column   );
                searchQuery.append(" LIKE ?   ");
            } else {

                searchQuery.append("OR " + db_column   );
                searchQuery.append(" LIKE ?           ");
            }
        }

        if(searchText == null) { throw new AppException(ExceptionCodes.SEARCH_TEXT_EMPTY); }

        PreparedStatement search = connection.prepareStatement(searchQuery.toString());
        for(int i = 1; i <= fieldName.length ; i++ ) {
            search.setString(i, searchText + "%" );
        }

        List<Address> addressList = new ArrayList<>();
        ResultSet searchResult = search.executeQuery();
        while(searchResult.next()) {

            Address address = constructAddress(searchResult);
            addressList.add(address);
        }
        return addressList;
    }
}
