package pf.xqq.rxjava;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func0;

public class RxVideoTime {
	private String fid; 
	private String resource; 
	private String url;
	private String courseId;
	private String name;
	private String token;
	
	public RxVideoTime(String deURL,String fid,String resource,String courseId) {
		this.resource=resource;
		this.fid=fid;
		this.courseId=courseId;
		url = "http://60.205.95.148/xxpt/?q=video/get/obj";
	}

	public String getGist() throws IOException {
		 
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder()
		.add("fid", "8620") 
		.add("from ", "q=558")
		.add("resource ", "0")
		.build();
		
		Request request = new Request.Builder().url(url).post(formBody).build();
		Response response = mOkHttpClient.newCall(request).execute();
		if (response.code()==200) {
			String htmlStr = response.body().string();	
			Log.e("QT","获取视屏弹框数据---->"+htmlStr);
			return null;
		}
		return null;
	}

	public Observable<String> getGistObservable() {
		return Observable.defer(new Func0<Observable<String>>() {
			@Override
			public Observable<String> call() {
				try {
					return Observable.just(getGist());
				} catch (IOException e) {
					return Observable.error(e);
				}
			}
		});

	}

}