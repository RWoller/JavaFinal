import java.util.ResourceBundle;

public class Strings {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("strings");

    public static String get(String key, Object... args) {
        String template = bundle.getString(key);
        return String.format(template, args);
    }
}
