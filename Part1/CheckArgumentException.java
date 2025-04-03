class CheckArgumentException extends Exception {
    CheckArgumentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}