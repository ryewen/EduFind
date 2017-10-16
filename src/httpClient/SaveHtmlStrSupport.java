package httpClient;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SaveHtmlStrSupport implements SaveHtmlStr {

	protected String TABLE_BEGIN_SIGN = "";
	
	private static final String TABLE_END_SIGN = "</table>";
	
	private static final String TD_BEGIN_SIGN = "<td";
	
	private static final String TD_END_SIGN = "</td>";
	
	protected String TD_ERROR_BEGIN_SIGN = "error"; //just example
	
	@Override
	public List<List<String>> saveHtmlIntoLists(String html) throws DealHtmlStrException {
		return recSaveHtmlIntoLists(html);
	}
	
	private List<List<String>> recSaveHtmlIntoLists(String html) throws DealHtmlStrException {
		List<List<String>> lists = new LinkedList<List<String>>();
		List<String> tableStrs = getTableStrs(html);
		Iterator<String> it = tableStrs.iterator();
		while(it.hasNext()) {
			List<String> tableList = new LinkedList<String>();
			Iterator<String> it2 = getTdStrs(it.next()).iterator();
			while(it2.hasNext()) {
				String str = it2.next();
				tableList.add(str);
			}
			lists.add(tableList);
		}
		return lists;
	}
	
	private List<String> getTdStrs(String tableStr) throws DealHtmlStrException {
		List<String> tdStrs = new LinkedList<String>();
		String[] firstSplitted = tableStr.split(TD_BEGIN_SIGN);
		for(int i = 1; i < firstSplitted.length; i ++) {
			String tdStr = "";
			String str = firstSplitted[i];
			str = str.split(TD_END_SIGN)[0];
			String[] strs = str.split("<br>");
			for(int j = 0; j < strs.length; j ++) {
				String[] beforeBRStrs = strs[j].split(">");
				if(beforeBRStrs.length == 1) {
					tdStr += beforeBRStrs[0];
				} else {
					if(beforeBRStrs.length == 2) {
						tdStr += beforeBRStrs[1];
					} else {
						if(beforeBRStrs.length == 3) {
							tdStr += beforeBRStrs[2].split("<")[0];
						} else {
							throw new DealHtmlStrException("Cannot get td text, maybe td string split error");
						}
					}
				}
			}
			if(tdStr.startsWith(TD_ERROR_BEGIN_SIGN)) {
				tdStrs.add("");
			} else {
				if(!(tdStr.equals("周次")) && !(tdStr.equals("节次")))	tdStrs.add(tdStr);
			}
		}
		return tdStrs;
	}
	
	private List<String> getTableStrs(String htmlStr) throws DealHtmlStrException {
		List<String> tableStrs = new LinkedList<String>();
		String[] firstSplitted = htmlStr.split(TABLE_BEGIN_SIGN);
		if(firstSplitted.length == 1) {
			throw new DealHtmlStrException("Cannot get table, maybe table sign string error");
		}
		for(int i = 1; i < firstSplitted.length; i ++) {
			String tableStr = firstSplitted[i].split(TABLE_END_SIGN)[0];
			tableStrs.add(tableStr);
		}
		return tableStrs;
	}
	
	public static String getValue(String htmlStr, String leftStr, String rightStr) throws DealHtmlStrException {
		try {
			return htmlStr.substring(htmlStr.indexOf(leftStr) + leftStr.length(), htmlStr.indexOf(rightStr));
		} catch (StringIndexOutOfBoundsException e) {
			throw new DealHtmlStrException();
		}
	}
}
