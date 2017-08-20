package httpClient;

import java.io.IOException;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetNameStr {

	@Autowired
	private TeachNetHttpClient client;

	private static final String NAME_URL = TeachNetHttpClient.HOME_URL + "/PUB/foot.aspx";
	
	public String getNameStr(List<Cookie> cookies) throws IOException, DealHtmlStrException {
		String html = client.saveTableHtmlStr(cookies, NAME_URL, null);
		try {
			return html.split("id=TheFootMemo")[1].split("]")[1].split("</div>")[0];
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new DealHtmlStrException();
		}
	}
}
