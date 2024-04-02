package algonquin.cst2335.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewSavedSearchesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SavedSearchesAdapter adapter;
    List<SearchEntry> searchHistory = new ArrayList<>();

    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_searches);

        recyclerView = findViewById(R.id.yourRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        deleteButton = findViewById(R.id.Delete_button);

        adapter = new SavedSearchesAdapter(this, searchHistory, new SavedSearchesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String searchTerm) {
                // Start DictionaryMainActivity with the selected search term
                Intent intent = new Intent(ViewSavedSearchesActivity.this, DictionaryMainActivity.class);
                intent.putExtra("searchTerm", searchTerm);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to delete the search history
                deleteSearchHistory();
            }
        });

        loadSearchHistory();
    }

    private void loadSearchHistory() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        new Thread(() -> {
            List<SearchEntry> entries = db.searchEntryDao().getAllSync();
            runOnUiThread(() -> {
                searchHistory.clear();
                searchHistory.addAll(entries);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void deleteSearchHistory() {
        // Clear the search history list and notify the adapter
        searchHistory.clear();
        adapter.notifyDataSetChanged();

        // Delete search history from the database
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        new Thread(() -> {
            db.searchEntryDao().deleteAll();
        }).start();
    }
}

