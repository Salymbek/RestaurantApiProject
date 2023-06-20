package peaksoft.exception;

public class NotVacanciesException extends RuntimeException{
    public NotVacanciesException() {
        super();
    }

    public NotVacanciesException(String message) {
        super(message);
    }
}
