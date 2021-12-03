package com.example.pokedex;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonResponse;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.GET;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> implements Filterable{

    private ArrayList<Pokemon> dataset;
    private Context context;
    private ArrayList<Pokemon> PokemonListAll;
    public PokemonListAdapter(Context context){
        dataset = new ArrayList<>();
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getNumber()+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void Add(ArrayList<Pokemon> listPokemon){
        dataset.addAll(listPokemon);
        this.PokemonListAll = new ArrayList<>(listPokemon);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Pokemon> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(PokemonListAll);
            } else {
                for (Pokemon pokemon : PokemonListAll) {
                    if (pokemon.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(pokemon);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataset.clear();
            dataset.addAll((Collection<? extends Pokemon>) filterResults.values);
            notifyDataSetChanged();
        }
    };
        public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView){
            super(itemView);
            fotoImageView = itemView.findViewById(R.id.fotoImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
        }
    }
}
