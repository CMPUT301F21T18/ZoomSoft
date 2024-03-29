package com.example.zoomsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity class for adding a new habit
 */
public class AddHabit extends AppCompatActivity {

    EditText habitTitle;
    EditText habitReason;
    EditText habitDescription;
    Switch status;
    Switch dayOfWeek;
    DatePicker startDate;
    ImageButton addButton;
    ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        addButton = findViewById(R.id.edit_habit_check);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                habitTitle = findViewById(R.id.habit_title_edit_text);
                habitReason = findViewById(R.id.habit_reason_edit_text);
                habitDescription = findViewById(R.id.habit_description_edit_text);
                status = (Switch) findViewById(R.id.public_private_switch);
                startDate = (DatePicker) findViewById(R.id.datePicker);

                String privacy;

                if (status.isChecked()){
                    privacy = "private";
                }
                else {
                    privacy = "public";
                }

                String title = habitTitle.getText().toString();
                int newDay = startDate.getDayOfMonth();
                int newMonth = startDate.getMonth() + 1;
                int newYear = startDate.getYear();
                String date = newYear+"-"+newMonth+"-"+newDay;
                String reason = habitReason.getText().toString();
                String description = habitDescription.getText().toString();
                ArrayList<Integer> days = new ArrayList<>();

                //Reading the switches and setting days of the week
                dayOfWeek = findViewById(R.id.switch_1);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_2);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_3);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_4);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_5);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_6);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                dayOfWeek = findViewById(R.id.switch_7);
                if (dayOfWeek.isChecked()){
                    days.add(1);
                }
                else {
                    days.add(0);
                }
                if(!title.isEmpty()) {
                    Habits habit = new Habits(title, date, reason, days, privacy);
                    MainPageFirebase mainPageFirebase = new MainPageFirebase();
                    mainPageFirebase.addNewHabit(habit, description);
                    Toast.makeText(AddHabit.this,
                            "New Habit Added", Toast.LENGTH_LONG).show();

                    finish(); //this is the right thing to do.
                }
                else {
                    Toast.makeText(AddHabit.this,
                            "You must enter a habit title", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton = findViewById(R.id.edit_habit_stop);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}