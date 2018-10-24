package hatchure.towny;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hatchure.towny.Helpers.Adapters.CustomAdapter;
import hatchure.towny.Interfaces.ApiInterface;
import hatchure.towny.Models.OfferSortItem;
import hatchure.towny.Models.OfferSortList;
import hatchure.towny.Models.Offers;
import hatchure.towny.WebHandler.WebRequesthandler;
import retrofit2.Call;
import retrofit2.Callback;

import static hatchure.towny.Helpers.Utils.GetProcessDialog;

public class FiltersActivity extends AppCompatActivity {
    List<OfferSortItem> sortList = null;
    ListView filtersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        TextView applyFilter = findViewById(R.id.apply_filter);
        filtersList = findViewById(R.id.sortLIst);
        AddItemsToList();

        filtersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sortList != null) {
                    OfferSortItem listItem = sortList.get(position);
                    //make request
                }
            }
        });

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToHome = new Intent(getApplicationContext(), Home.class);
                startActivity(moveToHome);
            }
        });
    }

    private void AddItemsToList() {
        final ProgressDialog p = GetProcessDialog(FiltersActivity.this);
        p.show();
        ApiInterface apiService =
                WebRequesthandler.getClient().create(ApiInterface.class);

        Call<OfferSortList> call = apiService.GetOfferSortList();
        call.request();
        call.enqueue(new Callback<OfferSortList>() {
            @Override
            public void onResponse(Call<OfferSortList> call, retrofit2.Response<OfferSortList> response) {
                Log.d("ProductResult", response.body().toString());
                p.dismiss();
                sortList = response.body().getOffersSortList();
                List<String> vals = new ArrayList<String>();
                for (OfferSortItem val : sortList) {
                    vals.add(val.getOffersortName().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.singl_textview_list_item, (String[]) vals.toArray());
                filtersList.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<OfferSortList> call, Throwable t) {
                p.dismiss();
                Toast.makeText(getApplicationContext(), "failiure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}