package com.example.connectmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;

public class make_payment extends AppCompatActivity implements JsonResponse {

    Button payBtn;
    TextView amtText;
    ProgressBar loadingSpinner;
    TextInputEditText cardNumber, expiry, cvv, cardholderName;
    String amount, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        amtText = findViewById(R.id.amt);
        payBtn = findViewById(R.id.paybtn);
        loadingSpinner = findViewById(R.id.loading_spinner);
        cardNumber = findViewById(R.id.card_number);
        expiry = findViewById(R.id.expiry);
        cvv = findViewById(R.id.cvv);
        cardholderName = findViewById(R.id.cardholder_name);

        // Set amount
        amtText.setText("Total Amount: $" + view_cart.total_amt);

        // Add text watchers for real-time formatting
        cardNumber.addTextChangedListener(new CardNumberWatcher());
        expiry.addTextChangedListener(new ExpiryWatcher());

        payBtn.setOnClickListener(v -> {
            if (validateForm()) {
                loadingSpinner.setVisibility(View.VISIBLE);
                payBtn.setEnabled(false);

                new Handler().postDelayed(() -> {
                    JsonReq JR = new JsonReq();
                    JR.json_response = make_payment.this;
                    String q = "/make_payment?omid=" + view_cart.omid + "&amt=" + view_cart.total_amt;
                    JR.execute(q);
                }, 2000);
            } else {
                Toast.makeText(this, "Please correct the form errors", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Card Number Validation (16 digits, no spaces)
        String cardNum = cardNumber.getText().toString().replace(" ", "");
        if (!Pattern.matches("\\d{16}", cardNum)) {
            cardNumber.setError("Enter a valid 16-digit card number");
            isValid = false;
        } else {
            cardNumber.setError(null);
        }

        // Expiry Validation (MM/YY format, future date)
        String exp = expiry.getText().toString();
        if (!Pattern.matches("\\d{2}/\\d{2}", exp)) {
            expiry.setError("Enter valid MM/YY");
            isValid = false;
        } else {
            String[] parts = exp.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            // Get current date using Calendar
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR) % 100; // Last 2 digits of year
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1

            if (month < 1 || month > 12 || (year < currentYear || (year == currentYear && month < currentMonth))) {
                expiry.setError("Card has expired or invalid date");
                isValid = false;
            } else {
                expiry.setError(null);
            }
        }

        // CVV Validation (3 or 4 digits)
        String cvvCode = cvv.getText().toString();
        if (!Pattern.matches("\\d{3,4}", cvvCode)) {
            cvv.setError("Enter valid CVV (3-4 digits)");
            isValid = false;
        } else {
            cvv.setError(null);
        }

        // Cardholder Name Validation
        String name = cardholderName.getText().toString().trim();
        if (name.length() < 2 || !Pattern.matches("[a-zA-Z ]+", name)) {
            cardholderName.setError("Enter a valid name");
            isValid = false;
        } else {
            cardholderName.setError(null);
        }

        return isValid;
    }

    @Override
    public void response(JSONObject jo) {
        try {
            status = jo.getString("status");
            loadingSpinner.setVisibility(View.GONE);
            payBtn.setEnabled(true);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), User_Home.class));
                finish();
            } else if (status.equalsIgnoreCase("failed")) {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            loadingSpinner.setVisibility(View.GONE);
            payBtn.setEnabled(true);
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    // Card Number Formatting (adds spaces every 4 digits)
    private class CardNumberWatcher implements TextWatcher {
        private boolean isFormatting;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (isFormatting) return;
            isFormatting = true;

            String input = s.toString().replace(" ", "");
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                if (i > 0 && i % 4 == 0) formatted.append(" ");
                formatted.append(input.charAt(i));
            }
            cardNumber.setText(formatted.toString());
            cardNumber.setSelection(formatted.length());

            isFormatting = false;
        }
    }

    // Expiry Formatting (adds "/" after MM)
    private class ExpiryWatcher implements TextWatcher {
        private boolean isFormatting;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (isFormatting) return;
            isFormatting = true;

            String input = s.toString().replace("/", "");
            if (input.length() >= 2) {
                String formatted = input.substring(0, 2) + (input.length() > 2 ? "/" + input.substring(2) : "");
                expiry.setText(formatted);
                expiry.setSelection(formatted.length());
            }

            isFormatting = false;
        }
    }
}