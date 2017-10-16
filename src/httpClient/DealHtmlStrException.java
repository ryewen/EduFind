package httpClient;

public class DealHtmlStrException extends Exception {

	private String error;
	
	public DealHtmlStrException() {
		
	}
	
	public DealHtmlStrException(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return error;
	}
	
	public String getMessage(String item) {
		return "抱歉, 解析" + item + "时发生错误或教务网无相关信息";
	}
}
