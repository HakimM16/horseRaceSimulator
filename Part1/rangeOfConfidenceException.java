class rangeOfConfidenceException extends Exception {
    rangeOfConfidenceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}