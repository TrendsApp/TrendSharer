package trendsapps.org.trendsharer.Model;

import java.util.ArrayList;

public class Packet {

    private Header header;
    private ArrayList<HotDeal> hotdeals;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArrayList<HotDeal> getHotdeals() {
        return hotdeals;
    }

    public void setHotdeals(ArrayList<HotDeal> hotdeals) {
        this.hotdeals = hotdeals;
    }
}
