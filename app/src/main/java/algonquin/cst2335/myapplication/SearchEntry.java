package algonquin.cst2335.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_entries")
public class SearchEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "search_term")
    public String searchTerm;

    @ColumnInfo(name = "definitions")
    public String definitions; // JSON String of definitions
}


