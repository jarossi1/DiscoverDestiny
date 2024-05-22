package UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_jordan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import database.Repository;
import entities.Excursion;
import entities.VacationEntity;

public class Vacation_Details extends AppCompatActivity {
    String name;
    String hotel;
    double price;
    int vacationID;
    EditText editName;
    EditText editHotel;
    EditText editPrice;
    EditText editNote;
    Repository repository;
    VacationEntity currentVacation;
    TextView editStartDate;
    TextView editEndDate;
    String startDate;
    String endDate;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    DatePickerDialog.OnDateSetListener vacayEndDate;
    DatePickerDialog.OnDateSetListener vacayStartDate;

    final Calendar myCalenderStart = Calendar.getInstance();
    final Calendar myCalenderEnd = Calendar.getInstance();

    int numExcursions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotelName");
        price = getIntent().getDoubleExtra("price", 0.0);

//        editNote=findViewById(R.id.note);
        editName = findViewById(R.id.vacationname);
        editHotel = findViewById(R.id.hotelname);
        editPrice = findViewById(R.id.vacationprice);
        editName.setText(name);
        editHotel.setText(hotel);
        editPrice.setText(Double.toString(price));

        editStartDate = findViewById(R.id.vacayStartDate);
        editEndDate = findViewById(R.id.vacayendDate);

//        editStartDate.setText(startDate);
//        editEndDate.setText(endDate);

        vacationID = getIntent().getIntExtra("id", -1);

        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        if (startDate != null && !startDate.isEmpty()) {
            setCalenderStart(startDate);
        }

