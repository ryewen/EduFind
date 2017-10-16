package httpClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import teachnet.info.object.InfoObject;
import teachnet.info.object.score.Score;

@Component
public class SaveScoreDetailImpl extends SaveInfoDetailSupport {
	
	private static final String SCORE_TITLE_DETAIL_KIND_NAME = "课程/环节";
	
	private static final String SCORE_TITLE_DETAIL_KIND_CREDIT = "学分";
	
	private static final String SCORE_TITLE_DETAIL_KIND_SCORE = "综合成绩";
	
	private static final String SCORE_DETAIL_KIND_LENGTH = "length";
	
	private static final String SCORE_DETAIL_KIND_NAME = "name";
	
	private static final String SCORE_DETAIL_KIND_CREDIT = "credit";
	
	private static final String SCORE_DETAIL_KIND_SCORE = "score";
	
	@Autowired
	@Qualifier("saveScoreHtmlStrImpl")
	private SaveHtmlStr saveHtmlStr;
	
	@Override
	protected SaveHtmlStr getSaveHtmlStr() {
		return saveHtmlStr;
	}
	
	@Override
	protected List<InfoObject> getOneListObjectsAndDetails(List<String> objectsStrs){
		List<InfoObject> scores = new LinkedList<InfoObject>();
		Map<String, Integer> detailNameAndIndex = getDetailKindAndIndex(objectsStrs);
		Iterator<String> it = objectsStrs.iterator();
		int detailNameLength = detailNameAndIndex.get(SCORE_DETAIL_KIND_LENGTH);
		for(int i = 0; i < detailNameLength; i ++) {
			it.next();
		}
		int i = 0;
		Score score = null;
		while(it.hasNext()) {
			if(i == 0) score = new Score();
			String str = it.next();
			if(i == detailNameAndIndex.get(SCORE_DETAIL_KIND_NAME)) {
				score.setName(str);
			}
			if(i == detailNameAndIndex.get(SCORE_DETAIL_KIND_CREDIT)) {
				score.setCredit(str);
			}
			if(i == detailNameAndIndex.get(SCORE_DETAIL_KIND_SCORE)) {
				score.setScore(str);
			}
			i ++;
			if(i == detailNameLength) {
				i = 0;
				scores.add(score);
			}
		}
		return scores;
	}
	
	@Override
	protected Map<String, Integer> getDetailKindAndIndex(List<String> objectsStrs) {
		Map<String, Integer> detailNameAndIndex = new HashMap<String, Integer>();
		Iterator<String> it = objectsStrs.iterator();
		Integer i = 0;
		while(it.hasNext()) {
			String str = it.next();
			try {
				int temp = Integer.valueOf(str);
				detailNameAndIndex.put(SCORE_DETAIL_KIND_LENGTH, i);
				return detailNameAndIndex;
			} catch (NumberFormatException e) {
				if(str.equals(SCORE_TITLE_DETAIL_KIND_NAME)) {
					detailNameAndIndex.put(SCORE_DETAIL_KIND_NAME, i);
				}
				if(str.equals(SCORE_TITLE_DETAIL_KIND_CREDIT)) {
					detailNameAndIndex.put(SCORE_DETAIL_KIND_CREDIT, i);
				}
				if(str.equals(SCORE_TITLE_DETAIL_KIND_SCORE)) {
					detailNameAndIndex.put(SCORE_DETAIL_KIND_SCORE, i);
				}
				i ++;
				continue;
			}
		}
		return detailNameAndIndex;
	}
}
