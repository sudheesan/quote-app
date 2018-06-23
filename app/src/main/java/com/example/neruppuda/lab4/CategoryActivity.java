package com.example.neruppuda.lab4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    List<String> categoryNameList = new ArrayList<String>();
    String [] categories;
    String selectedCategory;
    private DrawerLayout navDrawerLayout;
    ProgressDialog progress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final ListView lv = findViewById(R.id.category_list_view);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        progress= new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progress.setTitle("Loading");
        progress.setMessage("Loading Categories ...");
        progress.setCancelable(false);
        progress.show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
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
                                Intent intent = new Intent(CategoryActivity.this,MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.category_nav_list:

                                break;
                            default:
                                break;
                        }

                        menuItem.setChecked(true);

                        navDrawerLayout.closeDrawers();

                        return true;
                    }
                });

        mDatabase.child("Categories").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                for(String category:map.keySet()){
                    categoryNameList.add(category);
                }

                categories= new String[categoryNameList.size()];
                categories=categoryNameList.toArray(categories);

                CustomCategoryListAdaptor adapter=new CustomCategoryListAdaptor(CategoryActivity.this, categories);
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

                selectedCategory = categories[i].toString();
                Intent intent = new Intent(CategoryActivity.this,RecyclerCategoryView.class);
                intent.putExtra("category",selectedCategory);
                startActivity(intent);

            }
        });


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
}
