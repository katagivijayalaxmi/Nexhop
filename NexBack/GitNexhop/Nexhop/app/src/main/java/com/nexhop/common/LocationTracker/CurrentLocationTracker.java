package com.nexhop.common.LocationTracker;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.nexhop.common.Constants;
import com.nexhop.model.LocationBean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vijayalaxmi on 16/3/15.
 */
public class CurrentLocationTracker implements LocationListener {
     Context ctx=null;
     public LocationBean locBean=null;
     double myLatitude=0, myLongitude=0;
    /**
     * Get the network info
     * @param ctx
     * @return
     */
    public CurrentLocationTracker(Context ctx)
    {
        if(null!=ctx){
            this.ctx=ctx;
            locBean=new LocationBean();
            LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if(null!=locationManager){
                Criteria criteria= new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
                criteria.setCostAllowed(true);
                String provider = locationManager.getBestProvider(criteria, true);

                getLocation(locationManager,provider);

                    if(!LocationUtility.isLocationEnabled(ctx)||locBean.getLatitude()==0.0 || locBean.getLongitude()==0.0)
                    {
                        getApproximateLatLong();
                    }


            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null)
        {
            locBean.setLatitude(location.getLatitude());
            locBean.setLongitude(location.getLatitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getLocation(LocationManager locationManager,String provider) {
            Location locations=	locationManager.getLastKnownLocation(provider);
            if(locations!=null)
            {
                locBean.setLatitude(locations.getLatitude());
                locBean.setLongitude(locations.getLongitude());
                locationManager.removeUpdates(this);
            }
    }

    public LocationBean getLatLong()
    {
        return locBean;
    }

   private void getApproximateLatLong()
   {
       TelephonyManager telephonyManager = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
       GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
       int cid = cellLocation.getCid();
       int lac = cellLocation.getLac();
       if(RqsLocation(cid, lac)){
           try {
               locBean.setLatitude(Double.parseDouble(String.valueOf((float) myLatitude / 1000000)));
               locBean.setLongitude(Double.parseDouble(String.valueOf((float) myLongitude / 1000000)));
           }catch(Exception e){
               locBean.setLatitude(Constants.LATITUDE);
               locBean.setLongitude(Constants.LONGITUDE);
               e.printStackTrace();}
       }
       else
       {
           locBean.setLatitude(Constants.LATITUDE);
           locBean.setLongitude(Constants.LONGITUDE);
       }
   }
    private Boolean RqsLocation(int cid, int lac){
        Boolean result = false;
        try {
            URL url = new URL(Constants.MAP_URL);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();
            OutputStream outputStream = httpConn.getOutputStream();
            WriteData(outputStream, cid, lac);
            InputStream inputStream = httpConn.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            dataInputStream.readShort();
            dataInputStream.readByte();
            int code = dataInputStream.readInt();
            if (code == 0) {
                myLatitude = (double)dataInputStream.readInt();
                myLongitude = (double)dataInputStream.readInt();
                result = true;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }
    private void WriteData(OutputStream out, int cid, int lac)
            throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeShort(21);
        dataOutputStream.writeLong(0);
        dataOutputStream.writeUTF("en");
        dataOutputStream.writeUTF("Android");
        dataOutputStream.writeUTF("1.0");
        dataOutputStream.writeUTF("Web");
        dataOutputStream.writeByte(27);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeUTF("");
        dataOutputStream.writeInt(cid);
        dataOutputStream.writeInt(lac);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.flush();
    }
}
