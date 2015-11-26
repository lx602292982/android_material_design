package  com.lixiang.weather.support.http;

public class HttpResponseHandler {
	String chartSet = "UTF-8";
	
	public HttpResponseHandler() {
	}
	public HttpResponseHandler(String chartSet) {
		this.chartSet = chartSet;
	}
	public String getChartSet(){
		return chartSet;
	}
	public void onSuccess(String data) {};
	public void onFailure(int code) {};
}
