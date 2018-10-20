package hatchure.towny;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hatchure.towny.Helpers.Adapters.CustomAdapter;
import hatchure.towny.Helpers.Utils;
import hatchure.towny.Interfaces.ApiInterface;
import hatchure.towny.Models.Offers;
import hatchure.towny.WebHandler.WebRequesthandler;
import retrofit2.Call;
import retrofit2.Callback;

import static hatchure.towny.Helpers.Utils.GetLastKnownLocation;
import static hatchure.towny.Helpers.Utils.GetProcessDialog;
import static hatchure.towny.Helpers.Utils.IsNetworkAvailable;

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        recyclerView = findViewById(R.id.recyclerView);
        location = GetLastKnownLocation(Home.this);
        if (CheckPermissions()) {
            //GetOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
            ShowLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        location = GetLastKnownLocation(Home.this);
        //        GetOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
//        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        location = GetLastKnownLocation(Home.this);
//        GetOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
//        customAdapter.notifyDataSetChanged();
    }

    private boolean CheckPermissions()
    {
        if (IsNetworkAvailable(this))
        {
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
                location = GetLastKnownLocation(Home.this);
            } else {
                Toast.makeText(getApplicationContext(), location.getLatitude() + location.getLongitude() + "", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    private void GetOffers(String latitude, String longitude, String radius) {
        final ProgressDialog p =GetProcessDialog(Home.this);
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
        final TextView logOut = findViewById(R.id.logout);
        final TextView filter = findViewById(R.id.filter);
        final TextView search = findViewById(R.id.search);
        final TextView appTitle = findViewById(R.id.app_title);
        final EditText searchBar = findViewById(R.id.searchBar);
        final TextView cancelSearch = findViewById(R.id.cancel);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //GetOffers(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), radius);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ResetAppPreferences(Home.this);
                Intent intent = new Intent(Home.this, Entry.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appTitle.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                logOut.setVisibility(View.GONE);
                searchBar.setVisibility(View.VISIBLE);
                cancelSearch.setVisibility(View.VISIBLE);
            }
        });

        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appTitle.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                filter.setVisibility(View.VISIBLE);
                logOut.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.GONE);
                cancelSearch.setVisibility(View.GONE);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(getApplicationContext(), FiltersActivity.class);
                startActivity(filterIntent);
            }
        });
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
