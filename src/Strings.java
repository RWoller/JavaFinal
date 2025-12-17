import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

// 5.3 Use of a resource bundle
public class Strings {
    private static final ResourceBundle bundle =
            ResourceBundle.getBundle("strings", Locale.getDefault(), new UTF8Control());
    public static String get(String key, Object... args) {
        return String.format(bundle.getString(key), args);
    }

    /**
     * Custom ResourceBundle.Control to force UTF-8 encoding
     */
    private static class UTF8Control extends ResourceBundle.Control {

        @Override
        public ResourceBundle newBundle(
                String baseName,
                Locale locale,
                String format,
                ClassLoader loader,
                boolean reload
        ) throws IOException {

            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream stream = loader.getResourceAsStream(resourceName)) {
                if (stream == null) {
                    return null;
                }
                return new PropertyResourceBundle(
                        new InputStreamReader(stream, StandardCharsets.UTF_8)
                );
            }
        }
    }
}
