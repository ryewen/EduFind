package httpClient.post.param.object;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RatePagePostParams implements PagePostParams {
	
	//special 不同post的key value是否一致，写在httpClient.PostRateLesson的setPostParams()方法中
	
	public static final String CJ0 = "cj0";
	
	public static final String CJ1 = "cj1";
	
	public static final String CJ2 = "cj2";
	
	public static final String CJ3 = "cj3";
	
	public static final String CJ4 = "cj4";
	
	public static final String CQL = "cql";
	
	public static final String DJDM = "djdm";
	
	public static final String HID_ITEM = "hid_item";
	
	public static final String HID_PERCENTALL = "hid_percentall";
	
	public static final String HIDQKYY = "hidqkyy";
	
	public static final String SCORE = "score";
	
	public static final String SEL_SCORECJ5 = "sel_scorecj5";
	
	public static final String TXTQTYJ = "txtqtyj";

	//commom
	
	public static final String TXT_COUNT = "txt_count";
	
	public static final String TXTJS = "txtjs";
	
	public static final String TXTKC = "txtkc";
	
	public static final String TXTKCLB = "txtkclb";
	
	public static final String TXTKCLX = "txtkclx";
	
	public static final String TXTLB = "txtlb";
	
	public static final String TXTPJLC = "txtPJLC";
	
	public static final String TXTS = "txts";

	public static final String TXTSKBJ = "txtSKBJ";
	
	public static final String TXTSKFS = "txtSKFS";
	
	public static final String TXTUSER = "txtuser";

	public static final String TXTXN = "txtxn";
	
	public static final String TXTXQ = "txtxq";
	
	public static final String TXTZJ = "txtZJ";
	
	private Map<String, String> commonPostParams = new HashMap<String, String>();
	
	private Map<String, String> specialPostParams = new HashMap<String, String>();
	
	public Map<String, String> getPostParams() {
		Set<Entry<String, String>> entrys1 = commonPostParams.entrySet();
		Set<Entry<String, String>> entrys2 = specialPostParams.entrySet();
		Map<String, String> map = new HashMap<String, String>();
		Iterator<Entry<String, String>> it1 = entrys1.iterator();
		Iterator<Entry<String, String>> it2 = entrys2.iterator();
		while(it1.hasNext()) {
			Entry<String, String> entry = it1.next();
			map.put(entry.getKey(), entry.getValue());
		}
		while(it2.hasNext()) {
			Entry<String, String> entry = it2.next();
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
	
	public void putCommonPostParam(String key, String value) {
		getCommonPostParams().put(key, value);
	}
	
	public void putSpecialPostParam(String key, String value) {
		getSpecialPostParams().put(key, value);
	}
	
	public Map<String, String> getCommonPostParams() {
		return commonPostParams;
	}
	
	public Map<String, String> getSpecialPostParams() {
		return specialPostParams;
	}
}
