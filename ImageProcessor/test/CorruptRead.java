import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This class is for testing purposes for throwing IOExceptions for readable.
 */

public class CorruptRead implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
