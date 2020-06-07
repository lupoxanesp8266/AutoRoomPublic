package com.lupoxan.autoroom.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.controller.ChangeStateListeners;
import com.lupoxan.autoroom.model.LocalUser;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.6
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private MenuFrame menu;
	private LogInFrame login;
	private LightsFrame lights;
	private ComfortFrame comfort;
	private LedsFrame leds;
	private GraphsFrame graphs;
	private ToolsFrame tools;
	private FirebaseFrame firebaseFrame;
	private MariaDBFrame localFrame;
	private SensorsFrame sensorsFrame;
	private EntryFrame entryFrame;
	private LivingRoomFrame livingRoomFrame;
	private JPanel container;
	public static LocalUser user;

	public MainFrame(ActionListeners action, ChangeStateListeners changeStateListeners) throws HeadlessException {
		super();
		// Only for decorated
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("AutoRoom");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setIconImage(new ImageIcon("/home/pi/autoRoom/img/logoAutoRoom.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setUndecorated(true);
		container = new JPanel();
		container.setLayout(new CardLayout(10, 10));
		container.setBackground(Color.DARK_GRAY);
		this.setContentPane(container);

		login = new LogInFrame(action);
		container.add(login);

		menu = new MenuFrame(action);
		container.add(menu);

		lights = new LightsFrame(action);
		container.add(lights);

		comfort = new ComfortFrame(action);
		container.add(comfort);

		leds = new LedsFrame(action, changeStateListeners);
		container.add(leds);

		graphs = new GraphsFrame(action);
		container.add(graphs);

		tools = new ToolsFrame(action);
		container.add(tools);

		firebaseFrame = new FirebaseFrame(action);
		container.add(firebaseFrame);

		localFrame = new MariaDBFrame(action);
		container.add(localFrame);

		sensorsFrame = new SensorsFrame(action);
		container.add(sensorsFrame);
		
		entryFrame = new EntryFrame(action);
		container.add(entryFrame);
		
		livingRoomFrame = new LivingRoomFrame(action);
		container.add(livingRoomFrame);

		login.setVisible(true);

	}

	public MenuFrame getMenu() {
		return menu;
	}

	public void setMenu(MenuFrame menu) {
		this.menu = menu;
	}

	public LogInFrame getLogin() {
		return login;
	}

	public void setLogin(LogInFrame login) {
		this.login = login;
	}

	public LightsFrame getLights() {
		return lights;
	}

	public void setLights(LightsFrame lights) {
		this.lights = lights;
	}

	public ComfortFrame getComfort() {
		return comfort;
	}

	public void setComfort(ComfortFrame comfort) {
		this.comfort = comfort;
	}

	public LedsFrame getLeds() {
		return leds;
	}

	public void setLeds(LedsFrame leds) {
		this.leds = leds;
	}

	public GraphsFrame getGraphs() {
		return graphs;
	}

	public void setGraphs(GraphsFrame graphs) {
		this.graphs = graphs;
	}

	public ToolsFrame getTools() {
		return tools;
	}

	public void setTools(ToolsFrame tools) {
		this.tools = tools;
	}

	public FirebaseFrame getFirebaseFrame() {
		return firebaseFrame;
	}

	public void setFirebaseFrame(FirebaseFrame firebaseFrame) {
		this.firebaseFrame = firebaseFrame;
	}

	public MariaDBFrame getLocalFrame() {
		return localFrame;
	}

	public void setLocalFrame(MariaDBFrame localFrame) {
		this.localFrame = localFrame;
	}

	public SensorsFrame getSensorsFrame() {
		return sensorsFrame;
	}

	public void setSensorsFrame(SensorsFrame sensorsFrame) {
		this.sensorsFrame = sensorsFrame;
	}

	public EntryFrame getEntryFrame() {
		return entryFrame;
	}

	public void setEntryFrame(EntryFrame entryFrame) {
		this.entryFrame = entryFrame;
	}

	public LivingRoomFrame getLivingRoomFrame() {
		return livingRoomFrame;
	}

	public void setLivingRoomFrame(LivingRoomFrame livingRoomFrame) {
		this.livingRoomFrame = livingRoomFrame;
	}
	
	

}
