/**
 * Name: Nirmal Patel
 * Final Project: Song Search
 * Due Date: 5th April
 */
package algonquin.cst2335.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<SongBeans> songBeansList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SongAdapter(List<SongBeans> songBeansList, OnItemClickListener listener) {
        this.songBeansList = songBeansList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongBeans songBeans = songBeansList.get(position);
        holder.bind(songBeans);
    }

    @Override
    public int getItemCount() {
        return songBeansList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView artistNameTextView;
        private TextView albumNameTextView;
        private TextView durationTextView;
        private ImageView albumCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistNameTextView = itemView.findViewById(R.id.artist_name);
            albumNameTextView = itemView.findViewById(R.id.album_name);
            durationTextView = itemView.findViewById(R.id.song_duration);
            albumCoverImage = itemView.findViewById(R.id.album_cover_image);
            itemView.setOnClickListener(this);



        }

        public void bind(SongBeans songBeans) {
            titleTextView.setText(songBeans.getTitle());
            artistNameTextView.setText(songBeans.getArtistName());
            albumNameTextView.setText(songBeans.getAlbumName());
            durationTextView.setText(songBeans.getFormattedDuration());
            Glide.with(itemView.getContext())
                    .load(songBeans.getCoverUrl())
                    .into(albumCoverImage);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        }
    }


}