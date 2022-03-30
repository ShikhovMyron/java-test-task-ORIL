package test.task.exeption;

public class NonexistentCurrencyName extends Exception {

    public NonexistentCurrencyName(String message) {
        super(String.format("Unacceptable currency name '%s'", message));
    }
}
