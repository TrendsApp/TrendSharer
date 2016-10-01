package trendsapps.org.trendsharer.Model;

/**
 * Created by jawadhsr on 10/1/16.
 */
public class Packet {

    private int packetId;
    private HotDeal[] hotdeals;

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public HotDeal[] getHotdeals() {
        return hotdeals;
    }

    public void setHotdeals(HotDeal[] hotdeals) {
        this.hotdeals = hotdeals;
    }
}
