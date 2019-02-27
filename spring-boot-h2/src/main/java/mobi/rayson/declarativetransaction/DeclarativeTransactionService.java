package mobi.rayson.declarativetransaction;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-02-27
 *  Time: 3:40 PM
 *  Description:
 **/
public interface DeclarativeTransactionService {
  void insertRecord();

  void insertThenRollback() throws RollbackException;

  void invokeInsertThenRollback() throws RollbackException;
}
