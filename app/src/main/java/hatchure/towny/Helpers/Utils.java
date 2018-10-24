package hatchure.towny.Helpers;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

import hatchure.towny.Home;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Utils {
    public static final String MyPREFERENCES = "TownyPreferences";
    public static final String PhoneNumber = "PhoneNumber";

    public static boolean IsNetworkAvailable(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null && !activeNetwork.isConnectedOrConnecting()) {
            Toast.makeText(context, "Something went wrong. Please check your internet connection.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static void ResetAppPreferences(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Utils.PhoneNumber, "");
        editor.commit();
    }

    public static SharedPreferences.Editor GetSharedPreferencesEditor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.edit();
    }

    public static ProgressDialog GetProcessDialog(Context context) {
        final ProgressDialog p = new ProgressDialog(context);
        p.setMessage("Please wait...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        return p;
    }

    public static LocationManager GetLocationManager(Context context) {
        return (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    public static String GetLocationProvider(LocationManager locationManager) {

        Criteria criteria = new Criteria();
        return locationManager.getBestProvider(criteria, false);
    }

    public static Location GetLastKnownLocation(Context context, LocationManager locationManager, String provider) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        return locationManager.getLastKnownLocation(provider);

//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//        }
//
//        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers = mLocationManager.getProviders(true);
//        if(providers.size()==0)
//            providers.add(LocationManager.GPS_PROVIDER);
//        Location bestLocation = null;
//        for (String provider : providers) {
//            mLocationManager.requestLocationUpdates(provider, 0, 0, (LocationListener) context);
//            Location l = mLocationManager.getLastKnownLocation(provider);
//
//            if (l == null) {
//                continue;
//            }
//            if (bestLocation == null
//                    || l.getAccuracy() < bestLocation.getAccuracy()) {
//                bestLocation = l;
//            }
//        }
//
//        return bestLocation;
    }

    public static void RequestLocationUpdates(Context context,LocationListener listener, LocationManager mLocationManager, String locationProvider) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        mLocationManager.requestLocationUpdates(locationProvider, 400, 1, listener);
    }
}
