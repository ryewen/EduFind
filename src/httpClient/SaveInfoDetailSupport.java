package httpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import teachnet.info.object.InfoObject;

public abstract class SaveInfoDetailSupport implements SaveInfoDetail {

	@Autowired
	private TeachNetHttpClient httpClient;
	
	@Override
	public List<InfoObject> getLessonsAndDetails(List<Cookie> cookies, String tableUrl,
			Map<String, String> queryPostParams) throws IOException, DealHtmlStrException {
		String html = httpClient.saveTableHtmlStr(cookies, tableUrl, queryPostParams);
		return recGetLessonsAndDetails(getSaveHtmlStr().saveHtmlIntoLists(html));
	}

	private List<InfoObject> recGetLessonsAndDetails(List<List<String>> objectsStrsList) {
		List<InfoObject> objects = new LinkedList<InfoObject>();
		Iterator<List<String>> listsIt = objectsStrsList.iterator();
		while(listsIt.hasNext()) {
			Iterator<InfoObject> it = getOneListObjectsAndDetails(listsIt.next()).iterator();
			while(it.hasNext()) {
				objects.add(it.next());
			}
		}
		return objects;
	}
	
	protected abstract SaveHtmlStr getSaveHtmlStr();
	
	protected abstract List<InfoObject> getOneListObjectsAndDetails(List<String> objectsStrs);
	
	protected abstract Map<String, Integer> getDetailKindAndIndex(List<String> objectsStrs);
}
