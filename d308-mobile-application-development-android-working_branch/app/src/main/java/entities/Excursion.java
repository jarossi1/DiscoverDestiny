package entities;

// Import Room annotations for defining database entities
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// this class makes a table in the database, and provides the names for each column inside
@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)      // PrimaryKey, the database will automatically assign a unique ID to each new excursion 1,2,3...
    private int excursionID;
    private String excursionName;
    private double price;
    private int vacationID;
    private String excursionDate;



    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
    public String getExcursionDate(){return excursionDate;}

    public void setExcursionDate(String excursionDate){this.excursionDate = excursionDate;}

    public Excursion(int excursionID, String excursionName, double price, int vacationID, String excursionDate) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.price = price;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }
}
