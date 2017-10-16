package httpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import teachnet.info.object.InfoObject;

public interface SaveInfoDetail {

	public List<InfoObject> getLessonsAndDetails(List<Cookie> cookies, String tableUrl,
			Map<String, String> queryPostParams) throws IOException, DealHtmlStrException ;
}
