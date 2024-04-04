package algonquin.cst2335.myapplication;
/**
 * DictionaryMainActivity is the main activity class responsible for displaying a dictionary application interface,
 * allowing users to search for word definitions and view saved searches.
 * This activity utilizes a RecyclerView to display the fetched word definitions.
 *
 * @author Harsimranjit Singh
 * @version 1.0
 * @since 2024-04-02
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


public class DictionaryMainActivity extends AppCompatActivity {
    EditText etSearchTerm;

    Button btnSearch, btnViewSaved;
    RecyclerView rvDefinitions;
    DefinitionsAdapter adapter;
    List<Definition> definitionList = new ArrayList<>();

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictonary_activity_main);

        etSearchTerm = findViewById(R.id.etSearchTerm);
        btnSearch = findViewById(R.id.btnSearch);
        rvDefinitions = findViewById(R.id.rvDefinitions);
        btnViewSaved = findViewById(R.id.btnViewSaved);

        rvDefinitions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DefinitionsAdapter(definitionList);
        rvDefinitions.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String searchTerm = getIntent().getStringExtra("searchTerm");
        if (searchTerm != null) {
            etSearchTerm.setText(searchTerm); // Set the search term in EditText
            fetchDefinitions(searchTerm); // Fetch definitions for the selected term
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String term = etSearchTerm.getText().toString();
                fetchDefinitions(term);
                saveSearchTerm(term);

            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DictionaryMainActivity.this, ViewSavedSearchesActivity.class);
                startActivity(intent);
            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DictionaryMainActivity.this, ViewSavedSearchesActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
    /**
     * Fetches word definitions from the API for the given search term.
     *
     * @param term The search term for which definitions are to be fetched.
     */
    private void fetchDefinitions(String term) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + term;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Clear the current definitions
                            definitionList.clear();

                            JSONArray entries = new JSONArray(response);
                            for (int i = 0; i < entries.length(); i++) {
                                JSONObject entry = entries.getJSONObject(i);
                                JSONArray meanings = entry.getJSONArray("meanings");
                                for (int j = 0; j < meanings.length(); j++) {
                                    JSONObject meaning = meanings.getJSONObject(j);
                                    JSONArray definitionsArray = meaning.getJSONArray("definitions");
                                    for (int k = 0; k < definitionsArray.length(); k++) {
                                        JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                        String definitionText = definitionObject.getString("definition");
                                        definitionList.add(new Definition(definitionText));
                                    }
                                }
                            }

                            // Notify the adapter of the data change on the UI thread
                            runOnUiThread(() -> {
                                adapter.notifyDataSetChanged();
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DictionaryMainActivity.this, "Error parsing the definitions", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DictionaryMainActivity.this, "Error fetching the definitions", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }/**
     * Saves the search term and its associated definitions to the database.
     *
     * @param searchTerm The search term to be saved.
     */
    private void saveSearchTerm(String searchTerm) {
        Gson gson = new Gson();
        String definitionsJson = gson.toJson(definitionList); // Convert list of definitions to JSON String

        SearchEntry searchEntry = new SearchEntry();
        searchEntry.searchTerm = searchTerm;
        searchEntry.definitions = definitionsJson;

        AppDatabase db = AppDatabase.getDatabase(this);
        new Thread(() -> db.searchEntryDao().insert(searchEntry)).start(); // Save to database in a background thread

        //Toast.makeText(this, "Search term saved: " + searchTerm, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.help) {
            // Build and show the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Help");
            builder.setMessage("This is the help message. i am here to help.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog when the "OK" button is clicked
                    dialog.dismiss();
                }
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

