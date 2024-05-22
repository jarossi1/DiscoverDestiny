package dao;

// Importing Room annotations classes
// An interface serves as a contract that defines many method signatures.

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;                                  // Importing List

import entities.Excursion;                              // Importing Excursion entities

@Dao                                                    // Defining an interface as a Data Access Object. DAO for Room
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     // (@Insert) Insert an Excursion object into Room, if it already is there ignore it
    void insert(Excursion excursion);

    @Update                                             // (@Update) Update a current Excursion object in the database.
    void update(Excursion excursion);

    @Delete                                             // (@Delete) Delete an Excursion object from database.
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")                         // (@Query) to get all Excursion objects, ORDERED BY excursionID in ascending order
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID=:vaca ORDER BY excursionID ASC")  // (@Query) to get all Excursion objects associated with a specific vacationID, ORDERED BY excursionID in ascending order.
    List<Excursion> getAssociatedExcursion(int vaca);

}