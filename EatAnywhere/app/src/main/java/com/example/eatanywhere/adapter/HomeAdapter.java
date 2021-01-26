package com.example.eatanywhere.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatanywhere.R;
import com.example.eatanywhere.RecyclerViewClickInterface;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CustomViewHolder> {

    private List<Restaurant_> restaurantsList;
    private Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public HomeAdapter(Context context, List<Restaurant_> restaurantsList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.restaurantsList = restaurantsList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView thumb;
        TextView restaurantTitle;
        TextView price_category;
        TextView rating;
        TextView location;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            thumb = mView.findViewById(R.id.thumbFav);
            restaurantTitle = mView.findViewById(R.id.Restaurant_TitleFav);
            price_category = mView.findViewById(R.id.PriceCategoryFav);
            rating = mView.findViewById(R.id.RatingFav);
            location = mView.findViewById(R.id.restaurant_locationFav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.restaurant_card, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.restaurantTitle.setText(restaurantsList.get(position).getName());
        holder.restaurantTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_restaurant_24, 0, 0, 0);

        // PRICE_RANGE: [2,3,4] > [€,€€,€€€]
        int price_range = restaurantsList.get(position).getPriceRange();
        String price_rangeStr = "";
        switch(price_range) {
            case 2:
                price_rangeStr = "[€]";
                break;
            case 3:
                price_rangeStr = "[€€]";
                break;
            case 4:
                price_rangeStr = "[€€€]";
                break;
        }
        price_rangeStr += " " + restaurantsList.get(position).getCuisines();
        holder.price_category.setText(price_rangeStr);

        holder.rating.setText(restaurantsList.get(position).getUserRating().getAggregateRating());
        String ratingColor = "#" + restaurantsList.get(position).getUserRating().getRatingColor();
        holder.rating.setTextColor(Color.parseColor(ratingColor));
        holder.rating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_star_20, 0);

        holder.location.setText(restaurantsList.get(position).getLocation().getAddress());
        holder.location.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_place_20, 0, 0, 0);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(restaurantsList.get(position).getFeaturedImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.thumb);

    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }
}
