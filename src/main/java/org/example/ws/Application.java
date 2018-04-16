package org.example.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import  org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;


/**g
 * Spring boot main application
 *
 */
 @SpringBootApplication
 @EnableTransactionManagement
 @EnableCaching
public class Application
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }

// defining cache manager implementation bean
@Bean
public CacheManager cacheManager() {
  ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(
                    "greetings");
  return cacheManager;

}
}
