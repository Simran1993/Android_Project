package algonquin.cst2335.myapplication;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
/**
 * ViewModel for managing UI-related data in a lifecycle-conscious way. This class abstracts
 * the data source from the UI and allows data to survive configuration changes such as screen rotations.
 * <p>
 * Specifically, this ViewModel handles database operations related to {@link Lookup} entities,
 * leveraging the {@link LookupDAO} for data access and manipulation.
 */
public class Model extends AndroidViewModel {
    /**
     * Data Access Object (DAO) for {@link Lookup} entities.
     */
    private final LookupDAO lookupDAO;
    /**
     * Live data list of all {@link Lookup} entries in the database.
     */
    private final LiveData<List<Lookup>> allLookups;
    /**
     * Constructs the ViewModel with a reference to the application and initializes
     * the {@link LookupDAO} and LiveData list of all lookups.
     *
     * @param application The application context, used to initialize the database instance.
     */

    public Model(@NonNull Application application) {
        super(application);
        Database database = Database.getInstance(application);
        lookupDAO = database.lookupDAO();
        allLookups = lookupDAO.getAllLookups();
    }
    /**
     * Inserts a {@link Lookup} entry into the database asynchronously.
     *
     * @param lookup The {@link Lookup} entity to be inserted.
     */
    public void insert(Lookup lookup) {
        Database.databaseWriteExecutor.execute(() -> lookupDAO.insert(lookup));
    }
    /**
     * Retrieves a LiveData list of all {@link Lookup} entries from the database.
     * LiveData observes changes to the data source and notifies the observer when the data changes.
     *
     * @return A LiveData list of all {@link Lookup} entries.
     */
    public LiveData<List<Lookup>> getAllLookups() {
        return allLookups;
    }
    /**
     * Clears all {@link Lookup} entries from the database asynchronously.
     */
    public void clearAllLookups() {
        Database.databaseWriteExecutor.execute(() -> lookupDAO.deleteAll());
    }

}
