package it.unibo.jetpackjoyride.utilities.exceptions;

/**
 * Exception thrown when data does not conform to the expected format.
 * This is used when reading files for example in
 */
public class InvalidDataFormatException extends Exception{
    public InvalidDataFormatException(String message) {
        super(message);
    }
}