        if (endDate != null && !endDate.isEmpty()) {
            setCalenderEnd(endDate);
        }

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());




        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);



        repository = new Repository(getApplication());
        setStartDatePicker();
        setEndDatePicker();
        setExcursionRecyclerView();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vacation_Details.this, Excursion_Details.class);
                intent.putExtra("vacayID", vacationID);
                startActivity(intent);
            }
        });
    }


    private void setStartDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        vacayStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalenderStart.set(Calendar.YEAR, year);
                myCalenderStart.set(Calendar.MONTH, monthOfYear);
                myCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };



        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Vacation_Details.this, vacayStartDate, myCalenderStart
                        .get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH),
                        myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setEndDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd/yy", Locale.US);

        vacayEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalenderEnd.set(Calendar.YEAR, year);
                myCalenderEnd.set(Calendar.MONTH, monthOfYear);
                myCalenderEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Vacation_Details.this, vacayEndDate,
                        myCalenderEnd.get(Calendar.YEAR), myCalenderEnd.get(Calendar.MONTH),
                        myCalenderEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public String getStartDate() {
        return getStartDate();
    }

    public String getEndDate() {
        return getEndDate();
    }

    private void updateLabelStart() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        editStartDate.setText(sdf.format(myCalenderStart.getTime()));
    }


    private void updateLabelEnd() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        editEndDate.setText(sdf.format(myCalenderEnd.getTime()));
    }

    private void setExcursionRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }
    private boolean verifyDates(){
        return !myCalenderStart.after(myCalenderEnd);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }


        if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editName.getText().toString() + " is at " + editHotel.getText().toString() + " starting on " + editStartDate.getText().toString() + " and ends on " + editEndDate.getText().toString() + " Price : $" + editPrice.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation: " + editName.getText().toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "null");
            startActivity(shareIntent);
            return true;
        }


        if (item.getItemId() == R.id.vacationsave) {
            if (!verifyDates()) {
                Toast.makeText(Vacation_Details.this, "Please select valid dates. Start date must be after End date, End date must be after Start date", Toast.LENGTH_LONG).show();
                return true;
            }
            VacationEntity vacationEntity;
            if (vacationID == -1) {
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else

                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacationEntity = new VacationEntity(vacationID, editName.getText().toString(), editHotel.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(vacationEntity);
                currentVacation = vacationEntity;
                Toast.makeText(Vacation_Details.this, currentVacation.getVacationName() + " was added, Dates are saved", Toast.LENGTH_LONG).show();

            } else {

                try {

                    vacationEntity = new VacationEntity(vacationID, editName.getText().toString(), editHotel.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(vacationEntity);
                    currentVacation = vacationEntity;
                    Toast.makeText(Vacation_Details.this, currentVacation.getVacationName() + " was updated, Dates are saved", Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                }
                Intent intent = new Intent(Vacation_Details.this, Excursion_Details.class);
                getIntent().putExtra("vacationStartDate", myCalenderStart.getTimeInMillis());
                intent.putExtra("vacationEndDate", myCalenderEnd.getTimeInMillis());
                intent.putExtra("vacayID", vacationID);
                startActivity(intent);

            }
            return true;
        }
        if (item.getItemId() == R.id.vacationdelete) {
            for (VacationEntity vacay : repository.getAllVacations()) {
                if (vacay.getVacationID() == vacationID) currentVacation = vacay;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(Vacation_Details.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Vacation_Details.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }


        // starting vacation notification
        if (item.getItemId() == R.id.startNotify) {

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;

            try {
                myDate = sdf.parse(editStartDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (myDate != null) {

                Long trigger = myDate.getTime();

                //broadcast receiver sends the notification
                Intent intent = new Intent(Vacation_Details.this, MyReceiver.class); //intent to send
//                intent.setAction("start notify");
                intent.putExtra("key", editName.getText().toString() + " starts today!"); //notification message
                PendingIntent sender = PendingIntent.getBroadcast(Vacation_Details.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE); //increments from 0 for the whole app every alert
                //alarm service wakes up the app to send the notification
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            }
        }

        // ending vacation notification
        if (item.getItemId() == R.id.endNotify) {

            Date myDate = null;

            try {
                myDate = sdf.parse(editEndDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (myDate != null) {

                Long trigger = myDate.getTime();

                Intent intent = new Intent(Vacation_Details.this, MyReceiver.class); //intent to send
//                intent.setAction("end notify");
                intent.putExtra("key", editName.getText().toString() + " ends today!");
                PendingIntent sender = PendingIntent.getBroadcast(Vacation_Details.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            }
        }

        if (item.getItemId() == android.R.id.home) {
                this.finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setCalenderStart(String startDate) {
        try {
            Date date = sdf.parse(startDate);
            myCalenderStart.setTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCalenderEnd(String endDate) {
        try {
            Date date = sdf.parse(endDate);
            myCalenderEnd.setTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onResume () {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }
}








//
//    private void scheduleVacationMessage() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//
//        scheduleMessage(alarmManager, myCalenderStart.getTimeInMillis(),  "Your vacation is starting today!");
//
//        scheduleMessage(alarmManager, myCalenderEnd.getTimeInMillis(), "Your vacation is ending today!");
//
//        Toast.makeText(this, "Vacation Notification Set", Toast.LENGTH_SHORT).show();
//        this.finish();
//
//    }
//
//    private void scheduleMessage(AlarmManager alarmManager, long triggerMillis, String message) {
//        Intent intent = new Intent(this, MyReceiver.class);
//        intent.putExtra("message", message); // Use the key "message"
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerMillis, pendingIntent);
//
//    }





/*
 if (item.getItemId()==R.id.notifyVacay) {
            String dateFromScreen = vacationEndDate;
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;

            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = myDate.getTime();

            //broadcast receiver sends the notification
            Intent intent = new Intent(Vacation_Details.this, MyReceiver.class); //intent to send
            intent.setAction("end action");
            intent.putExtra("end key", vacationName + " ends today!"); //notification message
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE); //increments from 0 for the whole app every alert
            //alarm service wakes up the app to send the notification
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish(); //go back to parent activity
            return true;
        }
        return true;
    }


 */