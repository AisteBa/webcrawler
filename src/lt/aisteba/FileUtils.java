package lt.aisteba;

import java.io.File;

public class FileUtils {

  protected final static String ILLEGAL_FILE_CHARS = "[\\\\/:*?\"<>|]";
  protected final static String DEFAULT_DElIMETER = "";

  public static String fileOutputName(String... fileParts){
    return String.join(File.separator, fileParts);
  }

  public static String creteUrlName(String url) {
    return creteUrlName(url, DEFAULT_DElIMETER);
  }

  public static String creteUrlName(String url, String delimiter) {
    String urlFileName = url.replaceAll(ILLEGAL_FILE_CHARS, delimiter);
    return urlFileName.substring(0, Math.min(urlFileName.length(), 255));
  }



}
