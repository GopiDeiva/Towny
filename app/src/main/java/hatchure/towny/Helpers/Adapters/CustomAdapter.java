package hatchure.towny.Helpers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import hatchure.towny.Helpers.GlideApp;
import hatchure.towny.Helpers.GlideHandler;
import hatchure.towny.Models.Offer;
import hatchure.towny.OfferDetails;
import hatchure.towny.R;

import static hatchure.towny.WebHandler.WebRequesthandler.BASE_URL;

public class CustomAdapter extends RecyclerView.Adapter {

    List<Offer> offersList;
    Context context;

    public CustomAdapter(Context context, List<Offer> offersList) {
        this.context = context;
        this.offersList = offersList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        final Offer offer = offersList.get(position);
        String basePath = offer.getOfferBasepath();
        if (basePath.startsWith("/")) {
            offer.setOfferBasepath(basePath.substring(1, basePath.length()));
        }
        Toast.makeText(context, BASE_URL + offer.getOfferBasepath() + offer.getOfferImg(), Toast.LENGTH_SHORT).show();
        GlideApp.with(context)
                .load(Uri.parse(BASE_URL + offer.getOfferBasepath() + offer.getOfferImg()))
                .into(holder.offerImage);
        Log.i("towny test", offer.getOfferName());
        holder.offerTitle.setText(offer.getOfferName());
        holder.shopName.setText(offer.getShopName());
        holder.distance.setText(offer.getDistance().substring(0,offer.getDistance().indexOf('.')+2)+"Km");
        holder.rating.setRating(Integer.parseInt(offer.getShopRating()));
        holder.expireTime.setText(offer.getOfferExpires());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Toast.makeText(context, offer.getOfferName(), Toast.LENGTH_LONG);
                Intent intent = new Intent(context, OfferDetails.class);
                context.startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        if (offersList != null) {
            Log.i("towny size", offersList.size() + "");
            return offersList.size();
        }
        Log.i("towny size", "size 0");
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView offerTitle, shopName, distance, expireTime;
        RatingBar rating;
        ImageView offerImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            offerTitle = itemView.findViewById(R.id.offer_title);
            offerImage = itemView.findViewById(R.id.offer_image);
            shopName = itemView.findViewById(R.id.shop_name);
            distance = itemView.findViewById(R.id.distance);
            expireTime = itemView.findViewById(R.id.expire_time);
            rating = itemView.findViewById(R.id.ratingBar);
        }
    }
}