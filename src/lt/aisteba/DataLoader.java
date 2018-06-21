package lt.aisteba;

import java.io.InputStream;

public interface DataLoader {

  InputStream load(String url);

}
