package com.objectfrontier.training.java.services.service;

public interface Query {

    String checkAutoIncrement = new StringBuilder()
            .append("SELECT id     ")
            .append("  FROM person ")
            .toString();

    String duplicateEmailCheck = new StringBuilder()
            .append("SELECT email                           ")
            .append("  FROM person                          ")
            .append(" WHERE email = ?                       ")
            .toString();

    String duplicateNameCheck = new StringBuilder()
            .append("SELECT firstname                       ")
            .append("     , lastname                        ")
            .append("  FROM person                          ")
            .append(" WHERE firstname = ? AND lastname = ?  ")
            .toString();

    String truncatePerson = "TRUNCATE TABLE person ";

    String deleteAllAddress = new StringBuilder()
            .append(" DELETE FROM address ")
            .toString();

    String resetAddressId = new StringBuilder()
            .append("ALTER TABLE address ")
            .append("AUTO_INCREMENT = 1  ")
            .toString();

    String createAddress = new StringBuilder()
            .append("INSERT INTO address(street       ")
            .append("                  , city         ")
            .append("                  , postal_code) ")
            .append("VALUES (?,?,?)                   ")
            .toString();

    String updateAddress = new StringBuilder()
            .append("UPDATE address         ")
            .append("   SET street = ?      ")
            .append("     , city = ?        ")
            .append("     , postal_code = ? ")
            .append(" WHERE id = ?          ")
            .toString();

    String readAddress = new StringBuilder()
            .append("SELECT id           ")
            .append("     , street       ")
            .append("     , city         ")
            .append("     , postal_code  ")
            .append("  FROM address      ")
            .append(" WHERE id = ?       ")
            .toString();

    String readAllAddress = new StringBuilder()
            .append("SELECT  id         ")
            .append("      , street      ")
            .append("      , city        ")
            .append("      , postal_code ")
            .append("  FROM address      ")
            .toString();

    String deleteAddress = new StringBuilder()
            .append("DELETE         ")
            .append("  FROM address ")
            .append(" WHERE id = ?  ")
            .toString();

    String createPerson = new StringBuilder()
            .append("INSERT INTO person (firstname     ")
            .append("                  , lastname      ")
            .append("                  , email         ")
            .append("                  , address_id    ")
            .append("                  , birth_date    ")
            .append("                  , created_date) ")
            .append("VALUES (?,?,?,?,?,?)              ")
            .toString();

    String updatePerson = new StringBuilder()
            .append("UPDATE person         ")
            .append("   SET firstname = ?  ")
            .append("     , lastname = ?   ")
            .append("     , email = ?      ")
            .append("     , birth_date = ? ")
            .append(" WHERE id = ?         ")
            .toString();

    String readPerson = new StringBuilder()
            .append("SELECT id           ")
            .append("     , firstname    ")
            .append("     , lastname     ")
            .append("     , email        ")
            .append("     , address_id   ")
            .append("     , birth_date   ")
            .append("     , created_date ")
            .append("  FROM person       ")
            .append(" WHERE id = ?       ")
            .toString();

    String readAllPerson = new StringBuilder()
            .append(" SELECT id           ")
            .append("      , firstname    ")
            .append("      , lastname     ")
            .append("      , address_id   ")
            .append("      , email        ")
            .append("      , birth_date   ")
            .append("      , created_date ")
            .append("  FROM person        ")
            .toString();

    String deletePerson = new StringBuilder()
            .append("DELETE         ")
            .append("  FROM person  ")
            .append(" WHERE id = ?  ")
            .toString();
}
