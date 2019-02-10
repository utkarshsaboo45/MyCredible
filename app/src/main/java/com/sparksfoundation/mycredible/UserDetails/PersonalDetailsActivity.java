package com.sparksfoundation.mycredible.UserDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetails;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetailsData;
import com.sparksfoundation.mycredible.R;
import com.sparksfoundation.mycredible.Remote.APIUtils;
import com.sparksfoundation.mycredible.Remote.UserService;
import com.sparksfoundation.mycredible.UserHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sparksfoundation.mycredible.LoginActivity.MY_PREF;

public class PersonalDetailsActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText nameEditText, emailEditText, mobileEditText, locationEditText, linksEditText, skillsEditText;

    private String name, email, mobile, location, links, skills;

    private int userId;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        nameEditText = findViewById(R.id.full_name_edit_text);
//        emailEditText = findViewById(R.id.email_edit_text);
        mobileEditText = findViewById(R.id.mobile_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        linksEditText = findViewById(R.id.links_edit_text);
        skillsEditText = findViewById(R.id.skills_edit_text);

        nameEditText.setText("utkarsh");
//        emailEditText.setText("utka@gmail.com");
        mobileEditText.setText("9876543210");
        locationEditText.setText("Vellore");
        linksEditText.setText("www.google.com");
        skillsEditText.setText("Android app dev");

        userService = APIUtils.getUserService();

        SharedPreferences prefs = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        userId = prefs.getInt("id", 0);

        final String isUpdate = getIntent().getStringExtra("isUpdate");

        if (isUpdate == null)
            getSupportActionBar().setTitle("Set Personal Details");
        else {
            getSupportActionBar().setTitle("Edit Personal Details");
            getPersonalDetails();
        }

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString().trim();
                email = "";//emailEditText.getText().toString().trim();
                mobile = mobileEditText.getText().toString().trim();
                location = locationEditText.getText().toString().trim();
                links = linksEditText.getText().toString().trim();
                skills = skillsEditText.getText().toString().trim();

                PersonalDetails personalDetails = new PersonalDetails(skills, mobile, name, links, location, email);

                if (isUpdate == null)
                    setPersonalDetails(personalDetails);
                else {
                    updatePersonalDetails(personalDetails);
                }
            }
        });
    }

    public void getPersonalDetails()
    {
        Call<PersonalDetailsData> call = userService.getPersonalDetails(userId);
        call.enqueue(new Callback<PersonalDetailsData>() {
            @Override
            public void onResponse(Call<PersonalDetailsData> call, Response<PersonalDetailsData> response) {
                if(response.body() != null) {
                    nameEditText.setText(response.body().getData().getName());
                    //emailEditText.setText(response.body().getData().getEmail());
                    mobileEditText.setText(response.body().getData().getMobile_no());
                    locationEditText.setText(response.body().getData().getLocation());
                    linksEditText.setText(response.body().getData().getLinks());
                    skillsEditText.setText(response.body().getData().getSkills());
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

    public void setPersonalDetails(final PersonalDetails personalDetails)
    {
        Call<PersonalDetailsData> call = userService.setPersonalDetails(userId, personalDetails);
        call.enqueue(new Callback<PersonalDetailsData>() {
            @Override
            public void onResponse(Call<PersonalDetailsData> call, Response<PersonalDetailsData> response) {
                PersonalDetailsData personalDetailsData = new PersonalDetailsData();
                personalDetailsData.setData(response.body().getData());

                Intent intent = new Intent(PersonalDetailsActivity.this, ProfessionalDetailsActivity.class);
                intent.putExtra("id", userId);
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PersonalDetailsData> call, Throwable t) {
                showToast("Set Personal details Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void updatePersonalDetails(final PersonalDetails personalDetails)
    {
        Call<PersonalDetailsData> call = userService.updatePersonalDetails(userId, personalDetails);
        call.enqueue(new Callback<PersonalDetailsData>() {
            @Override
            public void onResponse(Call<PersonalDetailsData> call, Response<PersonalDetailsData> response) {
                showToast("Personal Details Updated", Toast.LENGTH_SHORT);
                Intent intent = new Intent(PersonalDetailsActivity.this, UserHomeActivity.class);
                intent.putExtra("id", userId);
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PersonalDetailsData> call, Throwable t) {
                showToast("Update Personal details Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }


    public void showToast(String msg, int length)
    {
        Toast.makeText(this, msg, length).show();
    }
}
