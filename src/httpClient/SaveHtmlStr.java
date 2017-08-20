package httpClient;

import java.io.File;
import java.util.List;

public interface SaveHtmlStr {

	public List<List<String>> saveHtmlIntoLists(String html) throws DealHtmlStrException ;
}
