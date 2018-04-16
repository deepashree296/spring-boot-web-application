package org.example.ws.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

@Component
public class GreetingBatchBean {

  // create logger
  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private GreetingService greetingService;

  // first job measured from application start, and repeated as per cron configuration.
  //@Scheduled(
    //     cron="0,30 * * * * *")
  public void cronJob() {
    logger.info("> cronJob");

    Collection<Greeting> greetings = greetingService.findAll();
    logger.info("There are {} greetings in the data store", greetings.size());

    logger.info("< cronJob");
  }

  // time measured from start of first job , for the next job to start
  //@Scheduled(
    //     initialDelay=5000,
      //   fixedRate=15000)
  public void fixedRateJobWithInitialDelay() {
    logger.info("> fixedRateJobWithInitialDelay");

    long pause = 5000;
    long start = System.currentTimeMillis();
    do {
      if (start + pause < System.currentTimeMillis()) {
        break;
      }
    } while(true);
    logger.info("processing time was {} seconds", pause / 1000);

    logger.info("< fixedRateJobWithInitialDelay");
  }

  // time measured from end of first job for the next job to start, this ensures two jobs are not running at the same time.
  @Scheduled(
         initialDelay=5000,
         fixedDelay=15000)
  public void fixedDelayJobWithInitialDelay() {
    logger.info("> fixedDelayJobWithInitialDelay");

    long pause = 5000;
    long start = System.currentTimeMillis();
    do {
      if (start + pause < System.currentTimeMillis()) {
        break;
      }
    } while(true);
    logger.info("processing time was {} seconds", pause / 1000);

    logger.info("< fixedDelayJobWithInitialDelay");
  }



}
