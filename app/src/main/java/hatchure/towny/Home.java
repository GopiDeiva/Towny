package hatchure.towny;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hatchure.towny.Helpers.Adapters.CustomAdapter;
import hatchure.towny.Helpers.Utils;
import hatchure.towny.Interfaces.ApiInterface;
import hatchure.towny.Models.OTPResponse;
import hatchure.towny.Models.Offer;
import hatchure.towny.Models.Offers;
import hatchure.towny.WebHandler.WebRequesthandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hatchure.towny.Helpers.Utils.IsNetworkAvailable;
import static hatchure.towny.Helpers.Utils.MyPREFERENCES;

public class Home extends AppCompatActivity implements LocationListener {

    Offers offers = null;
    Location location = null;
    String radius = "20";
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        location = getLastKnownLocation();
        if (CheckPermissions()) {
            getOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
            ShowLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        location = getLastKnownLocation();
        getOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        location = getLastKnownLocation();
        getOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
        customAdapter.notifyDataSetChanged();
    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private boolean CheckPermissions()
    {
        if (!IsNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Something went wrong. Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
        else {
            if (location == null) {
                Toast.makeText(getApplicationContext(), "Something went wrong. Please check your location and internet", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder =  new AlertDialog.Builder(this);
                String message = "Do you want open GPS setting?";
                builder.setMessage(message)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                        d.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        d.cancel();
                                    }
                                });
                builder.create().show();
                location = getLastKnownLocation();
            } else {
                Toast.makeText(getApplicationContext(), location.getLatitude() + location.getLongitude() + "", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    public void getOffers(String latitude, String longitude, String radius) {
        final ProgressDialog p = new ProgressDialog(Home.this);
        p.setMessage("Please wait...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
        ApiInterface apiService =
                WebRequesthandler.getClient().create(ApiInterface.class);

        Call<Offers> call = apiService.GetOffers(latitude, longitude, radius);
        call.request();
        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, retrofit2.Response<Offers> response) {
                Log.d("ProductResult", response.body().toString());
                p.dismiss();
                customAdapter = new CustomAdapter(getApplicationContext(), offers.getOffers());
                recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                offers = response.body();
                Toast.makeText(getApplicationContext(),response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                p.dismiss();
                Toast.makeText(getApplicationContext(),"failiure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowLayout()
    {
        TextView logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Utils.PhoneNumber, "");
                editor.commit();
                Toast.makeText(getApplicationContext(), sharedpreferences.getString(Utils.PhoneNumber, ""), Toast.LENGTH_LONG).show();
                finish();
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        getOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
