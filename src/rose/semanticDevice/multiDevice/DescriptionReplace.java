package rose.semanticDevice.multiDevice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DescriptionReplace {

	public DescriptionReplace(String path, int num) {
		// TODO Auto-generated constructor stub
		for (int i = 2; i <= num; i++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(path)));
				String path2 = path.substring(0, path.length() - 5);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path2 + i + ".xml")));
				String tmp = "";
				while ((tmp = br.readLine()) != null) {
					tmp = tmp.replace("<deviceType>urn:schemas-upnp-org:device:MultiDevice:1</deviceType>", "<deviceType>urn:schemas-upnp-org:device:MultiDevice:" + i + "</deviceType>");
					tmp = tmp.replace("<UDN>uuid:fcuMultiDevice1</UDN>", "<UDN>uuid:fcuMultiDevice" + i + "</UDN>");
					bw.write(tmp);
				}
				bw.close();
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DescriptionReplace dr = new DescriptionReplace("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description1.xml", 10);
	}

}
