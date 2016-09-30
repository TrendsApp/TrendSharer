package trendsapps.org.trendsharer.JSONParser;

import org.json.*;
/**
 * Created by jawadhsr on 9/30/16.
 */
public class JSONParser {

    public JSONObject StringtoJSON(String object){
        JSONObject retValue = null;

        try {
            retValue = new JSONObject(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retValue;
    }
}
