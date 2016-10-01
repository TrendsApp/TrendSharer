package trendsapps.org.trendsharer.JSONParser;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rangathara on 10/1/16.
 */

public class JSONMessage {
//    private int messageID;
//    private String deviceAddress;
//    private String shop;
//    private String discount;
//    private String content;
//    private int duration;
//    private String timeStamp;
    private JSONObject header;
    private JSONObject body;
    private JSONObject message;
    private Map<String, Object> headerMap;
    private Map<String, Object> bodyMap;
    private Map<String, Object> messageMap;

    public JSONMessage(int messageID, String deviceAddress, String shop, String discount,
                       String content, int duration, String timeStamp) {
        headerMap = new HashMap<>();
        bodyMap = new HashMap<>();
        messageMap = new HashMap<>();
        headerMap.put("messageId", messageID);
        headerMap.put("deviceAddress", deviceAddress);
        bodyMap.put("shop", shop);
        bodyMap.put("discount", discount);
        bodyMap.put("content", content);
        bodyMap.put("duration", duration);
        bodyMap.put("timeStamp", timeStamp);
        header = new JSONObject(headerMap);
        body = new JSONObject(bodyMap);
        messageMap.put("header", header);
        messageMap.put("body", body);
        message = new JSONObject(messageMap);
    }

    public JSONObject getJSONMessage() {
        return message;
    }
}
