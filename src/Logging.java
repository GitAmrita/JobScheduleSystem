import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amritachowdhury on 8/6/17.
 */
public class Logging {
    public static void log(String message, String classPath, LogLevel level) {
        if (level.getValue() >= Config.DISPLAY_LOG_LEVEL) {
            String l = level.getName();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date) + " " + l + ": Class: " + classPath + " Message: " + message);
        }
    }
}
