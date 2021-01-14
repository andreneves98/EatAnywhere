package com.example.eatanywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatanywhere.R;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Restaurant_> restaurantsList;
    private Context context;

    public CustomAdapter(Context context,List<Restaurant_> restaurantsList){
        this.context = context;
        this.restaurantsList = restaurantsList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView thumb;
        TextView restaurantTitle;
        TextView price_category;
        TextView rating;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            thumb = mView.findViewById(R.id.thumb);
            restaurantTitle = mView.findViewById(R.id.Restaurant_title);
            price_category = mView.findViewById(R.id.PriceCategory);
            rating = mView.findViewById(R.id.Rating);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.restaurantTitle.setText(restaurantsList.get(position).getName());

        // PRICE_RANGE: [2,3,4] > [€,€€,€€€]
        int price_range = restaurantsList.get(position).getPriceRange();
        String price_rangeStr = "";
        switch(price_range) {
            case 2:
                price_rangeStr = "€";
                break;
            case 3:
                price_rangeStr = "€€";
                break;
            case 4:
                price_rangeStr = "€€€";
                break;
        }
        price_rangeStr += " " + restaurantsList.get(position).getCuisines();
        holder.price_category.setText(price_rangeStr);

        holder.rating.setText(restaurantsList.get(position).getUserRating().getAggregateRating());

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
