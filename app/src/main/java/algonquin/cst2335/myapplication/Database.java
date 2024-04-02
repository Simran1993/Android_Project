package algonquin.cst2335.myapplication;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Lookup.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract LookupDAO lookupDAO();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    "histrory"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}

