package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.ExcursionDAO;
import dao.VacationDAO;
import entities.Excursion;
import entities.VacationEntity;



// The @Database handles the entities to include in the database and the database version.
@Database(entities = {VacationEntity.class, Excursion.class}, version = 1,exportSchema = false)


// The VacationDatabaseBuilder class uses the RoomDatabase for storing the vacation and excursion data
public abstract class VacationDatabaseBuilder extends RoomDatabase {


    //abstract methods to allow access to interfaces, the subclasses must include this method to provide an instance
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();


    //This makes sure that only one instance of the database is created.
    private static volatile VacationDatabaseBuilder INSTANCE;



    // This method below ensures that there is only one database created
    // If there is an existing database it will be used, if not a new one is built
    static VacationDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class,"MyVacationDatabase2.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
