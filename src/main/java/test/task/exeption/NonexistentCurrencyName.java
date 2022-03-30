package test.task.exeption;

public class UnacceptableCurrencyName extends Exception {

    public UnacceptableCurrencyName(String message) {
        super(String.format("Unacceptable currency name '%s'", message));
    }
}
