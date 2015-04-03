package com.nexhop.common.LocationTracker;

import android.text.TextUtils;

import com.nexhop.common.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Locale;
public class GetReverseGeoCoding
{
	 private String Address1 = "", Address2 = "", City = "", State = "", Country = "", County = "", PIN = "";
	 public void getAddress(double lat, double lon)
	 {
	        Address1 = Constants.EMPTY_STRING;
	        Address2 = Constants.EMPTY_STRING;
	        City = Constants.EMPTY_STRING;
	        State = Constants.EMPTY_STRING;
	        Country =Constants.EMPTY_STRING;
	        County = Constants.EMPTY_STRING;
	        PIN = Constants.EMPTY_STRING;
	        try
	        {
	        	//ttp://maps.googleapis.com/maps/api/geocode/json?latlng="+Double.toString(lat)+","+Double.toString(lng)+"&sensor=true&language="+Locale.getDefault().getCountry(), lat, lng);
	        	 JSONObject jsonObj = parser_JsonClass.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + Double.toString(lat) + ","
	                     				+ Double.toString(lon) + "&sensor=true&language="+Locale.getDefault().getCountry());
	        	 String Status = jsonObj.getString("status");
	             if (Status.equalsIgnoreCase("OK")) 
	             {
	                 JSONArray Results = jsonObj.getJSONArray("results");
	                 JSONObject zero = Results.getJSONObject(0);
	                 JSONArray address_components = zero.getJSONArray("address_components");
	                 for (int i = 0; i < address_components.length(); i++)
	                 {
	                     JSONObject zero2 = address_components.getJSONObject(i);
	                     String long_name = zero2.getString("long_name");
	                     JSONArray mtypes = zero2.getJSONArray("types");
	                     String Type = mtypes.getString(0);

	                     if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "")
	                     {
	                         if (Type.equalsIgnoreCase("street_number"))
	                         {
	                             Address1 = long_name + " ";
	                         } 
	                         else if (Type.equalsIgnoreCase("route")) 
	                         {
	                             Address1 = Address1 + long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("sublocality"))
	                         {
	                             Address2 = long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("locality"))
	                         {
	                             // Address2 = Address2 + long_name + ", ";
	                             City = long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("administrative_area_level_2"))
	                         {
	                             County = long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("administrative_area_level_1"))
	                         {
	                             State = long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("country")) 
	                         {
	                             Country = long_name;
	                         } 
	                         else if (Type.equalsIgnoreCase("postal_code"))
	                         {
	                             PIN = long_name;
	                         }
	                     }
	                 }
	             }
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	        
	 }

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCounty() {
		return County;
	}

	public void setCounty(String county) {
		County = county;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}
	 
	 
	 
}
