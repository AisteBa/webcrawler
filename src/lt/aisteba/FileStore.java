package lt.aisteba;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileStore implements Store {

  public void save(InputStream stream, String name){
    try {
      toFile(name, toBytes(stream));
    } catch (IOException e) {
      System.out.println("Canot create file" + name + " because " + e.getMessage());
    }
  }

  public void toFile( String fileName, byte[] bytes ) throws IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    fos.write(bytes);
    fos.close();
  }

  public byte[] toBytes(InputStream stream) {
    if (stream == null) {
      return new byte[] {};
    }
    byte[] buffer = new byte[1024];
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      int numRead = 0;
      while ((numRead = stream.read(buffer)) > -1) {
        output.write(buffer, 0, numRead);
      }
    } catch (IOException e) {
      System.err.println("Failed to read stream " + e.getMessage());
    } finally {
      try {
        stream.close();
        output.flush();
      } catch (IOException e) {
        System.err.println("Failed to read stream " + e.getMessage());
      }
    }

    return output.toByteArray();
  }

}
