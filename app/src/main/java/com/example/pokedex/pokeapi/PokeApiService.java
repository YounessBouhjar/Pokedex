package com.example.pokedex.pokeapi;


import com.example.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon/?limit=154")
    Call<PokemonResponse> ListPokemon();
    //@Query("limit") int limit, @Query("offset") int offset

}
