package hatchure.towny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static hatchure.towny.Helpers.Utils.IsNetworkAvailable;

public class OfferDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        if (!IsNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Somewthing went wrong. Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
        else {

        }
    }
}
