package trendsapps.org.trendsharer.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class JSONMessage {
//    private int messageID;
//    private String deviceAddress;
//    private String shop;
//    private String discount;
//    private String content;
//    private int duration;
//    private String timeStamp;

    private JSONObject jsonObject;

    public JSONMessage(int messageID, String deviceAddress, String shop, String discount,
                       String content, int duration, String timeStamp) throws JSONException {

        JSONObject header = new JSONObject();
        header.put("messageID",messageID);
        header.put("deviceAddress",deviceAddress);
        JSONObject body = new JSONObject();
        body.put("shop", shop);
        body.put("discount", discount);
        body.put("content", content);
        body.put("duration", duration);
        body.put("timeStamp", timeStamp);
        jsonObject = new JSONObject();
        jsonObject.put("header", header);
        jsonObject.put("body", body);
    }

    public JSONObject getJSONMessage() {
        return jsonObject;
    }


}
