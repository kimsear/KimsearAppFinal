package com.example.kimsearappfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyDataModel> dataList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, List<MyDataModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataList != null && position < dataList.size()) {
            MyDataModel data = dataList.get(position);
            holder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardTitle;
        TextView cardDescription;
        TextView productPrice;
        private ImageView addCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
            cardTitle = itemView.findViewById(R.id.card_title);
            cardDescription = itemView.findViewById(R.id.card_description);
            productPrice = itemView.findViewById(R.id.productPriceTextView);
            addCart = itemView.findViewById(R.id.addCartButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        MyDataModel dataModel = dataList.get(position);
                        if (isUserLoggedIn()) {
                            addToCart(dataModel);
                        } else {
                            navigateToLogin();
                        }
                    }
                }
            });
        }

        private boolean isUserLoggedIn() {
            SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            return sharedPref.getBoolean("logged_in", false);
        }

        private void navigateToLogin() {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }

        private void addToCart(MyDataModel dataModel) {
            // Implement the logic to add the item to the cart
            // For example, you can store the item information in a database or shared preferences
            // This is a placeholder for your cart-adding logic
        }

        public void bind(MyDataModel dataModel) {
            if (!dataModel.getImageResIds().isEmpty()) {
                cardImage.setImageResource(dataModel.getImageResIds().get(0)); // Display the first image
            } else {
                // Handle the case where there are no images
                // You might want to set a placeholder image or hide the ImageView
            }
            cardTitle.setText(dataModel.getTitle());
            cardDescription.setText(dataModel.getDescription());
            productPrice.setText(String.format("$%.2f", dataModel.getPrice()));
        }
    }
}
