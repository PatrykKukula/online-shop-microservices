package pl.patrykkukula.Validation;

public class ValidationUtils {

    public static String validateSortDirection(String sortDirection){
        if (sortDirection.equalsIgnoreCase("ASC")) return "ASC";
        else if(sortDirection.equalsIgnoreCase("DESC")) return "DESC";
        return "ASC";
    }
}
