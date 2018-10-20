package hatchure.towny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        TextView applyFilter = findViewById(R.id.apply_filter);
        RecyclerView filtersList = findViewById(R.id.recyclerView);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToHome = new Intent(getApplicationContext(), Home.class);
                startActivity(moveToHome);
            }
        });
    }
}
