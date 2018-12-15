package com.objectfrontier.training.java.services.exception;

public enum ExceptionCodes {

    ID_NOT_FOUND(101, "id is null"),
    INVALID_ENTRY(102,"entry is invalid"),
    FIRST_NAME_NULL(200, "first name is null"),
    LAST_NAME_NULL(201, "last name is null"),
    DOB_NULL(202,"birth date is null"),
    MAIL_NULL(203,"email is null"),
    CITY_NULL(300, "city is null"),
    STREET_NULL(301,"Street is null"),
    POSTAL_CODE_NULL(302,"postal code is null"),
    DUPLICATE_EMAIL(500,"email is duplicated"),
    DUPLICATE_NAME(501,"name is duplicated"),
    AUTO_INC_FAIL(400, "does not auto increment"),
    CONNECTION_EXCEPTION(404,"connection exception"),
    FIELD_NULL(405, "no field specified"),
    SEARCH_TEXT_EMPTY(406, "no search value specified"),
    NO_RECORD_FOUND(407, "no results to be shown");

    private final int id;
    private final String errorMessage;

    ExceptionCodes(int id, String errorMessage) {
        this.id = id;
        this.errorMessage = errorMessage;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.errorMessage;
    }
}


