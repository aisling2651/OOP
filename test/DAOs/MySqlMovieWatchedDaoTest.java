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
public class MySqlMovieWatchedDaoTest
{
    
    public MySqlMovieWatchedDaoTest()
    {
    }

    /**
     * Test of insertMovieWatched method, of class MySqlMovieWatchedDao.
     */
    //@Test
    public void testInsertMovieWatched() throws Exception
    {
        System.out.println("insertMovieWatched");
        int userID = 102;
        int movieID = 1006;
        MySqlMovieWatchedDao instance = new MySqlMovieWatchedDao();
        instance.insertMovieWatched(userID, movieID);
    }
    
//        /**
//     * Test of findMovieWatched method, of class MySqlMovieWatchedDao.
//     */
//    @Test
//    public void testFindMovieWatched() throws Exception
//    {
//        System.out.println("findMovieWatched");
//        int userId = 1;
//        MySqlMovieWatchedDao instance = new MySqlMovieWatchedDao();
//        List<Integer> expResult = <1052>;
//        List<Integer> result = instance.findMovieWatched(userId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }


//    /**
//     * Test of getRecomended method, of class MySqlMovieWatchedDao.
//     */
//    @Test
//    public void testGetRecomended_int() throws Exception
//    {
//        System.out.println("getRecomended");
//        int userId = 0;
//        MySqlMovieWatchedDao instance = new MySqlMovieWatchedDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.getRecomended(userId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//

//    /**
//     * Test of getMovieGenres method, of class MySqlMovieWatchedDao.
//     */
//    @Test
//    public void testGetMovieGenres() throws Exception
//    {
//        System.out.println("getMovieGenres");
//        List<Integer> id = null;
//        MovieDaoInterface IMovieDao = null;
//        MySqlMovieWatchedDao instance = new MySqlMovieWatchedDao();
//        List<String> expResult = null;
//        List<String> result = instance.getMovieGenres(id, IMovieDao);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRecomended method, of class MySqlMovieWatchedDao.
//     */
//    @Test
//    public void testGetRecomended_ArrayList_MovieDaoInterface() throws Exception
//    {
//        System.out.println("getRecomended");
//        ArrayList<String> genres = null;
//        MovieDaoInterface IMovieDao = null;
//        MySqlMovieWatchedDao instance = new MySqlMovieWatchedDao();
//        List<Movie> expResult = null;
//        List<Movie> result = instance.getRecomended(genres, IMovieDao);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
