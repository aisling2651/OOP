package DTOs;

import java.util.ArrayList;
import java.util.Objects;

/**                                                     Feb 2019 
 * 
 * This POJO (Plain Old Java Object) is called the Data Transfer Object (DTO).
 * (or, alternatively, the Model Object or the Value Object).
 It is used to transfer data between the DAO and the Business Objects.
 Here, it represents a row of data from the Movie database table.
 The DAO fills a Movie object (DTO) with data retrieved from the resultSet 
 and passes the Movie object to the Business Layer. 
 Collections of DTOs( e.g. ArrayList<Movie> ) may also be passed 
 * between the Data Access Layer (DAOs) and the Business Layer objects.
 */

public class Movie
{
    private int id;
    private String title;
    private ArrayList<String> genre;
    private String director;
    private String runtime;
    private String year;
    private ArrayList<String> starring;

    public Movie()
    {
        this.title="";
        this.genre =null;
        this.director = "";
        this.runtime="";
        this.year ="";
        this.starring = null;
        
        
    }

    public Movie(int id, String title, String director, String runtime, String year)
    {
        this.id = id;
        this.title = title;
        this.director = director;
        this.runtime = runtime;
        this.year = year;
    }

    
    
    public void setYear(String year)
    {
        this.year = year;
    }

    

    public void setStarring(ArrayList<String> starring)
    {
        this.starring = starring;
    }

    public ArrayList<String> getStarring()
    {
        return starring;
    }

    public Movie(int id, String title, ArrayList<String> genre, String director, ArrayList<String> starring)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.starring = starring;
    }

    public Movie(int id, String title, ArrayList<String> genre, String director, String runtime, String year, ArrayList<String> starring)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.runtime = runtime;
        this.year = year;
        this.starring = starring;
    }

    public Movie(int id, String title, String director, ArrayList<String> starring)
    {
        this.id = id;
        this.title = title;
        this.director = director;
        this.starring = starring;
    }

    public Movie(int id, String title, ArrayList<String> genre, String director, String runtime, ArrayList<String> starring)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.runtime = runtime;
        this.starring = starring;
    }

    public Movie(int id, String title, ArrayList<String> genre, String director, String runtime)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.runtime = runtime;
    }

    public Movie(String title, ArrayList<String> genre, String director, String runtime, String year, ArrayList<String> starring)
    {
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.runtime = runtime;
        this.year = year;
        this.starring = starring;
    }
    

    public Movie(int id, String title, ArrayList<String> genre, String director)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
    }
    public Movie(int id, String title, String director)
    {
        this.id = id;
        this.title = title;
        this.director = director;
    }
    

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public ArrayList<String> getGenre()
    {
        return genre;
    }

    public String getDirector()
    {
        return director;
    }

    public String getRuntime()
    {
        
        return runtime;
    }

    public String getYear()
    {
        return year;
    }

    @Override
    public String toString()
    {
        return "*Movie* " + "movieid=" + id + ",title=" + title + ",genre=" + genre + ",director=" + director + ",runtime=" + runtime + ",year=" + year + ",starring=" + starring + '\n';
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setGenre(ArrayList<String> genre)
    {
        this.genre = genre;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Objects.hashCode(this.genre);
        hash = 41 * hash + Objects.hashCode(this.director);
        hash = 41 * hash + Objects.hashCode(this.runtime);
        hash = 41 * hash + Objects.hashCode(this.year);
        hash = 41 * hash + Objects.hashCode(this.starring);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Movie other = (Movie) obj;
        if (this.id != other.id)
            return false;
        if (!Objects.equals(this.title, other.title))
            return false;
        if (!Objects.equals(this.director, other.director))
            return false;
        if (!Objects.equals(this.runtime, other.runtime))
            return false;
        if (!Objects.equals(this.year, other.year))
            return false;
        if (!Objects.equals(this.genre, other.genre))
            return false;
        if (!Objects.equals(this.starring, other.starring))
            return false;
        return true;
    }

    

    
}
