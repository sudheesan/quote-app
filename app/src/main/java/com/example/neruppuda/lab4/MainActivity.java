package com.example.neruppuda.lab4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.firebase.client.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    List<String> athorList = new ArrayList<>();
    List<String> idList = new ArrayList<>();
    List<Integer> imageList = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    String [] itemList;
    String [] ids;
    Integer [] imageId;
    String [] categories;
    String authorId;
    String authorName;
    private DrawerLayout navDrawerLayout;
    ProgressDialog progress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable()){
            progress= new ProgressDialog(this,R.style.MyAlertDialogStyle);
            progress.setTitle("Loading");
            progress.setMessage("Loading Authors ...");
            progress.setCancelable(false);
            progress.show();

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Authors");
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

            navDrawerLayout = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.authors_nav_list:

                                    break;
                                case R.id.category_nav_list:
                                    Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                                    startActivity(intent);

                                    break;
                                default:
                                    break;
                            }

                            menuItem.setChecked(true);

                            navDrawerLayout.closeDrawers();

                            return true;
                        }
                    });

            final ListView lv = findViewById(R.id.listView);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Authors").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    for(String autherid:map.keySet()){
                        idList.add(autherid);
                    }

                    for(Map.Entry<String,Object> entry :map.entrySet()){

                        Map<String,Object> entryMap = (Map<String, Object>) entry.getValue();
                        athorList.add(entryMap.get("name").toString());
                        imageList.add(getResources().getIdentifier(entryMap.get("image").toString(), "drawable",getPackageName()));
                        categoryList.add(entryMap.get("Category").toString());
                    }


                    itemList=new String[athorList.size()];
                    itemList= athorList.toArray(itemList);

                    imageId = new Integer[imageList.size()];
                    imageId=imageList.toArray(imageId);

                    ids= new String[idList.size()];
                    ids=idList.toArray(ids);

                    categories = new String[categoryList.size()];
                    categories=categoryList.toArray(categories);

                    CustomAuthorListAdaptor adapter=new CustomAuthorListAdaptor(MainActivity.this, itemList, imageId,categories);
                    lv.setAdapter(adapter);

                    progress.dismiss();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    authorId=ids[i].toString();
                    authorName=itemList[i].toString();

                    mDatabase.child("Authors").child(authorId).child("quotes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<String> quotesList = (ArrayList<String>) dataSnapshot.getValue();
                            quotesList.removeAll(Collections.singleton(null));
                            Intent intent = new Intent(MainActivity.this,RecyclerTest.class);
                            intent.putStringArrayListExtra("quoteList",quotesList);
                            intent.putExtra("authorName",authorName);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });


        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
            builder.setMessage("Please connect to internet before launching the app")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           System.exit(0);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
