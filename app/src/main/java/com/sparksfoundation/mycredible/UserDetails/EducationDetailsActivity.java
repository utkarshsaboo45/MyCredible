package com.sparksfoundation.mycredible.UserDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sparksfoundation.mycredible.R;
import com.sparksfoundation.mycredible.UserHomeActivity;

public class EducationDetailsActivity extends AppCompatActivity {

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_details);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EducationDetailsActivity.this, UserHomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void showToast(String msg, int length)
    {
        Toast.makeText(this, msg, length).show();
    }
}
