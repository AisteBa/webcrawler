package lt.aisteba;

import java.io.Reader;
import java.util.List;
import java.util.Set;

public interface Parser {

  Set<Anchor> parse (Reader reader, String Url);

}
