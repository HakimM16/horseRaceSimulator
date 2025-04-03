public class Test {
    public static void main(String[] args) {
        Race race = new Race(20);
        /*
        Planning:
        - For the try-catch block, allow the user to create three horses with different confidence levels
        - give the user to choose which horse to bet on 
        - Once race is done, display whether the chosen horse has won or not
        - Add more exceptions for invalid input (e.g. number instead of string, char instead of string) ✅
         */
        try {
            Horse horse1 = new Horse('\u2658', "Thunder", 0.6); // white horse
            Horse horse2 = new Horse('\u265A', "Lightning", 0.9); // white king
            Horse horse3 = new Horse('\u2656', "Wind", 0.5); // white rook

            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);
        } catch (rangeOfConfidenceException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (CheckArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        race.startRace();
    }
}