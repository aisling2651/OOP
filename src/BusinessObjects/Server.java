/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import DAOs.MovieDaoInterface;
import DAOs.MovieWatchedDaoInterface;
import DAOs.MySqlMovieDao;
import DAOs.MySqlMovieWatchedDao;
import DTOs.Movie;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;
import javax.json.JsonObject;
import java.util.*;
import java.util.Map;

import java.util.logging.FileHandler;
import java.util.logging.Logger;


/**
 *
 * @author aisli
 */
public class Server
{
    
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final Level LOG_LEVEL = Level.INFO;
    private FileHandler logFile = null;
    
    //Log levels 
    //Severe
    //Warning
    //info         <-this and all logs above will be displayed
    //config            
    //fine
    //finer
    //finest
    //

    

    /**
     * Main creates a new instance of the server and starts the server
     *
     * @param args
     */
    public static void main(String[] args)
    {
        LOGGER.setLevel(LOG_LEVEL);
        Server server = new Server();
        server.start();
    }

    /**
     * Start method is creating a new sockets then a new client handler and
     * running a new thread for each client
     */
    public void start()
    {
        try
        {
            logFile = new FileHandler("Server.log", true);//true appends file
        }
        catch(Exception e)
        {   
            e.printStackTrace();
        }
        
        logFile.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(logFile);
        LOGGER.info("Server Starting....");

        MovieDaoInterface IMovieDao = new MySqlMovieDao();
        MovieWatchedDaoInterface IMovieWatchedDao = new MySqlMovieWatchedDao();
        Cache<String, String> cachemap = new Cache<>();
        
        try
        {
            
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server Message: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection
                
                LOGGER.info("A Client has connected from: " + socket.getInetAddress());
                // and open a new socket to communicate with the client
                clientNumber++;
                
                 LOGGER.info("ClientHandler started in thread for client: " + clientNumber);
                
                

                System.out.println("Server: Client " + clientNumber + " has connected.");
                

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, IMovieDao, IMovieWatchedDao, clientNumber, cachemap)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
                
            }
        }
        catch (IOException e)
        {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    /**
     * Each ClientHandler communicates with one Client
     */
    public class ClientHandler implements Runnable
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        MovieDaoInterface IMovieDao = new MySqlMovieDao();
        MovieWatchedDaoInterface IMovieWatchedDao = new MySqlMovieWatchedDao();
        Cache cachemap;

        /**
         *
         * @param clientSocket
         * @param IMovieDao
         * @param IMovieWatchedDao
         * @param clientNumber
         * @param cachemap
         */
        public ClientHandler(Socket clientSocket, MovieDaoInterface IMovieDao, MovieWatchedDaoInterface IMovieWatchedDao, int clientNumber, Cache cachemap)
        {
            try
            {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 
                this.cachemap = cachemap;

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        public void run()
        {
            String message;
            try
            {

                //waiting on client command and when it is null the while loop will end 
                while ((message = socketReader.readLine()) != null)
                {
                    
                    LOGGER.info("Command received from client: " + message);
                    //output client number and the command entered by that client
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    //checks if command exists in cache
                    if (cachemap.checkKey(message))
                    {
                        //outputs values associated with that command
                        socketWriter.println(cachemap.getValue(message));
                    }
                    //if command does not exist if checks the method to call based on the start of the command
                    else if (message.startsWith("FINDBYTITLE"))
                    {
                        String[] protocol = message.split(","); //splits the command to get the title parameter
                        String title = protocol[1];
                        List<Movie> movies = IMovieDao.findMovieByTitle(title); //calls method in DAO and passes in title 
                        if (!(movies.isEmpty()))//checks if returned list is empty
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("FINDBYID"))
                    {
                        String[] protocol = message.split(","); //splits the command to get the ID parameter
                        String id = protocol[1];
                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //calls method in DAO and passes in ID
                        System.out.println(movies);
                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                    }
                    else if (message.startsWith("FINDMOVIESBYTITLELIKE"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the title parameter
                        String title = protocol[1];
                        List<Movie> movies = IMovieDao.findMoviesByTitleLike(title); //calls method in DAO and passes in title

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("FINDBYRUNTIMEGREATERTHAN"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the time parameter
                        String time = protocol[1];
                        List<Movie> movies = IMovieDao.findMoviesByRuntimeGreaterThan(time);//calls method in DAO and passes in time

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("FINDBYDIRECTOR"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the director parameter
                        String director = protocol[1];
                        List<Movie> movies = IMovieDao.findMoviesByDirector(director); //calls method in DAO and passes in director

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("FINDBYYEAR"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the year parameter
                        String year = protocol[1];
                        List<Movie> movies = IMovieDao.findMoviesByYear(year); //calls method in DAO and passes in year

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("DELETEBYID"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the id parameter
                        String id = protocol[1];
                        
                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(id));//calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            IMovieDao.deleteMovieByID(Integer.parseInt(id));//delete movie with id
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            cachemap.addToCache(message, result); //Adds command and string to cache map
                            socketWriter.println(result);  // sends result to client as string in Json format
                            cachemap.emptyCache();
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("UPDATERUNTIMEBYID"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the id and time parameter
                        String id = protocol[1];
                        String time = protocol[2];

                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            IMovieDao.updateMovieRuntimeByID(Integer.parseInt(id), time); //updates movie object
                            movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //gets updated movie objects
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            socketWriter.println(result);  // sends result to client as string in Json format
                            cachemap.emptyCache(); //reset cache as datbase has been updated
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("UPDATEMOVIEACTORS"))
                    {

                        String[] protocol = message.split("~"); //splits the command to get the id and actor parameters
                        String id = protocol[1];
                        String actors = protocol[2];

                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            IMovieDao.updateMovieActors(Integer.parseInt(id), actors); //updates movie objects
                            movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //gets updated movie objects
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            socketWriter.println(result);  // sends result to client as string in Json format
                            cachemap.emptyCache(); //reset cache as datbase has been updated
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("updateMovieGenre"))
                    {

                        String[] protocol = message.split("~"); //splits the command to get the id and genre parameters
                        String id = protocol[1];
                        String genre = protocol[2];

                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            IMovieDao.updateMovieGenre(Integer.parseInt(id), genre); //updates movie objects
                            movies = IMovieDao.findMovieByID(Integer.parseInt(id)); //gets updated movie objects
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            socketWriter.println(result);  // sends result to client as string in Json format

                            cachemap.emptyCache();//reset cache as datbase has been updated
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("update"))
                    {

                        String[] protocol = message.split("~"); //splits the command to get the id and genre parameters
                        
                        String [] updates = new String[5];
                        updates[0] = protocol[1];
                        updates[1] = protocol[3];
                        updates[2] = protocol[5];
                        updates[3] = protocol[2];
                        updates[4] = protocol[4];
                        
                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(updates[0])); //calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            IMovieDao.updateMovie(updates); //updates movie objects
                            movies = IMovieDao.findMovieByID(Integer.parseInt(updates[0])); //gets updated movie objects
                            String result = createJsonString(movies); //Converts list into a string in Json Format
                            socketWriter.println(result);  // sends result to client as string in Json format

                            cachemap.emptyCache();//reset cache as datbase has been updated
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }

                    else if (message.startsWith("WATCHMOVIE"))
                    {

                        String[] protocol = message.split(","); //splits the command to get the userid and movieid parameters
                        String userid = protocol[1];
                        String movieid = protocol[2];

                        List<Movie> movies = IMovieDao.findMovieByID(Integer.parseInt(movieid));//calls method in DAO and passes in id to see if movie exists

                        if (!(movies.isEmpty()))
                        {
                            //if movie exists the movie id and user id are entered into the user watched table
                            IMovieWatchedDao.insertMovieWatched(Integer.parseInt(userid), Integer.parseInt(movieid));
                            
                            String result = createJsonString(movies);;
                            socketWriter.println(result);  // sends result to client as string in Json format
                            
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                    }

                    else if (message.startsWith("ADDMOVIE"))
                    {
                        Movie m = new Movie();//create new movie object
                        String[] protocol = message.split("~"); //splits the command to get the movie parameters
                        String title = protocol[1];
                        String genre = protocol[2];
                        String director = protocol[3];
                        String rTime = protocol[4];
                        String year = protocol[5];
                        String starring = protocol[6];

                        //converting genre from a string into an array list of strings
                        String[] genreArray = genre.split(",");
                        ArrayList<String> genreArrayList = new ArrayList<>(Arrays.asList(genreArray));

                        //converting actors from a string into an array list of strings
                        String[] starringArray = starring.split(",");
                        ArrayList<String> starringArrayList = new ArrayList<>(Arrays.asList(starringArray));

                        //setting values in new movie object
                        m.setTitle(title);
                        m.setGenre(genreArrayList);
                        m.setDirector(director);
                        m.setRuntime(rTime);
                        m.setYear(year);
                        m.setStarring(starringArrayList);

                        
                        
                        if(IMovieDao.insertMovie(m))
                        {
                            ArrayList<Movie> movies = new ArrayList<>();
                            movies.add(m);
                            String result = createJsonString(movies); //converting movie object into a string in json format
                            socketWriter.println(result);  // sends result to client as string in Json format

                        }//adding new movie new datbase
                        else{
                            String result = "{\"Message\" : \"Fail\"}";
                        }
                        cachemap.emptyCache(); //rest cache as datbase has been updated
                    }
                    else if (message.startsWith("RECOMMENDBYGENRE"))
                    {
                        String[] protocol = message.split(","); //splits command to get user id
                        String userid = protocol[1];

                        //gets list of recommended movies based on user id
                        List<Movie> movies = (IMovieWatchedDao.getRecomended1(Integer.parseInt(userid)));

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies);//convert list of movie objects into a string
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                    else if (message.startsWith("RECOMMENDBYGENREDIR"))
                    {
                        String[] protocol = message.split(","); //splits command to get user id
                        String userid = protocol[1];

                        //gets list of recommended movies based on user id
                        List<Movie> movies = (IMovieWatchedDao.getRecomended2(Integer.parseInt(userid)));

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies);//convert list of movie objects into a string
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }

                    }
                     else if (message.startsWith("RECOMMENDRAND"))
                    {
                        //finds movies by director and genre
                        List<Movie> movies = (IMovieDao.findRandom());

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies);
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                    }
                    
                    else if (message.startsWith("FINDMOVIESBYDIRECTORANDGENRE"))
                    {
                        String[] protocol = message.split(","); //split command to get direcotr and genre parameters
                        String director = protocol[1];
                        String genre = protocol[2];
                        
                        //finds movies by director and genre
                        List<Movie> movies = (IMovieDao.findMoviesDirectorGenre(director, genre));

                        if (!(movies.isEmpty()))
                        {
                            String result = createJsonString(movies);
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                        else
                        {
                            String result = "{\"Message\" : \"Fail\"}";
                            socketWriter.println(result);  // sends result to client as string in Json format
                        }
                    }

                    cachemap.print();
                }
            }

            catch (IOException ex)
            {
                ex.printStackTrace();
                LOGGER.warning("IOException caught");
            }
            catch (DaoException ex)
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
            LOGGER.warning("Handler for Client " + clientNumber + " is terminating .....");
        }
    }

    /**
     * Method takes in a list of movie objects and converts it to a string in
     * Json format
     *
     * @param movies
     * @return
     */
    public static String createJsonString(List<Movie> movies)
    {

        String m = "{\"Message\" :\"Success\",\"Movies\":[";
        int size = movies.size();

        for (Movie movie : movies)
        {
            m = m + createJsonString(movie);
            if (size != 1)
            {
                m = m + ",";
                size--;
            }

        }

        m = m + "]}";

        return m;
    }

    /**
     * Method takes in a movie object and converts it to a string in Json format
     *
     * @param m
     * @return
     */
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
    
    
    
}
