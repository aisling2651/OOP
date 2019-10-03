/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.Movie;
import DTOs.MovieWatched;
import Exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aisli
 */
public interface MovieWatchedDaoInterface
{
    public void insertMovieWatched(int userID, int movieID) throws DaoException;
    
     //Recomend
    public List<Movie> getRecomended1(int userId) throws DaoException;
    public List<Movie> getRecomended2(int userId) throws DaoException;
    
//Searches
    public List<Integer> findMovieWatched(int userId) throws DaoException;
    public List<String> getMovieGenres(List<Integer> id, MovieDaoInterface IMovieDao) throws DaoException;
    public List<Movie> getRecomendedByGenre(ArrayList<String> genres, MovieDaoInterface IMovieDao) throws DaoException;
    public List<Movie> getRecomendedByDirectorGenre(ArrayList<String> directors,ArrayList<String> genres, MovieDaoInterface IMovieDao) throws DaoException;}

