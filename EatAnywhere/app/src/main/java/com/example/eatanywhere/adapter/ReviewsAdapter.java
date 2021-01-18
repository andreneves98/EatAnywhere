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
import com.example.eatanywhere.model.reviews.Review;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.CustomViewHolder> {

    private List<Review> reviewsList;
    private Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public ReviewsAdapter(Context context, List<Review> reviewsList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.reviewsList = reviewsList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView profilePic;
        TextView userRating;
        TextView reviewDate;
        TextView ratingText;
        TextView reviewText;
        TextView visit;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            profilePic = mView.findViewById(R.id.profilePic);
            userRating = mView.findViewById(R.id.user_rating);
            reviewDate = mView.findViewById(R.id.review_date);
            ratingText = mView.findViewById(R.id.rating_text);
            reviewText = mView.findViewById(R.id.review_text);
            visit = mView.findViewById(R.id.visit);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });*/
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.review_card, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        //holder.profilePic.setImageDrawable(reviewsList.get(position).getUser().getProfileImage());

        holder.userRating.setText(reviewsList.get(position).getRating());

        String ratingColor = "#";
        ratingColor += reviewsList.get(position).getRatingColor();
        holder.userRating.setTextColor(Color.parseColor(ratingColor));

        int timestamp = reviewsList.get(position).getTimestamp();
        holder.reviewDate.setText(reviewsList.get(position).timestampToDate(timestamp));

        holder.ratingText.setText(reviewsList.get(position).getRatingText());
        holder.reviewText.setText(reviewsList.get(position).getReviewText());
        holder.visit.setText(reviewsList.get(position).getReviewTimeFriendly());


        /*Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(restaurantsList.get(position).getFeaturedImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.thumb);*/

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
