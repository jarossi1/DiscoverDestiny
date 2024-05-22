package UI;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_jordan.R;
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

public class Excursion_Details extends AppCompatActivity {

    String name;
    Double price;

    Excursion currentExcursion;
    int excursionID;
    int vacayID;
    private Date startVacay;
    private Date endVacay;
    EditText editName;
    EditText editPrice;

    TextView editDate;
    Repository repository;
    String vacayStart;          // Start date of the vacation as string
    String vacayEnd;            // End date of the vacation as string


    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalenderStart = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Retrieving data from intent extras and updating UI

        repository = new Repository(getApplication());          // this new object can be used to perform all the needed operations that the excursion class needs.
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", 0.0);
        editPrice = findViewById(R.id.excursionPrice);
        editPrice.setText(Double.toString(price));
        excursionID = getIntent().getIntExtra("id", -1);
        vacayID = getIntent().getIntExtra("vacayID", -1);
//        editNote = findViewById(R.id.note);
        editDate = findViewById(R.id.editExcursionDate);
        editDate.setText(getIntent().getStringExtra("excursionDate"));
        getVacationDates();



        startDate = new DatePickerDialog.OnDateSetListener() {

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

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editDate.getText().toString();
                if (info.equals("")) info = "05/01/24";
                try {
                    myCalenderStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(Excursion_Details.this, startDate, myCalenderStart
                        .get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH),
                        myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void getVacationDates() {
        List<VacationEntity> list = repository.getAllVacations();
        for (VacationEntity v : list) {
            if (v.getVacationID() == vacayID) {
                try {
                    vacayStart = v.getVacationStart();
                    vacayEnd = v.getVacationEnd();
                    startVacay = sdf.parse(vacayStart);
                    endVacay = sdf.parse(vacayEnd);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalenderStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }




        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            if (endVacay.before(myCalenderStart.getTime()) || startVacay.after(myCalenderStart.getTime())) {
               String msg = "Excursion date must be between " + vacayStart + " and " + vacayEnd;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return false;
            }
            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacayID, editDate.getText().toString());
                repository.insert(excursion);

                String message = editName.getText().toString() + " added";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            } else {
                try {
                    excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacayID, editDate.getText().toString());
                    repository.update(excursion);
                    String message = editName.getText().toString() + " updated";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }



        if (item.getItemId() == R.id.excursiondelete) {
            Excursion currentExcursion = null;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) {
                    currentExcursion = excursion;
                    break;
                }
            }


            if (currentExcursion != null) {
                repository.delete(currentExcursion);
                String message = currentExcursion.getExcursionName() + " was deleted";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Excursion not found in database", Toast.LENGTH_LONG).show();
            }
            return true;
        }



        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(Excursion_Details.this, MyReceiver.class);
                intent.putExtra("key", editName.getText().toString() + " excursion starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(Excursion_Details.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

