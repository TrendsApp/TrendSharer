package trendsapps.org.trendsharer.JSONParser;

import org.json.*;

import trendsapps.org.trendsharer.Model.Packet;
import trendsapps.org.trendsharer.Model.HotDeal;

/**
 * Created by jawadhsr on 9/30/16.
 */
public class JSONParser {


    public JSONObject ObjecttoJSON(HotDeal object){
        JSONObject hotDeal= null;
        JSONObject fullObject = null;
        try {
            fullObject = new JSONObject();
            hotDeal = new JSONObject();
            hotDeal.put("shopname",object.getShopName());
            hotDeal.put("content",object.getContent());
            hotDeal.put("discount",object.getDiscount());
            hotDeal.put("date",object.getStoredDate());

            fullObject.put("hotdeal",hotDeal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotDeal;
    }

    public JSONObject fullObjtoJSON(Packet object){
        JSONObject packet = null;

        try{
            packet = new JSONObject();

            packet.put("id",object.getPacketId());
        }catch (JSONException e){
            e.printStackTrace(); // for the development
        }

        return  packet;
    }
}
