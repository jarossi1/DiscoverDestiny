package database;    // Package declared

import android.app.Application;

import entities.Excursion;
import dao.ExcursionDAO;
import dao.VacationDAO;
import entities.VacationEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Defined Repository class which manages data operations and handles threading, (This works as a go-between the DAO and the application)
public class Repository {


    // DAO objects: declared private to encapsulate the data access logic within the Repository class
    // This is to control how the database is accessed and changed, making the code easy to maintain and secure
    private ExcursionDAO mExcursionDAO;                         // DAO object for Excursion
    private VacationDAO mVacationDAO;                           // DAO object for Vacation


    private List<VacationEntity> mAllVacations;                 // List of all vacations from database
    private List<Excursion> mAllExcursions;                     // List of all Excursions from database
    private static int NUMBER_OF_THREADS = 4;                   // Number of threads to be used in the thread pool



    // Creating an ExecutorService with a fixed thread pool
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);



    /*
    (public Repository constructor) constructor that initializes the DAOs
    constructor is called when creating an instance of the Repository class
    requires an Application context to initialize the database
     */

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);

    // initialize ExcursionDAO and VacationDAO using the database
    // this is required for the Repository to do data operations linked to excursions and vacations
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }

    public List<VacationEntity> getAllVacations() {
        databaseExecutor.execute(() -> {

            // (mAllVacations) gets all the vacations from the DAO (stores them in memory temporarily)
            mAllVacations = mVacationDAO.getAllVacations();
        });

        // Delay to ensure that the background tasks complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Returns the (temporarily cached list)
        return mAllVacations;
    }


    public void insert(VacationEntity vacationEntity) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacationEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(VacationEntity vacationEntity) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacationEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(VacationEntity vacationEntity) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacationEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public List<Excursion> getAssociatedExcursions(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursion(vacationID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


        public void delete(Excursion excursion){
            databaseExecutor.execute(() -> {
                mExcursionDAO.delete(excursion);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

