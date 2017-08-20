package httpClient;

import org.springframework.stereotype.Component;

@Component
public class SaveScoreHtmlStrImpl extends SaveHtmlStrSupport {
	
	private static final String TABLE_BEGIN_SIGN = "<table width='857' border=0 ID='ID_Table' CELLPADDING=0 CELLSPACING=1  bgcolor='#89bfa7' align='center'>";
	
	private static final String EMPTY_INFO_ERROR = " width=";
	
	public SaveScoreHtmlStrImpl() {
		super.TABLE_BEGIN_SIGN = TABLE_BEGIN_SIGN;
		super.TD_ERROR_BEGIN_SIGN = EMPTY_INFO_ERROR;
	}
}
