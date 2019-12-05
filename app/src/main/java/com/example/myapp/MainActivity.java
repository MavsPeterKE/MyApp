package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edFname;
    private EditText edGrossIncome;
    private EditText edNoOfChildren;
    private Button btCompute;
    private String firstName;
    private String grossIncome;
    private String noOfChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edFname = findViewById(R.id.fname);
        edGrossIncome = findViewById(R.id.grossIncome);
        edNoOfChildren = findViewById(R.id.noOfChildren);
        btCompute = findViewById(R.id.compute);
        btCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInput();
            }
        });
    }


    private void validateUserInput() {
        firstName = edFname.getText().toString().trim();
        grossIncome = edGrossIncome.getText().toString().trim();
        noOfChildren = edNoOfChildren.getText().toString().trim();
        if (firstName.equals("")||firstName.isEmpty()){
            edFname.setError("Required");
        }else if (grossIncome.equals("")||grossIncome.isEmpty()){
            edGrossIncome.setError("Required");
        }else if (noOfChildren.equals("")||noOfChildren.isEmpty()){
            edNoOfChildren.setError("Required");
        }else {
            double dependencyExemption = Integer.parseInt(noOfChildren) * (3000);
            double netIncome = Double.parseDouble(grossIncome) - dependencyExemption;
            calculateTaxDue(netIncome);
        }
    }

    private void calculateTaxDue(double netIncome) {
        if (netIncome > 0 && netIncome<5000) {
            int taxPercentage = 15;
            double taxCalculated = (taxPercentage / 100) * netIncome;
            showIncomeAndTax(netIncome, taxCalculated);
        } else if (netIncome > 50000) {
            int taxPercentage = 25; //for first 50000
            double taxOnFisrt50 = (taxPercentage / 100) * netIncome;
            double taxOnremaining = (taxPercentage / 100) * (netIncome-50000);
            double totalTax = taxOnFisrt50+taxOnremaining;
            showIncomeAndTax(netIncome, totalTax);
        }else {
            Toast.makeText(this, "Income is Lower. No Tax", Toast.LENGTH_SHORT).show();
        }
    }

    private void showIncomeAndTax(double netIncome, double taxCalculated) {
        Toast.makeText(this, "Net Income :" + netIncome + " Tax Payable =" + taxCalculated, Toast.LENGTH_SHORT).show();
        startBlankActivity();
    }

    private void startBlankActivity() {
        startActivity(new Intent(this, EmptyActivity.class));
    }
}
