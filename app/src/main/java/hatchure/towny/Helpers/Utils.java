package hatchure.towny.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Utils {
    public static final String MyPREFERENCES = "TownyPreferences" ;
    public static final String PhoneNumber = "PhoneNumber";

    public static boolean IsNetworkAvailable(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
