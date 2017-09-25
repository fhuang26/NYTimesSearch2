package com.example.felixh.nytimessearch.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.felixh.nytimessearch.R;
import com.example.felixh.nytimessearch.data.SearchState;

public class SettingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final int N_DATESORT = 3;
    public String datesort = "None";
    CompoundButton.OnCheckedChangeListener checkListener;
    class DatesortOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            datesort = parent.getItemAtPosition(pos).toString();
            if (datesort.equals("None")) {
                SearchState.date_sort_sign = 0;
            } else if (datesort.equals("From Newest to Oldest")) {
                SearchState.date_sort_sign = -1;
            } else {
                SearchState.date_sort_sign = 1;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            datesort = "None";
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (SearchActivity.currActivity.etQuery.getText().toString().contains("art")) {
            SearchState.flag_art = true;
        }
        if (SearchActivity.currActivity.etQuery.getText().toString().contains("fashion style")) {
            SearchState.flag_fashion_style = true;
        }
        if (SearchActivity.currActivity.etQuery.getText().toString().contains("sports")) {
            SearchState.flag_sports = true;
        }
        CheckBox cb_art = (CheckBox) findViewById(R.id.cb_art);
        if (SearchState.flag_art) cb_art.setChecked(true);

        CheckBox cb_fashion_style = (CheckBox) findViewById(R.id.cb_fashion_style);
        if (SearchState.flag_fashion_style) cb_fashion_style.setChecked(true);

        CheckBox cb_sports = (CheckBox) findViewById(R.id.cb_sports);
        if (SearchState.flag_sports) cb_sports.setChecked(true);

        // Defines a listener for every time a checkbox is checked or unchecked
        checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                // compoundButton is the checkbox
                // boolean is whether or not checkbox is checked
                // Check which checkbox was clicked
                switch(view.getId()) {
                    case R.id.cb_art:
                        if (checked) {
                            SearchState.flag_art = true;
                        } else {
                            SearchState.flag_art = false;
                        }
                        break;
                    case R.id.cb_fashion_style:
                        if (checked) {
                            SearchState.flag_fashion_style = true;
                        } else {
                            SearchState.flag_fashion_style = false;
                        }
                        break;
                    case R.id.cb_sports:
                        if (checked) {
                            SearchState.flag_sports = true;
                        } else {
                            SearchState.flag_sports = false;
                        }
                        break;
                }
                String w = "";
                if (SearchState.flag_art) w = "art";
                if (SearchState.flag_fashion_style) {
                    if (w.isEmpty()) w = "fashion style";
                    else w = w + " fashion style";
                }
                if (SearchState.flag_sports) {
                    if (w.isEmpty()) w = "sports";
                    else w = w + " sports";
                }

                SearchActivity.currActivity.etQuery.setText(w);
            }
        };
        cb_art.setOnCheckedChangeListener(checkListener);
        cb_fashion_style.setOnCheckedChangeListener(checkListener);
        cb_sports.setOnCheckedChangeListener(checkListener);

        Spinner spDatesort = (Spinner) findViewById(R.id.spDateSorting);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.datesort_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spDatesort
        spDatesort.setAdapter(adapter);

        spDatesort.setScrollBarSize(N_DATESORT);
        if (SearchState.date_sort_sign == -1) {
            spDatesort.setSelection(0);
        } else if (SearchState.date_sort_sign == 1) {
            spDatesort.setSelection(1);
        } else {
            spDatesort.setSelection(2);
        }

        spDatesort.setOnItemSelectedListener(new DatesortOnItemSelectedListener());
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // store the values selected into a Calendar instance
        String monthStr = "" + month;
        if (month < 10) monthStr = "0" + monthStr;
        String dayStr = "" + day;
        if (day < 10) dayStr = "0" + dayStr;
        SearchState.beginDate = "" + year + "-" + monthStr + "-" + dayStr;
        Toast.makeText(this.getApplicationContext(), SearchState.beginDate, Toast.LENGTH_LONG);
        /*
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        */
    }
}
