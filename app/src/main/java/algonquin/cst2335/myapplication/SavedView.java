/**
 * The {@code SavedView} class extends {@link AppCompatActivity} and is responsible for displaying
 * the saved lookups in a RecyclerView. It allows the user to view their saved data and provides
 * an option to delete all saved lookups.
 *
 * <p>This class initializes the UI components, sets up the ViewModel for data observation,
 * and handles the deletion of all saved lookups through a button click.</p>
 */
package algonquin.cst2335.myapplication;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedView extends AppCompatActivity {

    private Model viewModel;
    private Adapter adapter;
    /**
     * Initializes the activity, sets up the RecyclerView with its adapter and layout manager,
     * and observes the lookups data. It also initializes the delete button and its click listener
     * to allow data deletion.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved);


        RecyclerView RecyclerView = findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        RecyclerView.setAdapter(adapter);


        viewModel = new ViewModelProvider(this).get(Model.class);
        // Observes the all lookups LiveData from the ViewModel and updates the adapter.
        viewModel.getAllLookups().observe(this, lookups -> {

            adapter.setLookups(lookups);
        });


        Button DELETE = findViewById(R.id.DELETE);
        // Sets up the click listener for the delete button to clear all lookups.
        DELETE.setOnClickListener(v -> deletealldata());
    }
    /**
     * Calls the {@code clearAllLookups} method from the ViewModel to delete all saved lookups data.
     */
    private void deletealldata() {
        viewModel.clearAllLookups();
    }

}