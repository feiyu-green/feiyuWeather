package com.feiyuWeather.app.util;

import com.feiyuWeather.app.model.City;
import com.feiyuWeather.app.model.County;
import com.feiyuWeather.app.model.Province;
import com.feiyuWeather.app.model.feiyuWeatherDB;

import android.text.TextUtils;

public class Utility {
/**
 * �����ʹ�����������ص�ʡ������
 */
	public synchronized static boolean handleProvincesResponse(feiyuWeatherDB feiyuWeatherDB,String response)
	{
		if(!TextUtils.isEmpty(response))
		{
			String[] allProvinces=response.split(",");
			if(allProvinces!=null&&allProvinces.length>0)
			{
				for(String p:allProvinces)
				{
					String[] array=p.split("\\|");
					Province province=new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//���������������ݴ洢��Province��
					feiyuWeatherDB.saveProvince(province);				
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * �����ʹ�����������ص��м�����
	 */
	public static boolean handleCitiesResponse(feiyuWeatherDB feiyuWeather, String response,int provinceId)
	{
	if(!TextUtils.isEmpty(response))
	{
		String[] allCities=response.split(",");
		if(allCities!=null&&allCities.length>0)
		{
			for(String c:allCities)
			{
				String[] array=c.split("\\|");
				City city=new City();
				city.setCityCode(array[0]);
				city.setCityName(array[1]);
				city.setProvinceId(provinceId);
				//���������������ݴ��浽city��
				feiyuWeather.saveCity(city);
			}
			return true;
		}
	}
		return false;
		
	}
	/**
	 * �����ʹ�����������ص��ؼ�����
	 */
	public static boolean handleCountiesResponse(feiyuWeatherDB feiyuWeatherDB,String response,int cityId)
	{
		if(!TextUtils.isEmpty(response))
		{
			String[] allCounties=response.split(",");
			if(allCounties!=null&&allCounties.length>0)
			{
				for(String c:allCounties)
				{
					String[] array=c.split("\\|");
					County county=new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//���������������ݴ洢��County��
					feiyuWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
		
	}
	
}
