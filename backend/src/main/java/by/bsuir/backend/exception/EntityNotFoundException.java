package by.bsuir.backend.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String name, Integer id) {
        super("Error occurred while search entity '" + name + "' with ID '" + id + "'");
    }

    public EntityNotFoundException(String entityName, String name) {
        super("Error occurred while search entity '" + entityName + "' with name '" + name + "'");
    }

    public EntityNotFoundException(String name, Integer id, Throwable cause) {
        super("Error occurred while search entity '" + name + "' with ID '" + id + "': " + cause);
    }
}
