package com.nexhop.common;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RestUrl
{
	String TAG = "RestUrl";


	public String queryRestUrl(Context context, String url, String requestType, List<NameValuePair> params)
	{
        Utilities.Debug(TAG,params.toString());
        try{
			if(null == context){
				return null;
			}
			HttpClient httpclient = new DefaultHttpClient();
			if (requestType == "post")
			{
				HttpPost post = new HttpPost(Constants.ROOT_URL + url);
				UrlEncodedFormEntity entity;
				try
				{
					entity = new UrlEncodedFormEntity(params, "UTF-8");
					post.setEntity(entity);
				}
				catch (UnsupportedEncodingException e1)
				{
					e1.printStackTrace();
				}
				try
				{
					HttpResponse response = httpclient.execute(post);
					HttpEntity entity1 = response.getEntity();
					if (entity1 != null)
					{
                        int serverResponseCode = 0;
                        serverResponseCode = response.getStatusLine().getStatusCode();
						switch (serverResponseCode)
						{
						case 200:
							InputStream instream = entity1.getContent();
							String result = Constants.EMPTY_STRING;
							result = convertStreamToString(instream);
							instream.close();
							return result;
						case 400:
							return "500" + "delimiter_" + Constants.POORCONNECTIVITY;
						case 500:
							return "500" + "delimiter_" + Constants.SERVERERROR;
						case 502:
							return "500" + "delimiter_" + Constants.SERVERERROR;
						case 503:
							return "500" + "delimiter_" + Constants.SERVERERROR;
						default:
							return "500" + "delimiter_" + response.getStatusLine().getStatusCode() + "Error Occured";
						}
					}
				}
				catch (ClientProtocolException e)
				{
					 e.printStackTrace();
					 return "500" + "delimiter_" + Constants.TECHNICALERROR;
				}
				catch (IOException e)
				{
					 e.printStackTrace();
					 return "500" + "delimiter_" + Constants.TECHNICALERROR +"";
				}
				catch (Exception e)
				{
					 e.printStackTrace();
					 return "500" + "delimiter_" + Constants.TECHNICALERROR +" Server Communication Failed";
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is), 2 * 1024);
		StringBuilder sb = new StringBuilder(2 * 1024);

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
