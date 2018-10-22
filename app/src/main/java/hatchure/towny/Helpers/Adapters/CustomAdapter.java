package hatchure.towny.Helpers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.List;
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
        Glide.with(context)
                //.load(Uri.parse("https://i2.wp.com/beebom.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg?w=640&ssl=1"))
                .load(Uri.parse(BASE_URL+offer.getOfferBasepath()+offer.getOfferImg()))
                .into( holder.offerImage);
        holder.offerTitle.setText(offer.getOfferName());
        holder.shopName.setText(offer.getShopName());
        holder.distance.setText(offer.getDistance());
        holder.rating.setNumStars(Integer.parseInt(offer.getShopRating()));
        holder.expireTime.setText(offer.getOfferExpires());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Toast.makeText(context,offer.getOfferName(), Toast.LENGTH_LONG);
                Intent intent = new Intent(context, OfferDetails.class);
                context.startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersList.size();
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
            shopName= itemView.findViewById(R.id.shop_name);
            distance= itemView.findViewById(R.id.distance);
            expireTime= itemView.findViewById(R.id.expire_time);
            rating= itemView.findViewById(R.id.ratingBar);

        }
    }
}
