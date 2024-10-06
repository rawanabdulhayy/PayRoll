package minipayroll;

abstract class CustomException extends Exception {

    int severity;

    public CustomException(String message, int severity) {
        super(message);
        this.severity = severity;
    }

}

class InvalidCredentialsException extends CustomException {

    public InvalidCredentialsException() {
        super("Invalid credentials", 3);
    }

}

class EmptyInputException extends CustomException {

    public EmptyInputException(String inputField) {
        super("Empty input: " + inputField, 2);
    }

}

class InvalidInputException extends CustomException {

    public InvalidInputException(String inputField, String message) {
        super("Invalid input: " + inputField + "\nMessage: " + message, 1);
    }

}
