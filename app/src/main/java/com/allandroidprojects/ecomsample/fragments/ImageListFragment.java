package com.allandroidprojects.ecomsample.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.product.ItemDetailsActivity;
import com.allandroidprojects.ecomsample.startup.MainActivity;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static MainActivity mActivity;
    public String message = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        String[] items=null;
        String[] names=null;
        String[] prices=null;
        String[] descs=null;
        String[] contacts=null;
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            items = ImageUrlUtils.getMen_image();
            names = ImageUrlUtils.getMen_name();
            prices = ImageUrlUtils.getMen_price();
            descs = ImageUrlUtils.getMen_desc();
            contacts = ImageUrlUtils.getMen_contact();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 2){
            items = ImageUrlUtils.getWomen_image();
            names = ImageUrlUtils.getWomen_name();
            prices = ImageUrlUtils.getWomen_price();
            descs = ImageUrlUtils.getWomen_desc();
            contacts = ImageUrlUtils.getWomen_contact();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 3){
            items = ImageUrlUtils.getAcc_image();
            names = ImageUrlUtils.getAcc_name();
            prices = ImageUrlUtils.getAcc_price();
            descs = ImageUrlUtils.getAcc_desc();
            contacts = ImageUrlUtils.getAcc_contact();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 4){
            items = ImageUrlUtils.getClothing_image();
            names = ImageUrlUtils.getClothing_name();
            prices = ImageUrlUtils.getClothing_price();
            descs = ImageUrlUtils.getClothing_desc();
            contacts = ImageUrlUtils.getClothing_contact();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 5){
            items = ImageUrlUtils.getShoes_image();
            names = ImageUrlUtils.getShoes_name();
            prices = ImageUrlUtils.getShoes_price();
            descs = ImageUrlUtils.getShoes_desc();
            contacts = ImageUrlUtils.getShoes_contact();
        }else {
            items = ImageUrlUtils.getImageUrls();
            names = items;
            prices = items;
            descs = items;
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, items, names, prices, descs, contacts));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private String[] mNames;
        private String[] mPrices;
        private String[] mDescs;
        private String[] mContacts;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final TextView name;
            public final TextView price;
            public final TextView desc;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                name = (TextView) view.findViewById(R.id.name);
                price = (TextView) view.findViewById(R.id.price);
                desc = (TextView) view.findViewById(R.id.desc);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, String[] items,
                                               String[] names, String[] prices, String[] descs,
                                               String[] contacts) {
            mNames = names;
            mValues = items;
            mPrices = prices;
            mDescs = descs;
            mContacts = contacts;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
                //((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(mValues[position]);

            holder.mImageView.setImageURI(uri);
            holder.mImageView.setMinimumHeight(100);

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mValues[position]);
                    intent.putExtra("name", mNames[position]);
                    intent.putExtra("desc", mDescs[position]);
                    intent.putExtra("price", mPrices[position]);
                    intent.putExtra("contact", mContacts[position]);
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });
            holder.name.setText(mNames[position]);
            holder.price.setText("HK$ " + mPrices[position]);
            holder.desc.setText(mDescs[position]);
        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }
    }

}
