package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class VacationEntity {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;

    private String hotelName;
    private double price;

    private String vacationStart;
    private String vacationEnd;



    public VacationEntity(int vacationID, String vacationName, String hotelName, double price, String vacationStart, String vacationEnd) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotelName = hotelName;
        this.price = price;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
    }

    public int getVacationID() {return vacationID;}
    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }


    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }
    public String getHotelName() {return hotelName;}
    public void setHotelName(String hotelName) {this.hotelName = hotelName;}
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getVacationStart(){return vacationStart;}
    public void setVacationStart(String vacationStart){this.vacationStart = vacationStart;}
    public String getVacationEnd(){return vacationEnd;}

    public void setVacationEnd(String vacationEnd){this.vacationEnd = vacationEnd;}


}

