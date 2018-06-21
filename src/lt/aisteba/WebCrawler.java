package lt.aisteba;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {

  final static String URL_DOWNLOAD_PATH = "C:\\Users\\Aiste\\Desktop\\savedFiles\\";
  private int maxDepth;

  private static Parser parser;
  private DataLoader dataloader;
  private Store store;

  public WebCrawler(Parser parser, DataLoader dataloader, Store store) {
    this.parser = parser;
    this.dataloader = dataloader;
    this.store = store;
  }

  public void crawl(String startUrl, int maxDepth) {
    this.maxDepth = maxDepth;
     List<AnalyzeResult> stack = new ArrayList<>();
    scrapUrl(startUrl, 0, stack);
    printAnalyzeResult(stack);
  }

  private void printAnalyzeResult(List<AnalyzeResult> stack) {

    for(AnalyzeResult result: stack){
      System.out.println("URL: " + result.getUrl());
      for (Anchor link : result.getAnchorLinks()){
        System.out.println("Link: " + link.getUrl());
      }
    }
  }

  private void scrapUrl(String url, int level, List<AnalyzeResult> stack){

    if (this.maxDepth > level){
      AnalyzeResult result = analyzeContent(url, level);
      if (result.getAnchorLinks() != null){
        for(Anchor link : result.getAnchorLinks()){
          String linkAddress = link.getUrl();
          if(linkAddress != null) {
            scrapUrl(linkAddress, level + 1, stack);
          }
        }
     }
     stack.add(result);
    }
  }

  private AnalyzeResult analyzeContent(String url, int level){

    InputStream stream = this.dataloader.load(url);

    AnalyzeResult result = new AnalyzeResult();
    result.setUrl(url);
    result.setStream(stream);

    if (this.maxDepth > level) {
      Set<Anchor> links = this.parser.parse(new InputStreamReader(stream), null);

      for (Anchor link : links) {
        String linkAdress = link.getUrl();
        if (linkAdress != null) {
          if (linkAdress.indexOf("//") == 0) {
            link.setUrl("https:" + linkAdress);
          } else if (linkAdress.indexOf("/") == 0) {
            link.setUrl(url + linkAdress);
          } else if (linkAdress.indexOf("#") >= 0){
            link.setUrl(null);
          }
        }
      }
      result.setAnchorLinks(links);
    }


    return result;
  }

  private void wrapScrapResults(List<AnalyzeResult> stack) {
    if (stack != null){
      for (AnalyzeResult result : stack){
        wrapScrapResult(result);
      }
    }
  }

  private void wrapScrapResult(AnalyzeResult result){
    String urlFileName = FileUtils.fileOutputName(URL_DOWNLOAD_PATH, FileUtils.creteUrlName(result.getUrl()) + ".html");
    this.store.save(result.getStream(), urlFileName);
  }

  private class AnalyzeResult {
    private String url;
    private InputStream stream;
    //anchor (<a href="#"></a>)
    private Set<Anchor> anchorLinks = new HashSet<>();

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public InputStream getStream() {
      return stream;
    }

    public void setStream(InputStream stream) {
      this.stream = stream;
    }

    public Set<Anchor> getAnchorLinks() {
      return anchorLinks;
    }

    public void setAnchorLinks(
        Set<Anchor> anchorLinks) {
      this.anchorLinks = anchorLinks;
    }

  }

  public static String toString( byte[] bytes ){
    return new String(bytes, StandardCharsets.UTF_8);
  }


}
