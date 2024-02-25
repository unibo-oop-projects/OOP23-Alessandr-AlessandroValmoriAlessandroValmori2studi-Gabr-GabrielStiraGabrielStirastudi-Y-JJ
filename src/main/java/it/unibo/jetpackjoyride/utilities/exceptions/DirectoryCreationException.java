package it.unibo.jetpackjoyride.utilities.exceptions;

/**
 * Exception throw when a directory couldn't be created
 */
public class DirectoryCreationException extends Exception{
    public DirectoryCreationException(String message){
        super(message);
    }
}
