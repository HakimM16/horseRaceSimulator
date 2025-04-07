class LengthOfRaceException extends Exception {
    LengthOfRaceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}