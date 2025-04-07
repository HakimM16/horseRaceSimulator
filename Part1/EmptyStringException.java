class EmptyStringException extends Exception {
    EmptyStringException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}