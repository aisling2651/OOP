/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import DTOs.Movie;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aisli
 */
public class Cache<K,V>
{
    
    
    private Map<K,V> cache = new HashMap(new HashMap<>());
    
    
        public synchronized boolean checkKey(K key)
        {
            return cache.containsKey(key);
        }
        
        public synchronized V getValue(K key)
        {
          return cache.get(key);
        }
        
        public synchronized void emptyCache()
        {
            cache.clear();
        }
        
        public synchronized void addToCache(K key, V m)
        {
            cache.put(key,m);
        }
        
        public void print()
        {
            System.out.println("\n**Cache**: ");
            for(Map.Entry<K, V> entry : cache.entrySet())
                    {
                        System.out.println(entry.getKey() + " = " + entry.getValue());
                    }
        
        }
       


    
}
