package com.allandroidprojects.ecomsample.product;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.fragments.ImageListFragment;
import java.lang.String;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    String stringName;
    String stringPrice;
    String stringDesc;
    String stringContact;
    TextView mDesc;
    TextView mPrice;
    TextView mTextView;
    TextView mContact;
    SimpleDraweeView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        mTextView = (TextView) findViewById(R.id.prod_name);
        mPrice = (TextView) findViewById(R.id.prod_price);
        mDesc = (TextView) findViewById(R.id.desc);
        mContact = (TextView) findViewById(R.id.contact);
        mContact.setPaintFlags(mContact.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mContact.getText().toString().trim()));
                startActivity(callIntent);
            }
        });

        //Getting parameters from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            stringName = getIntent().getStringExtra("name");
            stringPrice = getIntent().getStringExtra("price");
            stringDesc = getIntent().getStringExtra("desc");
            stringContact = getIntent().getStringExtra("contact");
        }

        Uri uri = Uri.parse(stringImageUri);
        mImageView.setImageURI(uri);
        mTextView.setText(stringName);
        mPrice.setText("HK$ " + stringPrice);
        mDesc.setText(stringDesc);
        mContact.setText("+852 " + stringContact);

    }
}
