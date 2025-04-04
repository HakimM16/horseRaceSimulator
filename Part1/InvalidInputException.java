class InvalidInputException extends Exception {
    InvalidInputException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}