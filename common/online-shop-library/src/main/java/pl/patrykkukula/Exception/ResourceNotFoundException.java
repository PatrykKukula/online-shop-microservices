package pl.patrykkukula.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String value) {
        super(resource + " not found for value: " + value);
    }
}
