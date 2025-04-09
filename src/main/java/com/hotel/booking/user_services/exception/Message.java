package com.hotel.booking.user_services.exception;

public final class Message {


    public static final String SUCCESS_CREATE = "%s created successfully";
    public static final String SUCCESS_GET = "%s fetched successfully";
    public static final String SUCCESS_UPDATE = "%s updated successfully";
    public static final String SUCCESS_DELETE = "%s deleted successfully";
    public static final String SUCCESS_ACCEPT = "%s accepted successfully";
    public static final String SUCCESS_VALIDATE = "%s validated successfully";
    public static final String FAILED_VALIDATE = "%s is invalid";
    public static final String INVALID_REQUEST = "Invalid Request: %s";
    public static final String OPERATION_FAILURE = "Operation failed: %s";
    public static final String NOT_FOUND = "%s not found";
    public static final String NULL_ARGUMENT = "%s is empty.";
    public static final String ALREADY_EXISTS = "%s already exists";
    public static final String MEDIATION_POLICY_ERROR = "Mediation policy name must not contain special characters.";
    public static final String PHONE_NOT_VALID = "Phone number must start with a valid country code(eg: +234), followed by a space, and then exactly 10 digits.";
    public static final String EMAIL_NOT_VALID = "Email must be a valid email address.";
    public static final String PASSWORD_ERROR = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and must be at least 8 characters long.";
    public static final String NAME_ERROR = "Name must not contain special characters.";
    public static final String TOKEN_OBTAINED_SUCCESSFULLY = "Access token obtained successfully";
    public static final String DECRYPTED_SUCCESSFULLY = "Client ID and secret key decrypted successfully";
    public static final String REGISTERED_SUCCESSFULLY = "Client registered successfully";
    public static final String VERSION = "Version must be 1 to 30 characters and not contain special characters";
    public static final String AUTH_HEADER_ERROR = "Auth header name must not contain special characters.";
    public static final String API_KEY_HEADER_ERROR = "Api key header name must not contain special characters.";

    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String INVALID_TOKEN = "Invalid Token";
    public static final String INVALID_USER = "Invalid User";
    public static final String INVALID_PASSWORD = "XXXXXXX Password";
    public static final String INVALID_EMAIL = "Invalid Email";
    public static final String INVALID_PHONE = "Invalid Phone";
    public static final String INVALID_NAME = "Invalid Name";
    public static final String INVALID_ID = "Invalid ID";

    public static final String NOT_CANCELLED = "%s not cancelled";
    public static final String SUCCESS_CANCELLED = "%s cancelled successfully";

    private Message() {
        throw new AssertionError("Cannot instantiate Message");
    }
}
   