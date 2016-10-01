package trendsapps.org.trendsharer.JSONParser;

import org.json.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import trendsapps.org.trendsharer.Model.Header;
import trendsapps.org.trendsharer.Model.Packet;
import trendsapps.org.trendsharer.Model.HotDeal;


public class JSONParser {


    private JSONObject HotDealtoJSON(HotDeal object){
        JSONObject hotDeal= null;
        try {
            hotDeal = new JSONObject();
            hotDeal.put("shopname",object.getShopName());
            hotDeal.put("content",object.getContent());
            hotDeal.put("discount",object.getDiscount());
            hotDeal.put("date",object.getStoredDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotDeal;
    }

    public JSONObject PacketToJSON(Packet object){
        JSONObject packetJSON = null;
        JSONObject header ;//= null;
        JSONArray  deals ;//= null;
        try{
            packetJSON = new JSONObject();
            header = new JSONObject();
            deals = new JSONArray();

            header.put("device_id",object.getHeader().getDeviceID());
            header.put("device_mac",object.getHeader().getDeviceMAC());

            ArrayList<HotDeal> hotDeals = object.getHotdeals();
            int i=0;
            for (HotDeal d : hotDeals) {
                deals.put(i,this.HotDealtoJSON(d));
                i++;
            }
            packetJSON.put("header",header);
            packetJSON.put("deals", deals);

        }catch (JSONException e){
            e.printStackTrace(); // for the development
        }

        return  packetJSON;
    }

    public Packet JSONtoPacket(JSONObject object){
        Packet newPacket = null;
        Header newHeader ;//= null;
        ArrayList<HotDeal> newDeals ;//= null;

        try{
            newPacket = new Packet();
            newHeader = new Header();
            newDeals = new ArrayList<>();
            JSONObject header;// = new JSONObject();
            JSONArray deals ;//= new JSONArray();
            header = object.getJSONObject("header");
            deals = object.getJSONArray("deals");


            // new header is set
            newHeader.setDeviceID(header.getInt("device_id"));
            newHeader.setDeviceMAC(header.getInt("device_mac"));
            newPacket.setHeader(newHeader);

            // new deals are set
            for(int i=0;i<deals.length();i++){

                JSONObject aDeal = deals.getJSONObject(i);
                HotDeal tempDeal = new HotDeal();
                tempDeal.setShopName(aDeal.getString("shop_name"));
                tempDeal.setContent(aDeal.getString("content"));
                tempDeal.setDiscount(aDeal.getString("discount"));
                tempDeal.setDuration(aDeal.getInt("duration"));
                tempDeal.setStoredDate((Timestamp) aDeal.get("timestamp"));
                newDeals.add(i,tempDeal);
            }
            newPacket.setHotdeals(newDeals);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return newPacket;
    }
}
