package mobi.rayson.declarativetransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-02-27
 *  Time: 3:43 PM
 *  Description:
 **/
@Component
public class DeclarativeTransactionServiceImpl implements DeclarativeTransactionService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public void insertRecord() {
    String sql = "INSERT INTO FOO (BAR) VALUES ('AAA')";
    jdbcTemplate.execute(sql);
  }

  @Override
  @Transactional(rollbackFor = RollbackException.class)
  public void insertThenRollback() throws RollbackException {
    String sql = "INSERT INTO FOO (BAR) VALUES ('BBB')";
    jdbcTemplate.execute(sql);
    throw new RollbackException();
  }

  @Override
  public void invokeInsertThenRollback() throws RollbackException {
    insertThenRollback();
  }
}
