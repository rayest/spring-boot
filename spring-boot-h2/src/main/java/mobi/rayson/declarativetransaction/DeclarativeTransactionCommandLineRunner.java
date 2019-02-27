package mobi.rayson.declarativetransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-02-27
 *  Time: 3:54 PM
 *  Description:
 **/
@Slf4j
@Component
public class DeclarativeTransactionCommandLineRunner implements CommandLineRunner {

  @Autowired
  private DeclarativeTransactionService declarativeTransactionService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... args) throws Exception {
    declarativeTransactionService.insertRecord();
    log.info("AAA {}",
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
    try {
      declarativeTransactionService.insertThenRollback();
    } catch (Exception e) {
      log.info("BBB {}",
          jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", Long.class));
    }

    try {
      declarativeTransactionService.invokeInsertThenRollback();
    } catch (Exception e) {
      log.info("BBB {}",
          jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", Long.class));
    }
  }
}
