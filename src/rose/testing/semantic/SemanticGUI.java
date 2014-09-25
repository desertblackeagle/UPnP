package rose.testing.semantic;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SemanticGUI extends JFrame {
	private SemanticControlPoint cp;
	private JLabel title, tmp, MS, Notify;
	private JPanel MSTraditionalLCD, MSTraditionalTV, MSSemanticLight, MSSemanticLED;
	private JPanel NOTraditionalLCD, NOTraditionalTV, NOSemanticLight, NOSemanticLED;
	public JLabel MSTraditionalLCDResult, MSTraditionalTVResult, MSSemanticLightResult, MSSemanticLEDResult;
	public JLabel NOTraditionalLCDResult, NOTraditionalTVResult, NOSemanticLightResult, NOSemanticLEDResult;
	
	public SemanticGUI() {
		// TODO Auto-generated constructor stub
		setSize(500, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		init();
		cpInit();
		revalidate();
		repaint();
	}

	private void init() {
		title = new JLabel("Semantic Control Point");
		title.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		title.setBounds(150, 0, 300, 50);
		add(title);

		MS = new JLabel("M-Search");
		MS.setBounds(0, 50, 100, 30);
		MS.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(MS);

		tmp = new JLabel("LCD");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		MSTraditionalLCD = new JPanel();
		MSTraditionalLCD.add(tmp);
		MSTraditionalLCD.setBounds(50, 100, 100, 30);
		MSTraditionalTV = new JPanel();
		tmp = new JLabel("Parent TV");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		MSTraditionalTV.add(tmp);
		MSTraditionalTV.setBounds(50, 140, 100, 30);
		MSSemanticLight = new JPanel();
		tmp = new JLabel("Parent Light");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		MSSemanticLight.add(tmp);
		MSSemanticLight.setBounds(50, 180, 150, 30);
		MSSemanticLED = new JPanel();
		tmp = new JLabel("LED");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		MSSemanticLED.add(tmp);
		MSSemanticLED.setBounds(50, 220, 150, 30);
		add(MSTraditionalLCD);
		add(MSTraditionalTV);
		add(MSSemanticLight);
		add(MSSemanticLED);

		Notify = new JLabel("Invoke");
		Notify.setBounds(0, 270, 100, 30);
		Notify.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(Notify);

		tmp = new JLabel("LCD");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		NOTraditionalLCD = new JPanel();
		NOTraditionalLCD.add(tmp);
		NOTraditionalLCD.setBounds(50, 330, 100, 30);
		NOTraditionalTV = new JPanel();
		tmp = new JLabel("Parent TV");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		NOTraditionalTV.add(tmp);
		NOTraditionalTV.setBounds(50, 370, 100, 30);
		NOSemanticLight = new JPanel();
		tmp = new JLabel("Parent Light");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		NOSemanticLight.add(tmp);
		NOSemanticLight.setBounds(50, 410, 150, 30);
		NOSemanticLED = new JPanel();
		tmp = new JLabel("LED");
		tmp.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		NOSemanticLED.add(tmp);
		NOSemanticLED.setBounds(50, 450, 150, 30);
		add(NOTraditionalLCD);
		add(NOTraditionalTV);
		add(NOSemanticLight);
		add(NOSemanticLED);
		
		MSTraditionalLCDResult = new JLabel("失敗");
		MSTraditionalLCDResult.setBounds(250, 100, 200, 30);
		MSTraditionalLCDResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(MSTraditionalLCDResult);
		MSTraditionalTVResult = new JLabel("失敗");
		MSTraditionalTVResult.setBounds(250, 140, 200, 30);
		MSTraditionalTVResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(MSTraditionalTVResult);
		MSSemanticLightResult = new JLabel("失敗");
		MSSemanticLightResult.setBounds(250, 180, 200, 30);
		MSSemanticLightResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(MSSemanticLightResult);
		MSSemanticLEDResult = new JLabel("失敗");
		MSSemanticLEDResult.setBounds(250, 220, 200, 30);
		MSSemanticLEDResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(MSSemanticLEDResult);

		NOTraditionalLCDResult = new JLabel("失敗");
		NOTraditionalLCDResult.setBounds(250, 330, 200, 30);
		NOTraditionalLCDResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(NOTraditionalLCDResult);
		NOTraditionalTVResult = new JLabel("失敗");
		NOTraditionalTVResult.setBounds(250, 370, 200, 30);
		NOTraditionalTVResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(NOTraditionalTVResult);
		NOSemanticLightResult = new JLabel("失敗");
		NOSemanticLightResult.setBounds(250, 410, 200, 30);
		NOSemanticLightResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(NOSemanticLightResult);
		NOSemanticLEDResult = new JLabel("失敗");
		NOSemanticLEDResult.setBounds(250, 450, 200, 30);
		NOSemanticLEDResult.setFont(new Font("傳統UPnP", Font.BOLD, 20));
		add(NOSemanticLEDResult);
	}

	private void cpInit() {
		cp = new SemanticControlPoint(this);
		cp.start();
		cp.searchTraditional();
		cp.searchSemantic();
		cp.invokeTraditional();
		cp.invokeSemantic();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SemanticGUI semanticGUI = new SemanticGUI();
	}

}
