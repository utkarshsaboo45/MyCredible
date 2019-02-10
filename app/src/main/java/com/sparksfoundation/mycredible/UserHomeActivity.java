package com.sparksfoundation.mycredible;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sparksfoundation.mycredible.Classes.SimpleFragmentPagerAdapter;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetailsData;
import com.sparksfoundation.mycredible.ProfessionalDetailsPOJOClasses.ProfessionalDetailsData;
import com.sparksfoundation.mycredible.Remote.APIUtils;
import com.sparksfoundation.mycredible.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sparksfoundation.mycredible.LoginActivity.MY_PREF;

public class UserHomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private TextView nameTextView, emailTextView, organizationTextView, locationTextView;

    private String name, email, organization, location;

    private UserService userService;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        nameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        organizationTextView = findViewById(R.id.organization_text_view);
        locationTextView = findViewById(R.id.location_text_view);

        name = email = organization = location = "";

        SharedPreferences prefs = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        email = prefs.getString("email", "-");
        userId = prefs.getInt("id", 0);

        emailTextView.setText(email);

        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.fragment_container);
        mViewPager.setAdapter(simpleFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        userService = APIUtils.getUserService();

        getPersonalDetails();
        getProfessionalDetails();
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
                //AuthUI.getInstance().signOut(this);
                showToast("Signed out Successfully!", Toast.LENGTH_SHORT);
                finish();
                startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));
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
                if(response.body() != null) {
                    name = response.body().getData().getName();
                    location = response.body().getData().getLocation();
                    nameTextView.setText(name);
                    locationTextView.setText(location);
                } else {
                    showToast("Personal Details Response Empty", Toast.LENGTH_LONG);
                }
            }
            @Override
            public void onFailure(Call<PersonalDetailsData> call, Throwable t) {
                showToast("Response Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void getProfessionalDetails()
    {
        Call<ProfessionalDetailsData> call = userService.getProfessionalDetails(userId);
        call.enqueue(new Callback<ProfessionalDetailsData>() {
            @Override
            public void onResponse(Call<ProfessionalDetailsData> call, Response<ProfessionalDetailsData> response) {
                if(response.body() != null) {
                    organization = response.body().getData().getOrganisation();
                    organizationTextView.setText(organization);
                } else {
                    showToast("Professional Details Response Empty", Toast.LENGTH_LONG);
                }
            }
            @Override
            public void onFailure(Call<ProfessionalDetailsData> call, Throwable t) {
                showToast("Response Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void showToast(String msg, int length)
    {
        Toast.makeText(this, msg, length).show();
    }

}
