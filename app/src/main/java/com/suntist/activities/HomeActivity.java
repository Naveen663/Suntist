package com.suntist.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suntist.R;
import com.suntist.adapters.DataItemsAdapter;
import com.suntist.models.DataItems;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Dell on 3/17/2018.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edt_name,edt_mobile;
    RadioGroup rg_gender;
    RadioButton rb_gender;
    Button btn_submit;
    RecyclerView rv_data;

    String name, mobile;

    ArrayList<DataItems> data_items_list;


    private static final String DATABASE_NAME = "myDb.db";
    private static final String DATABASE_TABLENAME = "SUNTIST";
    private static final String DATABASE_CREATE_TABLE ="create table if not exists SUNTIST (_id integer primary key autoincrement,name text not null,mobile integer not null, sex text not null)";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        initViews();

    }

    private void initViews() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv_ab_name = (TextView) toolbar.findViewById(R.id.tv_ab_name);
        TextView tv_ab_email = (TextView) toolbar.findViewById(R.id.tv_ab_email);
        TextView tv_ab_welcome = (TextView) toolbar.findViewById(R.id.tv_ab_welcome);


        String fb_name = getIntent().getStringExtra("FB_NAME");
        String fb_email = getIntent().getStringExtra("FB_EMAIL");


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/FuturaMediumBT.ttf");
        tv_ab_name.setTypeface(custom_font);
        tv_ab_email.setTypeface(custom_font);
        tv_ab_welcome.setTypeface(custom_font);

        tv_ab_name.setText(""+fb_name);
        tv_ab_email.setText(""+fb_email);


        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_mobile = (EditText) findViewById(R.id.edt_telephone);

        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);

        data_items_list = new ArrayList<>();

        rv_data = (RecyclerView) findViewById(R.id.rv_data);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_submit:

                name = edt_name.getText().toString().trim();
                mobile = edt_mobile.getText().toString().trim();

                int selectedId = rg_gender.getCheckedRadioButtonId();
                rb_gender = (RadioButton) findViewById(selectedId);


                if(TextUtils.isEmpty(name)){

                    Toast.makeText(HomeActivity.this,"Please enter Name",Toast.LENGTH_LONG).show();

                }else if(TextUtils.isEmpty(mobile)){

                    Toast.makeText(HomeActivity.this,"Please enter Telephone Number",Toast.LENGTH_LONG).show();

                }else if(TextUtils.isEmpty(rb_gender.getText().toString())){

                    Toast.makeText(HomeActivity.this,"Please select Gender",Toast.LENGTH_LONG).show();

                }else{

                    Log.v("##Suntist HA","Name : "+name);
                    Log.v("##Suntist HA","Mobile : "+mobile);
                    Log.v("##Suntist HA","rb_gender : "+rb_gender.getText().toString());

                    DataItems di = new DataItems(name, mobile, rb_gender.getText().toString());
                    data_items_list.add(di);


                    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                    rv_data.setLayoutManager(mLinearLayoutManager);
                    rv_data.setHasFixedSize(true);
                    rv_data.setItemAnimator(new DefaultItemAnimator());

                    //Set Adapter
                    DataItemsAdapter mAdapter = new DataItemsAdapter(data_items_list);
                    rv_data.setAdapter(mAdapter);

                    mAdapter.notifyDataSetChanged();


                    addToDB();

                }



                break;

        }
    }

    //Storing in SQLite
    private void addToDB() {

        SQLiteDatabase myDB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        myDB.execSQL(DATABASE_CREATE_TABLE);


        ContentValues newRow = new ContentValues();

            newRow.put("name", name);
            newRow.put("mobile",mobile);
            newRow.put("sex",rb_gender.getText().toString());

            myDB.insert(DATABASE_TABLENAME, null, newRow);


        Toast.makeText(getApplicationContext(), "New Row Added Successfully with Mobile : "+mobile, Toast.LENGTH_LONG).show();


    }
}
