package lt.aisteba;

import java.io.InputStream;

public interface Store {

  void save (InputStream stream, String name);

}
