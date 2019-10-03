/** Feb 2019
 *
 * Data Access Object (DAO) for Movie table with MySQL-specific code
 * This 'concrete' class implements the 'MovieDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 */
package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface
{
    public List<Movie>  findRandom() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
         List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            //String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE id = ?";
            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies ORDER BY RAND() LIMIT 1;";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
               Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
                
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findAMovieByID() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findAMovieByID() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }
    
     public Movie       findAMovieByID(int mID) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie u = new Movie();
        try
        {
            con = this.getConnection();

            //String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE id = ?";
            String query = "SELECT id,title,director,runtime,year FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, mID);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                //String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                //String starring = rs.getString("starring");

                //String[] genreArray = genre.split(",");
                //ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                //String[] starringArray = starring.split(",");
                //ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                u = new Movie(id, title, dirName, runtime, y);
                
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findAMovieByID() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findAMovieByID() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }
     
      public Movie      findAMovieByTitle(String mName) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie u = new Movie();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE title = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, mName);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                u = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findAMovieByTitle() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findAMovieByTitle() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    public List<Movie>  findMoviesByRuntimeGreaterThan(String time) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> m = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE runtime = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, time);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, director, runtime, y, starringArrayList);
                m.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByRuntimeGreaterThan > ? " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMoviesByRuntimeGreaterThan() " + e.getMessage());
            }
        }
        return m;     // may be empty
    }

    public List<Movie>  findMoviesByDirector(String dName) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE director like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%"+dName+"%");

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByDirector() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    public List<Movie>  findMovieByTitle(String mName) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE title like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ("%"+mName +"%"));
            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByTitle() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    public List<Movie>  findMoviesByYear(String year) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE year = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, year);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByDirector() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    public List<Movie>  findMovieByID(int mID) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, mID);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByDirector() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    public List<Movie>  findMoviesByTitleLike(String mName) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE title like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ("%"+mName + "%"));

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }

    

   

    public List<Movie>  findMoviesByGenre(String in) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE genre like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ("%" + in.trim() + "%"));

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }

    public List<Movie>  findMoviesByActors(String in) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE starring like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ("%" + in.trim() + "%"));

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }

    public List<Movie>  findMoviesDirectorGenre(String dir, String gen) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        try
        {
            con = this.getConnection();

            String query = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE director LIKE ? and genre like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ("%" + dir.trim() + "%"));
            ps.setString(2, ("%" + gen.trim() + "%"));

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }

    public List<Movie>  findMoviesByActors(ArrayList<String> in) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        String stt = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE starring like ?";
        for (int i = 1; i < in.size(); i++)
        {
            stt += (" or ?");
        }
        System.out.println(stt);
        try
        {
            con = this.getConnection();

            String query = stt;
            ps = con.prepareStatement(query);
            for (int i = 0; i < in.size(); i++)
            {
                ps.setString((i + 1), ("%" + in.get(i) + "%"));
            }

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }

    public List<Movie>  findMoviesByGenre(ArrayList<String> in) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        String stt = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE genre like ?";
        for (int i = 1; i < in.size(); i++)
        {
            stt += (" or ?");

        }

        stt += ("LIMIT 5");
        try
        {
            con = this.getConnection();

            String query = stt;
            ps = con.prepareStatement(query);
            for (int i = 0; i < in.size(); i++)
            {
                ps.setString((i + 1), ("%" + in.get(i) + "%"));
            }

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }

    
    
    
    public void         updateMovieActors(int movieID, String starring) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();

            String query = "UPDATE movies SET starring = ? WHERE id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, starring);
            ps.setInt(2, movieID);

            ps.execute();

        }
        catch (SQLException e)
        {
            throw new DaoException("updateMovieActors" + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("updateMovieActors " + e.getMessage());
            }

        }

    }
    
    public void         updateMovieGenre(int movieID, String genre) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();

            String query = "UPDATE movies SET genre = ? WHERE id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, genre);
            ps.setInt(2, movieID);

            ps.execute();

        }
        catch (SQLException e)
        {
            throw new DaoException("updateMovieGenre" + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("updateMovieGenre" + e.getMessage());
            }

        }

    }
    public boolean         updateMovie(String[] args) throws DaoException
    {
        //UPDATE `movies` SET `title`=[value-2],`genre`=[value-3],`director`=[value-4],`runtime`=[value-5],`plot`=[value-6],`rating`=[value-7],`format`=[value-8],`year`=[value-9],`starring`=[value-10],`copies`=[value-11] WHERE 1
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();

            String query = "UPDATE movies SET genre=?, director =?,runtime=?,starring=? WHERE id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, args[1]);
            ps.setString(2, args[2]);
            ps.setString(3, args[3]);
            ps.setString(4, args[4]);
            ps.setInt(5, Integer.parseInt(args[0]));

            int check = ps.executeUpdate();
            if(check==1)
            {
                return true;
            }else{
                return false;
            } 
            
        }
        catch (SQLException e)
        {
            throw new DaoException("updateMovieGenre" + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("updateMovieGenre" + e.getMessage());
            }

        }
    }
     public void        updateMovieRuntimeByID(int mID, String rTime) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie u = null;
        try
        {
            con = this.getConnection();

            String query = "UPDATE movies SET runtime = ? WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, rTime);
            ps.setInt(2, mID);

            ps.execute();

        }
        catch (SQLException e)
        {
            throw new DaoException("updateMovieRuntimeByID " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("updateMovieRuntimeByID() " + e.getMessage());
            }

        }

    }

    public boolean         insertMovie(Movie m) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        Movie u = null;
        try
        {
            con = this.getConnection();

            String query = "INSERT INTO movies (title,genre,director,runtime,plot,rating,format,year,starring,copies) VALUES(?,?,?,?,?,?,?,?,?,?)";

            String genre = "";
            for (int i = 0; i < m.getGenre().size(); i++)
            {
                genre = genre + m.getGenre().get(i);
                if (i != m.getGenre().size() - 1)
                {
                    genre = genre + ", ";
                }
            }

            String starring = "";

            for (int i = 0; i < m.getStarring().size(); i++)
            {
                starring = starring + "" + m.getStarring().get(i) + "";
                if (i != m.getStarring().size() - 1)
                {
                    starring = starring + ", ";
                }
            }

            ps = con.prepareStatement(query);
            String plot = "";
            String rating = "";
            String format = "";
            int copies = 1;

            ps.setString(1, m.getTitle());
            ps.setString(2, genre);
            ps.setString(3, m.getDirector());
            ps.setString(4, m.getRuntime());
            ps.setString(5, plot);
            ps.setString(6, rating);
            ps.setString(7, format);
            ps.setString(8, m.getYear());
            ps.setString(9, starring);
            ps.setInt(10, copies);

            int check =ps.executeUpdate();

            if(check==1)
            {
                return true;
            }else{
                return false;
            }  
        }
        catch (SQLException e)
        {
            
            throw new DaoException("insertMovie " + e.getMessage());
        }

    }
    
    public void         deleteMovieByID(int movieID) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        Movie u = null;
        try
        {
            con = this.getConnection();
            String query = "Delete FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, movieID);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DaoException("deleteMovieByID() " + e.getMessage());
        }

    }
    
    
    public List<Movie>  findRecommendDirectorGenre(ArrayList<String> dir, ArrayList<String> gen) throws DaoException
    {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> u = new ArrayList<>();
        String stt = "SELECT id,title,genre,director,runtime,year,starring FROM movies WHERE director LIKE ? and genre like ?";
        for (int i = 1; i < dir.size(); i++)
        {
            stt += (" or ?");

        }

        stt += ("LIMIT 5");
        try
        {
            con = this.getConnection();

            String query = stt;
            ps = con.prepareStatement(query);
            for (int i = 0; i < dir.size(); i++)
            {
                for (int j = 0; j < gen.size(); i++)
                {
                    ps.setString((i + 1), ("%" + dir.get(i) + "%"));
                    ps.setString((j + 1), ("%" + gen.get(i) + "%"));
                }
            }

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dirName = rs.getString("director");
                String runtime = rs.getString("runtime");
                String y = rs.getString("year");
                String starring = rs.getString("starring");

                String[] genreArray = genre.split(",");
                ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));
                String[] starringArray = starring.split(",");
                ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));
                Movie mo = new Movie(id, title, genreArrayList, dirName, runtime, y, starringArrayList);
                u.add(mo);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMoviesByTitleLike() " + e.getMessage());
        }
        finally
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
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return u;
    }
}
