package com.nnk.springboot.constants;

/**
 * Error message used with exception or validation exception.
 */
public final class ErrorMessage {
    /**
     * Error message for invalid entry with too much characters.
     */
    public static final String TOO_MUCH_CHARACTERS =
            "Entry must be less characters";
    /**
     * Error message for invalid number.
     */
    public static final String INVALID_NUMBER = "Type a valid number";
    /**
     * Error message when field is missing.
     */
    public static final String FIELD_IS_MANDATORY = "Field is mandatory";
    /**
     * Error message when username is not found.
     */
    public static final String USERNAME_NOT_FOUND = "Username was not found";

    /**
     * Private empty constructor.
     */
    private ErrorMessage() { }
}
