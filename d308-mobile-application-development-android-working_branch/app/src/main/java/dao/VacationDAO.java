package dao;
// Import Room annotations and classes
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;                // Import List
import entities.VacationEntity;       // Import VacationEntity class

@Dao                                                                        // (@DAO) Defining an interface as a Data Access Object. DAO for Room
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)                         // (@Insert) Insert an VacationEntity object into Room, if it already is there ignore it
    void insert(VacationEntity vacationEntity);

    @Update
    void update(VacationEntity vacationEntity);                             // (@Update) Update a current VacationEntity object in the database.

    @Delete
    void delete(VacationEntity vacationEntity);                             // (@Delete) Delete an VacationEntity object from database.

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID  ASC")              // (@Query) Query to retrieve all VacationEntity objects ordered by vacationID in ascending order
    List<VacationEntity> getAllVacations();
}
