package com.objectfrontier.training.filter.exception;

public enum ExceptionCodes {

    ID_NOT_FOUND(101, "id is null"),
    INVALID_ENTRY(102,"entry is invalid"),
    FIRST_NAME_NULL(200, "first name is null"),
    LAST_NAME_NULL(201, "last name is null"),
    DOB_NULL(202,"birth date is null"),
    MAIL_NULL(203,"email is null"),
    PASSWORD_NULL(204,"email is null"),
    CITY_NULL(300, "city is null"),
    STREET_NULL(301,"Street is null"),
    POSTAL_CODE_NULL(302,"postal code is null"),
    AUTO_INC_FAIL(400, "does not auto increment"),
    FIELD_NULL(405, "no field specified"),
    SEARCH_TEXT_EMPTY(406, "no search value specified"),
    NO_RECORD_FOUND(407, "no results to be shown"),
    DUPLICATE_EMAIL(500,"email is duplicated"),
    DUPLICATE_NAME(501,"name is duplicated"),
    SERVER_ERROR(600,"connection cannot be establihed"),
    FILE_NOT_FOUND(601,"the specified file cannot be found"),
    FILE_ERROR(602, "the specified file cannot be read"),
    SESSION_INVALID(603, "the credentials are wrong"),
    UNAUTHENTICATED_OPERATION(604, "operations not allowed for the particular user");

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


