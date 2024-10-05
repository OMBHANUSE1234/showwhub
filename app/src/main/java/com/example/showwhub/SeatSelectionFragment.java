package com.example.showwhub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SeatSelectionFragment extends AppCompatActivity {

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    private ImageView seatView;
    RadioGroup radioGroupSeats;
    private Button buttonSelectSeats;
    private int seatPrice = 200; // Default price for PREMIUM seats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatselection);

        seatView = findViewById(R.id.seatView);
        radioGroupSeats = findViewById(R.id.radioGroupSeats);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        buttonSelectSeats = findViewById(R.id.buttonSelectSeats);

        radioGroupSeats.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int imageResId = R.drawable.seat2; // Default image resource
                int numberOfSeats = 1;

                if (checkedId == R.id.radioButton1) {
                    imageResId = R.drawable.seat1;
                    numberOfSeats = 1;
                } else if (checkedId == R.id.radioButton2) {
                    imageResId = R.drawable.seat2;
                    numberOfSeats = 2;
                } else if (checkedId == R.id.radioButton3) {
                    imageResId = R.drawable.seat3;
                    numberOfSeats = 3;
                } else if (checkedId == R.id.radioButton4) {
                    imageResId = R.drawable.seat4;
                    numberOfSeats = 4;
                } else if (checkedId == R.id.radioButton5) {
                    imageResId = R.drawable.seat5;
                    numberOfSeats = 5;
                } else if (checkedId == R.id.radioButton6) {
                    imageResId = R.drawable.seat6;
                    numberOfSeats = 6;
                }

                seatView.setImageResource(imageResId);
                updateButtonText(numberOfSeats);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateButtonText(int numberOfSeats) {
        int totalAmount = seatPrice * numberOfSeats;
        buttonSelectSeats.setText("Pay ₹" + totalAmount);
    }

    public void payRS(View view) {
        int selectedSeats = radioGroupSeats.getCheckedRadioButtonId();
        int numberOfSeats = 1;

        if (selectedSeats == R.id.radioButton1) {
            numberOfSeats = 1;
        } else if (selectedSeats == R.id.radioButton2) {
            numberOfSeats = 2;
        } else if (selectedSeats == R.id.radioButton3) {
            numberOfSeats = 3;
        } else if (selectedSeats == R.id.radioButton4) {
            numberOfSeats = 4;
        } else if (selectedSeats == R.id.radioButton5) {
            numberOfSeats = 5;
        } else if (selectedSeats == R.id.radioButton6) {
            numberOfSeats = 6;
        }

        int totalAmount = seatPrice * numberOfSeats;
        Toast.makeText(this, "Total Amount: ₹" + totalAmount, Toast.LENGTH_SHORT).show();

        // Add booking data to Firebase
        String movieName = "Dharmaveer 2"; // Replace with actual movie name
        String bookingDate = getCurrentDateTime();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            Booking booking = new Booking(bookingDate, movieName, "", numberOfSeats, totalAmount);
            databaseReference.child("bookings").child(userId).setValue(booking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Booking Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        return sdf.format(new Date());
    }
}