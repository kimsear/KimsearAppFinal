package com.example.kimsearappfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailFragment extends Fragment {

    private ViewPager2 viewPager;
    private TextView productTitleTextView;
    private TextView productDescriptionTextView;
    private TextView productPriceTextView;
    private Button orderButton;
    private Button cancelButton;

    private List<Integer> imageList;

    private float totalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        productTitleTextView = view.findViewById(R.id.productTitleTextView);
        productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
        productPriceTextView = view.findViewById(R.id.productPriceTextView);
        orderButton = view.findViewById(R.id.orderButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        Spinner spinner = view.findViewById(R.id.SpinnerItem);
        Spinner spinner2 = view.findViewById(R.id.SpinnerItem2);

        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("Blue");
        colorList.add("Red");
        colorList.add("Yellow");
        colorList.add("White");

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, colorList);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(colorAdapter);

        ArrayList<String> quantityList = new ArrayList<>();
        quantityList.add("1");
        quantityList.add("2");
        quantityList.add("3");
        quantityList.add("4");

        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, quantityList);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(quantityAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Selected Item: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Selected Item: " + item, Toast.LENGTH_SHORT).show();

                float price = getArguments().getFloat("price", 0.0F);
                int quantity = Integer.parseInt(item);
                totalPrice = price * quantity;
                productPriceTextView.setText(String.format("Total Price: $%.2f", totalPrice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String description = getArguments().getString("description");
            float price = getArguments().getFloat("price", 0.0F);
            imageList = getArguments().getIntegerArrayList("imageList");

            productTitleTextView.setText(title);
            productDescriptionTextView.setText(description);
            productPriceTextView.setText(String.format("Price: $%.2f", price));  // Initial price display

            if (imageList != null && !imageList.isEmpty()) {
                setupViewPager();
            } else {
                Toast.makeText(getContext(), "No images to display", Toast.LENGTH_SHORT).show();
            }
        }

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserLoggedIn()) {
                    placeOrder();
                } else {
                    navigateToLogin();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void setupViewPager() {
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageList);
        viewPager.setAdapter(adapter);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("logged_in", false);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void placeOrder() {
        Toast.makeText(getContext(), "Order placed for $" + totalPrice, Toast.LENGTH_SHORT).show();
    }

    private class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

        private List<Integer> images;

        public ImagePagerAdapter(List<Integer> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(images.get(position));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}

