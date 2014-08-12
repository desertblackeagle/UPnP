package fcu.upnp;

import java.util.UUID;

import org.cybergarage.upnp.UPnP;
import org.cybergarage.upnp.device.NTS;
import org.cybergarage.upnp.ssdp.SSDPNotifyRequest;
import org.cybergarage.upnp.ssdp.SSDPNotifySocket;

public class NotifyTest
{

    public static void main(String[] args)
    {
        String MY_UUID = UUID.randomUUID().toString();

        SSDPNotifySocket ssdpSock = new SSDPNotifySocket("192.168.4.130");

        SSDPNotifyRequest ssdpReq = new SSDPNotifyRequest();
        ssdpReq.setServer(UPnP.getServerName());
        ssdpReq.setLeaseTime(120);
        ssdpReq.setLocation("http://192.168.4.130:11120/rootDesc.xml");
        ssdpReq.setNTS(NTS.ALIVE);

        // uuid:device-UUID(::upnp:rootdevice)*
        ssdpReq.setNT("upnp:rootdevice");
        ssdpReq.setUSN("uuid:" + MY_UUID + "::upnp:rootdevice");
        ssdpSock.post(ssdpReq);

        ssdpReq.setNT("urn:schemas-upnp-org:device:InternetGatewayDevice:2");
        ssdpReq.setUSN("uuid:"+MY_UUID+"::urn:schemas-upnp-org:device:InternetGatewayDevice:2");
        ssdpSock.post(ssdpReq);

        ssdpSock.close();
    }

}
