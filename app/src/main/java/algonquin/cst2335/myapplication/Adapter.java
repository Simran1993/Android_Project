/**
 * Adapter for a RecyclerView to display a list of {@link Lookup} objects.
 * This adapter manages the dataset of lookup entries, allowing for dynamic updates and customization
 * of each item's presentation within a RecyclerView.
 */
package algonquin.cst2335.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<View> {
    /**
     * The list of {@link Lookup} objects to be displayed by the RecyclerView.
     */
    private List<Lookup> lookupList = new ArrayList<>();
    /**
     * Called when RecyclerView needs a new {@link LookupViewHolder} of the given type to represent
     * an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link LookupViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new View(view);
    }
    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link LookupViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder The {@link LookupViewHolder} which should be updated to represent the
     *               contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull View holder, int position) {
        Lookup lookup = lookupList.get(position);
        holder.bind(lookup);
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter's data set.
     */
    @Override
    public int getItemCount() {
        return lookupList.size();
    }
    /**
     * Updates the data set used by the adapter and refreshes the view to display the new data.
     *
     * @param lookups The new list of {@link Lookup} objects to be displayed.
     */
    public void setLookups(List<Lookup> lookups) {
        this.lookupList = lookups;
        notifyDataSetChanged();
    }
}
