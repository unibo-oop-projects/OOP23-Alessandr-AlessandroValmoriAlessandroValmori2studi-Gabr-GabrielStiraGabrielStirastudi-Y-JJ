package it.unibo.jetpackjoyride.utilities.exceptions;

/**
 * Exception thrown when there was an attempt to use a functionality that has
 * not been implemented.
 * This exception is thrown when trying to use an object or feature that does
 * not exist.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public class NotImplementedObjectException extends Exception {
    /**
     * Defines the unique original class version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to create a new NotImplementedObjectException with the specified
     * detail message.
     *
     * @param message The message describing what and where something went wrong.
     */
    public NotImplementedObjectException(final String message) {
        super(message);
    }
}
