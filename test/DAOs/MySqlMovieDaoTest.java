/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aisli
 */
public class MySqlMovieDaoTest
{
    
    public MySqlMovieDaoTest()
    {
    }

    /**
     * Test of findAMovieByID method, of class MySqlMovieDao.
     */
    //@Test
    public void testFindAMovieByID() throws Exception
    {
        System.out.println("findAMovieByID");
        int mID = 19;
        MySqlMovieDao instance = new MySqlMovieDao();
        Movie expResult = new Movie(19,"tammy","Ben Falcone","97 min","2014");
        Movie result = instance.findAMovieByID(mID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findAMovieByTitle method, of class MySqlMovieDao.
     */
    //@Test
    public void testFindAMovieByTitle() throws Exception
    {
        System.out.println("findAMovieByTitle");
        String mName = "Dogma";
        MySqlMovieDao instance = new MySqlMovieDao();
        Movie expResult = new Movie(737,"Dogma",null,"Kevin Smith","130 min","1999",null);
        Movie result = instance.findAMovieByTitle(mName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

//    /**
//     * Test of findMoviesByRuntimeGreaterThan method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByRuntimeGreaterThan() throws Exception
//    {
//        System.out.println("findMoviesByRuntimeGreaterThan");
//        String time = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByRuntimeGreaterThan(time);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByDirector method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByDirector() throws Exception
//    {
//        System.out.println("findMoviesByDirector");
//        String dName = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByDirector(dName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMovieByTitle method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMovieByTitle() throws Exception
//    {
//        System.out.println("findMovieByTitle");
//        String mName = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMovieByTitle(mName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByYear method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByYear() throws Exception
//    {
//        System.out.println("findMoviesByYear");
//        String year = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByYear(year);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMovieByID method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMovieByID() throws Exception
//    {
//        System.out.println("findMovieByID");
//        int mID = 0;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMovieByID(mID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByTitleLike method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByTitleLike() throws Exception
//    {
//        System.out.println("findMoviesByTitleLike");
//        String mName = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByTitleLike(mName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteMovieByID method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testDeleteMovieByID() throws Exception
//    {
//        System.out.println("deleteMovieByID");
//        int movieID = 0;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.deleteMovieByID(movieID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMovieRuntimeByID method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testUpdateMovieRuntimeByID() throws Exception
//    {
//        System.out.println("updateMovieRuntimeByID");
//        int mID = 0;
//        String rTime = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.updateMovieRuntimeByID(mID, rTime);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertMovie method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testInsertMovie() throws Exception
//    {
//        System.out.println("insertMovie");
//        Movie m = null;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.insertMovie(m);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByGenre method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByGenre_String() throws Exception
//    {
//        System.out.println("findMoviesByGenre");
//        String in = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByGenre(in);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByActors method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByActors_String() throws Exception
//    {
//        System.out.println("findMoviesByActors");
//        String in = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByActors(in);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesDirectorGenre method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesDirectorGenre() throws Exception
//    {
//        System.out.println("findMoviesDirectorGenre");
//        String dir = "";
//        String gen = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesDirectorGenre(dir, gen);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByActors method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByActors_ArrayList() throws Exception
//    {
//        System.out.println("findMoviesByActors");
//        ArrayList<String> in = null;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByActors(in);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findMoviesByGenre method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindMoviesByGenre_ArrayList() throws Exception
//    {
//        System.out.println("findMoviesByGenre");
//        ArrayList<String> in = null;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findMoviesByGenre(in);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMovieActors method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testUpdateMovieActors() throws Exception
//    {
//        System.out.println("updateMovieActors");
//        int movieID = 0;
//        String starring = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.updateMovieActors(movieID, starring);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMovieGenre method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testUpdateMovieGenre() throws Exception
//    {
//        System.out.println("updateMovieGenre");
//        int movieID = 0;
//        String genre = "";
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.updateMovieGenre(movieID, genre);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMovie method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testUpdateMovie() throws Exception
//    {
//        System.out.println("updateMovie");
//        Movie m = null;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        instance.updateMovie(m);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findRecommendDirectorGenre method, of class MySqlMovieDao.
//     */
//    @org.junit.Test
//    public void testFindRecommendDirectorGenre() throws Exception
//    {
//        System.out.println("findRecommendDirectorGenre");
//        ArrayList<String> dir = null;
//        ArrayList<String> gen = null;
//        MySqlMovieDao instance = new MySqlMovieDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.findRecommendDirectorGenre(dir, gen);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    private void assertEquals(Movie expResult, Movie result)
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    

    private void assertEquals(Movie expResult, Movie result)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
