package lt.aisteba;

public class Anchor {

  String text;
  String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = replaceInvalidChar(url);
  }

  public String getText() {
    return text;
  }

  public void setText(String url) {
    this.text = url;
  }

  private String replaceInvalidChar(String url) {
    url = url.replaceAll("'", "");
    url = url.replaceAll("\"", "");
    return url;
  }

  @Override
  public String toString() {
    return "Url : " + this.url + ". Text : "
        + this.text;
  }

}
