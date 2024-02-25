package it.unibo.jetpackjoyride.utilities.exceptions;

/**
 * Exception thrown when data does not conform to the expected format.
 * This exception is thrown when encountering data with unexpected format.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public class InvalidDataFormatException extends Exception {
    /**
     * Defines the unique original class version.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constructor to create a new InvalidDataFormatException with the specified
     * detail message.
     *
     * @param message The message describing what and where something went wrong.
     */
    public InvalidDataFormatException(final String message) {
        super(message);
    }
}
