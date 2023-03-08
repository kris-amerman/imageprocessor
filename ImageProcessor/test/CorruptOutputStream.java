import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a corrupt OutputStream. This mock will always throw an
 * exception for any of its write methods.
 */
public class CorruptOutputStream extends OutputStream {

  /**
   * Throw an exception when trying to write to a byte array.
   */
  @Override
  public void write(byte[] b) throws IOException {
    throw new IOException("Corrupt OutputStream");
  }

  /**
   * Throw an exception when trying to write to a byte array with specified dimensions.
   */
  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    throw new IOException("Corrupt OutputStream");
  }

  /**
   * Throw an exception when trying to write to an int.
   */
  @Override
  public void write(int b) throws IOException {
    throw new IOException("Corrupt OutputStream");
  }
}
