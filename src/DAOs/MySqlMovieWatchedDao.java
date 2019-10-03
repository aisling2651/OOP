package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aisli
 */
public class MySqlMovieWatchedDao extends MySqlDao implements MovieWatchedDaoInterface
{
    
    public void insertMovieWatched(int userID, int movieID) throws DaoException
    {
        
        System.out.println("**"+userID);
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = this.getConnection();

            String query = "INSERT INTO movies_users_watched(userID,movieID,timestamp) VALUES (?,?,?)";
            
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            Timestamp time =  new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(3, time);
            
            

            ps.execute();

        } catch (SQLException e)
        {
            throw new DaoException("insertMovieWatched " + e.getMessage());
        }

    }
    
    //Recomended
    public List<Movie> getRecomended1(int userId) throws DaoException
    {
            MovieDaoInterface IMovieDao = new MySqlMovieDao();
            List<Integer> watchedId     = findMovieWatched(userId);
            List<String> genres         = getMovieGenres(watchedId,IMovieDao);
            List<Movie> recomended      = getRecomendedByGenre((ArrayList<String>) genres, IMovieDao);
            return recomended;
        
    }
    
     public List<Movie> getRecomended2(int userId) throws DaoException
    {
            MovieDaoInterface IMovieDao = new MySqlMovieDao();
            List<Integer> watchedId     = findMovieWatched(userId);
            List<String> genres         = getMovieGenres(watchedId,IMovieDao);
            List<String> directors      = getMovieDirectors(watchedId,IMovieDao);
            List<Movie> recomended      = getRecomendedByDirectorGenre((ArrayList<String>) directors,(ArrayList<String>) genres, IMovieDao);
            return recomended;
        
    }
    
    //Searches utilities
    public List<Integer> findMovieWatched(int userId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT movieID FROM movies_users_watched WHERE userID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            while (rs.next())
            {
                
                int id = rs.getInt("movieID");
                System.out.println(id);
                if(u.add(id)){
                    break;
                }
                    
            }
        } catch (SQLException e)
        {
            throw new DaoException("findMovieWatched() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findMovieWatched() " + e.getMessage());
            }
        }
        return u;
    }
    
    public List<String> getMovieGenres(List<Integer> id, MovieDaoInterface IMovieDao) throws DaoException
    {
        List<String> m = new ArrayList<>();
        for(int i : id)
        {
           List<Movie> mov =IMovieDao.findMovieByID(i);
           for(int j=0; j<mov.size();j++)
           {
               m.addAll(mov.get(j).getGenre());
           }
           
            
        }
        return m;
    }
    public List<String> getMovieDirectors(List<Integer> id, MovieDaoInterface IMovieDao) throws DaoException
    {
        List<String> m = new ArrayList<>();
        for(int i : id)
        {
           List<Movie> mov =IMovieDao.findMovieByID(i);
           for(int j=0; j<mov.size();j++)
           {
               m.add(mov.get(j).getDirector());
           }
           
           
            
        }
        return m;
    }
    
    public List<Movie> getRecomendedByGenre(ArrayList<String> genres, MovieDaoInterface IMovieDao) throws DaoException
    {
        List<Movie> recomended = new ArrayList<>();
        recomended.addAll(IMovieDao.findMoviesByGenre(genres));
        return recomended;
    }
    
    public List<Movie> getRecomendedByDirectorGenre(ArrayList<String> directors,ArrayList<String> genres, MovieDaoInterface IMovieDao) throws DaoException
    {
        List<Movie> recomended = new ArrayList<>();
        recomended.addAll(IMovieDao.findRecommendDirectorGenre(directors,genres));
        return recomended;
    }
    
    
    
}


