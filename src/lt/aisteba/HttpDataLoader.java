package lt.aisteba;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDataLoader implements DataLoader {

  public InputStream load(String url){
    URL pageUrl = null;
    InputStream urlContent = null;
    try {
      pageUrl = new URL(url);
    } catch (MalformedURLException e) {
      System.err.println("Url: "+ url+ " wrong url desccription " + e.getMessage());
    }
    if (pageUrl == null){
      return urlContent;
    }
    try {
      urlContent = pageUrl.openStream();

    } catch (IOException e) {
      System.err.println("Failed to read link content: "+ url + " " + e.getMessage());
    }
    return urlContent;
  }

}
