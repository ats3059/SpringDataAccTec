package hello.jdbc.reopsitory.ex;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MyDuplicateKeyException extends MyDbException {
    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
