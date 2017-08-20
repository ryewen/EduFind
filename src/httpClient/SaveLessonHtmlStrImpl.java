package httpClient;

import org.springframework.stereotype.Component;

@Component
public class SaveLessonHtmlStrImpl extends SaveHtmlStrSupport {

	private static final String TABLE_BEGIN_SIGN = "<TABLE class='page_table'>";
	
	private static final String EXPERIENCE_CLASS_TD_STYLE_SIGN = " style='width:";
	
	public SaveLessonHtmlStrImpl() {
		super.TABLE_BEGIN_SIGN = TABLE_BEGIN_SIGN;
		super.TD_ERROR_BEGIN_SIGN = EXPERIENCE_CLASS_TD_STYLE_SIGN;
	}
}
