/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author aisli
 */
public class MovieWatched
{
    private int userID;
    private int movieID;
    private Timestamp time;

    public MovieWatched()
    {
        userID= 0;
        movieID =0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        time = timestamp;
    }

    public int getUserID()
    {
        return userID;
    }

    public int getMovieID()
    {
        return movieID;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public void setMovieID(int movieID)
    {
        this.movieID = movieID;
    }

    public void setTime(Timestamp time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "MovieWatched{" + "userID=" + userID + ", movieID=" + movieID + ", time=" + time + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + this.userID;
        hash = 23 * hash + this.movieID;
        hash = 23 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final MovieWatched other = (MovieWatched) obj;
        if (this.userID != other.userID)
        {
            return false;
        }
        if (this.movieID != other.movieID)
        {
            return false;
        }
        if (!Objects.equals(this.time, other.time))
        {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
}
