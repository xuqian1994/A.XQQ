package pf.xqq.rxjava;


import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Bingo on 2016/8/16.
 */
public class post {

    public void loginByPost() {

        try {

            // 请求的地址
            String spec = "http://60.205.95.148/xxpt/?q=video/get/obj";
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "fid=8620"
                    + "&from=?q=588"
                     + "&resource=0" ;
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                Log.i("QT","获取的数据："+result);
                // 通过runOnUiThread方法进行修改主线程的控件内容
//                LoginActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 在这里把返回的数据写在控件上 会出现什么情况尼
//                        tv_result.setText(result);
//                    }
//                });

            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}