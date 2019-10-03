/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;
import DTOs.Movie;
import Exceptions.DaoException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 *
 * @author aisli
 */
public class Client
{

    public static void main(String[] args) throws DaoException
    {
        Client client = new Client();
        client.clientRun();
        //client.start();

    }

    public static int printMainMenu()
    {
        boolean correct = false;
        int opt = 0;
        while (!correct)
        {
            Scanner in = new Scanner(System.in);
            System.out.println("*****       Main Menu      *****");
            System.out.println("Search through movies       - 1");
            System.out.println("Recomend movies to you      - 2");
            System.out.println("Insert new movies           - 3");
            System.out.println("Update a Movie              - 4");
            System.out.println("Watch a Movie               - 5");
            System.out.println("Delete a Movie              - 6");
            System.out.println("Exit                        - 7");

            if (in.hasNextInt())
            {

                opt = in.nextInt();
                if (0 < opt && 8 > opt)
                {
                    correct = true;
                }

            }

        }
        return opt;
    }

    public void clientRun() throws DaoException
    {
        try
        {

            Scanner in = new Scanner(System.in);
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            Scanner socketReader = new Scanner(socket.getInputStream());
//            ArrayList<Object> utils = new ArrayList<>();
//            utils.add(socket);
//            utils.add(os);
//            utils.add(socketWriter);
//            utils.add(socketReader);

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client: This Client is running and has connected to the server");

            String response;
            boolean running = true;
            while (running)
            {
                response = mainMenu(socket);
                JsonObject Json = createJsonObject(response);
                if (checkJson(Json))
                {
                    ArrayList<Movie> results = createMovies(Json);
                    displayMovie(results);
                }
                else
                {
                    System.out.println("Couldn't find any result");
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
    }

    // The main menu to get functions the client has asked for
    public static String mainMenu(Socket socket)
    {
        String response;
        int option = printMainMenu();
        int[] array = new int[3];
        array[0] = option;
        switch (array[0])
        {
            case 1:
            {
                array[1] = searchType();
                String argument = getMainFunction(array);
                argument += getArgument();
                return callServer(socket, argument);
                // need to add a second argument where it passes the function protocol into the search.
                //return searches(socket);
            }
            case 2:
            {
                array[2] = recommendType();
                String argument = getMainFunction(array);
                return recommend(socket, argument);
            }
            case 3:
            {
                return insertMovie(socket);
            }
            case 4:
            {
                return updateMovie(socket);
            }
            case 5:
            {
                return watchMovie(socket);
            }
            case 6:
            {
                return deleteMovie(socket);
            }
            case 7:
            {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default:
                return "Error";

        }
    }

    /**
     * Print menu of the Search options the client can choose
     */
    public static int searchType()
    {
        int ans = 0;
        Scanner in = new Scanner(System.in);
        boolean correct = false;

        while (!correct)
        {
            System.out.println("What filter would you like to use:");
            System.out.println("Title                   - 1");
            System.out.println("Director                - 2");
            System.out.println("Film length (minutes)   - 3");
            System.out.println("Year released           - 4");

            if (in.hasNextInt())
            {
                ans = in.nextInt();
                if (ans > 0 && ans < 5)
                {
                    correct = true;
                }
            }
            else
            {
                System.out.println("Error\n Please enter a number between 1 and 4");
            }
        }
        return ans;
    }

    /**
     * Not implemented Just a print menu of the recommend system that will be
     * added
     */
    public static int recommendType()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("What filter would you like to use:");
        System.out.println("Recommend by genre's watched         - 1");
        System.out.println("Recommend by genre and director      - 2");
        System.out.println("Recommend a random movie             - 3");
        return in.nextInt();
    }

    /**
     *
     * getMainFunction passes the functions to be given to the server. Gets an
     * integer array
     *
     */
    public static String getMainFunction(int[] option)
    {
        switch (option[0])
        {
            case 1:
            {
                return getSearchFunction(option[1]);  //write command to socket
            }
            case 2:
            {
                return getRecommendFunction(option[2]);  //write command to socket
            }
            case 3:
            {
                return "ADDMOVIE,";
            }
            case 4:
            {
                return "FINDBYYEAR,";
            }
            default:
                return "This";
        }
    }

    /**
     * getSearchFunction passes the function to be used in the server.
     */
    public static String getSearchFunction(int option)
    {
        switch (option)
        {
            case 1:
            {
                //request to server
                return "FINDBYTITLE,";  //write command to socket
            }
            case 2:
            {
                return "FINDBYDIRECTOR,";
            }
            case 3:
            {
                return "FINDBYRUNTIMEGREATERTHAN,";
            }
            case 4:
            {
                return "FINDBYYEAR,";
            }
            default:
                return "This";
        }
    }

    /**
     * getRecommendFunction passes the function to be used in the server.
     */
    public static String getRecommendFunction(int option)
    {
        switch (option)
        {
            case 1:
            {
                //request to server
                return "RECOMMENDBYGENRE,";  //write command to socket
            }
            case 2:
            {
                return "RECOMMENDBYGENREDIR,";
            }
            case 3:
            {
                return "RECOMMENDRAND,";
            }
            default:
                return "This";
        }
    }

    /**
     * recommend passes the function to be used in the server.
     */
    public static String recommend(Socket socket, String argument)
    {
        try
        {
            Scanner in = new Scanner(System.in);
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            Scanner socketReader = new Scanner(socket.getInputStream());

            if (!(argument.startsWith("RECOMMENDRAND,")))
            {
                System.out.println("Please enter your user id:");
                argument += in.nextLine();
            }

            socketWriter.println(argument);

            return socketReader.nextLine(); // receives result as string in Json format
            // boolean matches;
            //matches = response.matches("/d{1,}/");
            // Returns an array of movies
        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }

    }

    /**
     *
     * getArgument is just used to get the users input for a search. Returns the
     * users input.
     */
    public static String getArgument()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your search:");
        return in.nextLine();
    }

    /**
     *
     * callServer sends the functions to the server and returns the Json string.
     *
     */
    public static String callServer(Socket socket, String protocol)
    {

        try
        {
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers

            Scanner socketReader = new Scanner(socket.getInputStream());

            socketWriter.println(protocol);

            String response = socketReader.nextLine();
            if (response == null)
            {
                return "Nothing came Back";
            }
            return response;
        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }
    }

    /**
     *
     * insertMovie Takes in the socket being used to connect to the server.
     * Inserts the movie and gets a json string back this can be use to see if
     * it was successful or failed.
     *
     */
    public static String insertMovie(Socket socket)
    {
        try
        {
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers

            Scanner socketReader = new Scanner(socket.getInputStream());

            Scanner in = new Scanner(System.in);
            boolean added = false;
            System.out.println("Please enter a movie title:");
            String title = (in.nextLine());
            System.out.println("Please enter genre spearated by a comma: ");
            ArrayList<String> genre = (stringSplit(in.nextLine()));
            System.out.println("Please enter director name: ");
            String director = (in.nextLine());
            System.out.println("Please enter run time: ");
            String rTime = (in.nextLine());
            System.out.println("Please enter year: ");
            String year = (in.nextLine());
            System.out.println("Please enter list of starring actors spearated by a comma: ");
            ArrayList<String> starring = (stringSplit(in.nextLine()));

            String genre2 = "";
            for (int i = 0; i < genre.size(); i++)
            {
                genre2 = genre2 + genre.get(i);
                if (i != genre.size() - 1)
                {
                    genre2 = genre2 + ", ";
                }
            }

            String starring2 = "";
            for (int i = 0; i < starring.size(); i++)
            {
                starring2 = starring2 + starring.get(i);
                if (i != starring.size() - 1)
                {
                    starring2 = starring2 + ", ";
                }
            }
            String returnString = "ADDMOVIE~" + title + "~" + genre2 + "~" + director + "~" + rTime + "~" + year + "~" + starring2;
            //returnString = "ADDMOVIE";
            socketWriter.println(returnString); //write command to socket

            //response from server
            String response = socketReader.nextLine(); // receives result as string in Json format

            return response;
        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }
    }

    /**
     *
     * updateMovie gets the movie object by id and then the user can alter each
     * of the entries and then update the movie. The movie is returned then in a
     * Json string which can then be transformed back to display the movie.
     *
     */
    public static String updateMovie(Socket socket)
    {

        try
        {
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers

            Scanner socketReader = new Scanner(socket.getInputStream());
            Scanner in = new Scanner(System.in);

            System.out.println("Please enter the id of the movie you would like to update");
            String id = in.nextLine();
            socketWriter.println("FINDBYID" + "," + id);
            String response = socketReader.nextLine(); // receives result as string in Json format

            JsonObject obj = createJsonObject(response);
            if (checkJson(obj))
            {
                ArrayList<Movie> update = createMovies(obj);
                System.out.println("What would you like to update");
                String function = updateMenu();
                socketWriter.println(updateFunctions(function, update.get(0)));
                String movieCheck = socketReader.nextLine();
                return movieCheck;
            }
            else
            {
                return response;
            }
        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }

    }

    public static String updateMenu()
    {
        while(true)
        {
            Scanner in = new Scanner(System.in);
            System.out.print("\nRuntime     - 1"
                    + "\nGenres      - 2"
                    + "\nActors      - 3"
                    + "\nFilm Length - 4\n");
            if(in.hasNextInt())
            {
                int ans       = in.nextInt();
                if(ans>0 && ans <5)
                {
                    return ans+"";
                }
            }
        }
    }

    public static String updateFunctions(String function, Movie m)
    {
        Scanner in = new Scanner(System.in);
        switch (function)
        {
            case "1":
            {
                System.out.println("Enter new runtime");
                String runtime = in.nextLine();
                m.setRuntime(runtime);
                break;
            }
            case "2":
            {
                System.out.println("Enter new genres seperated by commas (,)");
                String genres = in.nextLine();
                m.setGenre(stringSplit(genres));
                break;
            }
            case "3":
            {
                System.out.println("Enter new actors seperated by commas (,)");
                String starring = in.nextLine();
                m.setStarring(stringSplit(starring));
                break;
            }
            case "4":
            {
                System.out.println("Enter new director's name");
                String director = in.nextLine();
                m.setDirector(director);
                break;
            }
        }
        return "update~" + m.getId() + "~" + m.getRuntime() + "~" + m.getGenre() + "~" + m.getStarring() + "~" + m.getDirector();
    }

    public static String deleteMovie(Socket socket)
    {
        boolean deleted = false;
        try
        {
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            Scanner socketReader = new Scanner(socket.getInputStream());
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter a movie id:");
            String movieid = (in.nextLine());

            String returnString = "DELETEBYID," + movieid;
            socketWriter.println(returnString); //write command to socket
            //response from server
            String response = socketReader.nextLine(); // receives result as string in Json format
            JsonObject obj = createJsonObject(response);
            return response;

        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }

    }

    public static String watchMovie(Socket socket)
    {
        OutputStream os;
        try
        {
            os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers

            Scanner socketReader = new Scanner(socket.getInputStream());

            Scanner in = new Scanner(System.in);
            boolean added = false;
            System.out.println("Please enter a user id:");
            String userid = (in.nextLine());
            System.out.println("Please enter a movie id:");
            String movieid = (in.nextLine());

            String returnString = "WATCHMOVIE," + userid + "," + movieid;
            socketWriter.println(returnString); //write command to socket

            String response = socketReader.nextLine(); // receives result as string in Json format
            
            return response;

        }
        catch (IOException e)
        {
            return "Client message: IOException: " + e;
        }

    }

    public static ArrayList<String> stringSplit(String string)
    {
        String[] array = string.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        return list;

    }

    public static JsonObject createJsonObject(String jsonString)
    {
        StringReader r = new StringReader(jsonString);
        JsonReader r2 = Json.createReader(r);
        JsonObject jsonObj = r2.readObject();
        return jsonObj;
    }

    public static boolean checkJson(JsonObject obj)
    {
        String test = obj.getString("Message");
        if (!(test.contains("Success")))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static String createJsonString(Movie m)
    {

        // data values in variables ( normally retrieved from result set)
        int id = m.getId();
        String title = m.getTitle();
        String dir = m.getDirector();
        String runTime = m.getRuntime();
        String year = m.getYear();
        String genre = "[";

        for (int i = 0; i < m.getGenre().size(); i++)
        {
            genre = genre + "\"" + m.getGenre().get(i) + "\"";
            if (i != m.getGenre().size() - 1)
            {
                genre = genre + ",";
            }
        }

        genre = genre + "]";
        String starring = "[";

        for (int i = 0; i < m.getStarring().size(); i++)
        {
            starring = starring + "\"" + m.getStarring().get(i) + "\"";
            if (i != m.getStarring().size() - 1)
            {
                starring = starring + ",";
            }

        }

        starring = starring + "]";

//          Now to build the JSON string from the data above
        // note the escape characters ->  we enter \" to produce "
        String json = "{"
                + "\"ID\":\"" + id + "\","
                + "\"Title\":\"" + title + "\","
                + "\"Genre\":" + genre + ","
                + "\"Director\":\"" + dir + "\","
                + "\"Runtime\":\"" + runTime + "\","
                + "\"Year\":\"" + year + "\","
                + "\"Starring\":" + starring + "}";

        return json;

    }

    public static ArrayList<Movie> createMovies(JsonObject obj)
    {
        JsonArray movies = obj.getJsonArray("Movies");
        ArrayList<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++)
        {
            movieList.add(createMovie(movies.getJsonObject(i)));
        }

        return movieList;

    }

    public static Movie createMovie(JsonObject obj)
    {
        String idString = (obj.getString("ID"));
        int id = Integer.parseInt(idString);
        String title = (obj.getString("Title"));
        String dir = (obj.getString("Director"));
        String time = (obj.getString("Runtime"));
        String year = (obj.getString("Year"));
        ArrayList<String> genre = returnArrayList(obj, "Genre");
        ArrayList<String> starring = returnArrayList(obj, "Starring");
        Movie m = new Movie(id, title, genre, dir, time, year, starring);
        return m;
    }

    //Returns the an ArrayList from the JsonArray linked with the name/key
    public static ArrayList<String> returnArrayList(JsonObject obj, String name)
    {
        ArrayList<String> listout = new ArrayList<>();
        JsonArray listin = obj.getJsonArray(name);

        for (int j = 0; j < listin.size(); j++)
        {
            listout.add(listin.getString(j));
        }
        return listout;
    }
    
    public static void displayMovie(Movie movie)
    {
        System.out.println("\nTitle       : "+movie.getTitle()+"("+movie.getYear()+")");
        System.out.println("Film length : "+movie.getRuntime());
        System.out.println("Director    : "+movie.getDirector()+"\n");
    }
    public static void displayMovie(ArrayList<Movie> movies)
    {
        for(Movie m: movies){
            displayMovie(m);
            
        }
    }
    

}
