package cz.fit.ctu.rssreader.utils;

/**
 * Created by Jakub on 6. 3. 2015.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logger {
    private static String APP_NAME = "RSS Reader";
    private static Toast toast = null;


    /**
     * Debug void.
     *
     * @param message the message
     */
    public static void debug(String message) {
        Log.i(APP_NAME, message);
    }

    /**
     * Debug void.
     *
     * @param className the class name
     * @param message   the message
     */
    public static void debug(String className, String message) {
        Log.i(APP_NAME, "<" + className + ">: " + message);
    }

    /**
     * Debug void.
     *
     * @param e the e
     */
    public static void debug(Exception e) {
        Log.e("EXCEPTION", e.getMessage(), e);
    }

    /**
     * Make toast.
     *
     * @param context the context
     * @param message the message
     */
    public static void makeToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}