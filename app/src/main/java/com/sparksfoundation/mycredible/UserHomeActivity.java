package com.sparksfoundation.mycredible;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sparksfoundation.mycredible.Classes.SimpleFragmentPagerAdapter;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetailsData;
import com.sparksfoundation.mycredible.Remote.APIUtils;
import com.sparksfoundation.mycredible.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private UserService userService;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.fragment_container);
        mViewPager.setAdapter(simpleFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        userService = APIUtils.getUserService();

        userId = Integer.parseInt(getIntent().getStringExtra("id"));
        getPersonalDetails();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sign_out_menu:
                //sign out
                //AuthUI.getInstance().signOut(this);
                //Toast.makeText(ChatActivityMine.this, "Signed out Successfully!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(ChatActivityMine.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getPersonalDetails()
    {
        Call<PersonalDetailsData> call = userService.getPersonalDetails(userId);
        call.enqueue(new Callback<PersonalDetailsData>() {
            @Override
            public void onResponse(Call<PersonalDetailsData> call, Response<PersonalDetailsData> response) {
//                showToast("Response Successful: " + response.body().getData().toString(), Toast.LENGTH_SHORT);
                showToast("Response Successful: ", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<PersonalDetailsData> call, Throwable t) {
                showToast("Response Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void showToast(String msg, int length)
    {
        Toast.makeText(this, msg, length).show();
    }

}
