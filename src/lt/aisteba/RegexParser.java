package lt.aisteba;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser implements Parser{

  private Matcher mTag, mLink;
  private Pattern pTag, pLink;

  private static final String HTML_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
  private static final String HTML_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

  public RegexParser() {
    pTag = Pattern.compile(HTML_TAG_PATTERN);
    pLink = Pattern.compile(HTML_HREF_TAG_PATTERN);
  }

//  List<Anchor> parse (Reader reader, String Url);

  public Set<Anchor> parse(Reader reader, String url) {

    Set<Anchor> elements = new HashSet<>();
    String urtContent = null;
    try {
      urtContent = readFully(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (urtContent == null){
      return elements;
    }

    mTag = pTag.matcher(urtContent);

    while (mTag.find()) {

      String href = mTag.group(1);     // get the values of href
      String linkElem = mTag.group(2); // get the text of link Html Element

      mLink = pLink.matcher(href);

      while (mLink.find()) {

        String link = mLink.group(1);
        Anchor anchor = new Anchor();
        anchor.setUrl(link);
        anchor.setText(linkElem);

        elements.add(anchor);

      }
    }
    return elements;
  }

  public String readFully(Reader reader) throws IOException {
    char[] arr = new char[8*1024]; // 8K at a time
    StringBuffer buf = new StringBuffer();
    int numChars;

    while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
      buf.append(arr, 0, numChars);
    }

    return buf.toString();
  }

}
