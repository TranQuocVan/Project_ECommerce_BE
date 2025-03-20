package service.user.account;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class EmailValidator {
    private static final String EMAIL_PATTERN =
            "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    // Compile the regex pattern
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        // If email is null, it is invalid
        if (email == null) {
            return false;
        }

        // Match the input email with the pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
