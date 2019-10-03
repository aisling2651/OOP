/**                                               Feb 2019          
 * MovieDaoInterface
 * 
 * Declares the methods that all MovieDAO types must implement,
 * be they MySql Movie DAOs or Oracle Movie DAOs etc...
 * 
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid 
 * dependencies on the underlying concrete classes (e.g. MySqlMovieDao).
 * 
 * More sophistocated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a 
 * configuration file (that can be changed without altering source code)
 * 
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */
package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;

public interface MovieDaoInterface 
{
    //CREATE -Me
    public boolean insertMovie(Movie m)throws DaoException;
    
    //RAED - Liam and Me
    
    public List<Movie>  findMoviesByRuntimeGreaterThan(String time) throws DaoException;
    public List<Movie>  findMoviesByDirector(String dName) throws DaoException ;
    public List<Movie>  findMoviesByGenre(String in) throws DaoException;
    public List<Movie>  findMoviesByActors(String in) throws DaoException;
    public List<Movie>  findMovieByTitle(String mName) throws DaoException ; //The same as movies like
    public List<Movie>  findMoviesByTitleLike(String mName) throws DaoException;
    public List<Movie>  findMoviesByYear(String time) throws DaoException;
    public List<Movie>  findMovieByID(int mID) throws DaoException;
    public List<Movie>  findMoviesDirectorGenre(String dir, String gen) throws DaoException;
    public List<Movie>  findRandom() throws DaoException;
    
    
    //for junit testing
    public Movie             findAMovieByID(int mID) throws DaoException;
    public Movie             findAMovieByTitle(String mName) throws DaoException ;
    
    //Recomended 
    public List<Movie>  findRecommendDirectorGenre(ArrayList<String> dir, ArrayList<String> gen) throws DaoException;
    public List<Movie>  findMoviesByGenre(ArrayList<String> in) throws DaoException;
    public List<Movie>  findMoviesByActors(ArrayList<String> in) throws DaoException;
    
    
    
    //Update -Me
    public boolean updateMovie(String[] args) throws DaoException;
    public void updateMovieRuntimeByID(int mID, String rTime) throws DaoException;
    public void updateMovieActors(int movieID, String in)throws DaoException;
    public void updateMovieGenre(int movieID, String in)throws DaoException;
    
    //DELETE -Liam
    public void deleteMovieByID(int mID) throws DaoException;
    
    
    
   
    
    
    
}
