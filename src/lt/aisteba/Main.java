package lt.aisteba;


public class Main {

    public static void main(String[] args) {


     new WebCrawler(new RegexParser(), new HttpDataLoader(), new FileStore()).crawl("http://www.delfi.lt", 1);


    }

}
