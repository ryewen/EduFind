package httpClient;

import org.springframework.stereotype.Component;

@Component
public class SaveRateLessonHtmlImpl extends SaveHtmlStrSupport {
	
	private static final String TABLE_BEGIN_SIGN = "<table width='100%' border=1 ID='ID_Table' CELLPADDING=0 CELLSPACING=0  bordercolorlight='#89bfa7' bordercolordark='#FFFFFF'>";
	
	public SaveRateLessonHtmlImpl() {
		super.TABLE_BEGIN_SIGN = TABLE_BEGIN_SIGN;
	}
}
