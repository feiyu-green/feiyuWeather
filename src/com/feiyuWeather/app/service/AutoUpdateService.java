package com.feiyuWeather.app.service;

import com.feiyuWeather.app.receiver.AutoUpdateReceiver;
import com.feiyuWeather.app.util.HttpCallbackListener;
import com.feiyuWeather.app.util.HttpUtil;
import com.feiyuWeather.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
 public int onStartCommand(Intent intent,int flags,int startId)
 {
	 new Thread(new Runnable(){
		 public void run()
		 {
			 updateWeather();
		 }
		 
	 }).start();
	 AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
	 int anHour=8*60*60*1000;//8小时
	 long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
	 Intent i=new Intent(this,AutoUpdateReceiver.class);
	 PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
	 manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,pi);
	 return super.onStartCommand(intent, flags, startId);

 }
 /**
  * 更新天气信息
  */
 protected void updateWeather() {
	// TODO Auto-generated method stub
	SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
	String weatherCode=prefs.getString("weather_code", "");
	String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
	HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

		@Override
		public void onFinish(String response) {
			// TODO Auto-generated method stub
			Utility.handleWeatherResponse(AutoUpdateService.this, response);
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}});
}

}
