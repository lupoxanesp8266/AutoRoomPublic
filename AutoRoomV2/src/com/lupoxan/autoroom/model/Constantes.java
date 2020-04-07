/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


/**
 * Clase para recopilar todas las constantes que hay
 * @author lupo.xan
 * @since 24/09/2019
 * @version 0.9
 */
public class Constantes {
    public final static String TITULO = "appName";
    protected final static String FICHEROPROP = "/home/pi/autoRoom/Scripts/config.properties";
    protected final static String FICHEROPROP2 = "config.properties";
    protected final static String DATABASEURL = "dataBaseUrl";
    protected final static String CONFIGFIREBASE = "configJsonRaspi";
    protected final static String CONFIGFIREBASE2 = "configJsonWindows";
    protected final static String LOCALDATABASE = "localDataBaseUrl";
    protected final static String LOCALUSER = "localUserName";
    protected final static String LOCALPASS = "localPassword";
    protected final static String CLASSFORNAME = "nameOfClass";
    protected final static String RUTADATALOG = "dataLog";
    protected final static String ALERTACORREOS = "emailSender";
    protected final static String ALERTACORREOR = "emailReceiver";
    protected final static String PASSWORDCORREO = "password";
    protected final static String CONSUMERKEY = "claveUsuario";
    protected final static String CONSUMERSECRET = "secretoUsuario";
    protected final static String ACCESSTOKENKEY = "tokenAcceso";
    protected final static String ACCESSTOKENSECRET = "tokenSecreto";
    public static final String ARDUINO = "arduinoDevice";
    public static final String ZUM_BT = "zum_bt328";
    protected final static String EXTERIOR = "tempExt";
    protected final static String INTERIOR = "tempInt";
    public final static String LIGHTS = "iluminacion";
    protected final static String TITULOLIGHTS = "iluMinacion";
    public final static String AUTOLIGHTS = "autoIluminacion";
    protected final static String LIGHTSS = "lights";
    public final static String TABLE = "table";
    protected final static String BED = "bed";
    protected final static String UP = "up";
    public final static String LEDS = "leds";
    public final static String ROJOS = "ledsRojos";
    public final static String VERDES = "ledsVerdes";
    public final static String BLANCOL = "ledsBlancoL";
    public final static String BLANCOR = "ledsBlancoR";
    protected final static String LDSCAMA = "ledsCama";
    protected final static String ON = "on";
    protected final static String OFF = "off";
    protected final static String ONAC = "onAc";
    protected final static String OFFAC = "offAc";
    protected final static String PHOTO = "photoScript";
    public final static String WEBPAGE = "urlwebpage";
    public final static String LOGO = "rutalogo";
    protected final static String PORCENTAJE = "percent";
    protected final static String FADE = "modoFade";
    protected final static String BLINK = "modoBlink";
    protected final static String COOL = "modoCool";
    public final static String TIMERF = "timerFading";
    public final static String TIMERB = "timerBlinking";
    protected final static String LEDSTHREAD = "nombreThread";
    public final static String ERROR = "errorUsuario";
    protected final static String TITULOMENU = "tituloMenu";
    protected final static String TITULOLEDS = "tituloLeds";
    protected final static String TITULOINICIO = "tituloInicio";
    protected final static String TITULOCOMFORT = "tituloComfort";
    protected final static String TITULOSISTEMA = "tituloSistema";
    protected final static String TITULOGRAFICAS = "tituloGraficas";
    protected final static String COMFORT = "comfort";
    protected final static String COLD = "cold";
    protected final static String HOT = "hot";
    protected final static String MODO = "mode";
    protected final static String MILLIS = "millis";
    protected final static String TONE = "notificationRingTone";
    protected final static String ICON = "notificationIcon";
    protected final static String MOVEUPPER = "movimiento";
    protected final static String MOVELOWER = "movLower";
    protected final static String INTRUDER = "intruso";
    protected final static String SENSORS = "sensors";
    protected final static String HIGHUPPER = "highUpper";
    protected final static String TOKENSUSER = "tokens";
    protected final static String PORT = "puertoSocket";
    public static final String IP1 = "ip1";
    
    protected final static int TTL = 60000 * 60;//1Hora
    public final static int WAIT = 250;
    public final static int CERO = 0;
    public final static int HUNDRED = 100;
    public final static int MINUS = 30;
    protected final static int TIMERCOOL = 80;
    protected final static int ORED = 5;
    protected final static int OGREEN = 29;
    protected final static int OWHITEL = 16;
    protected final static int OWHITER = 25;
    protected final static int OWHITEC = 0;
    protected final static int LDR = 14;
    protected final static int LEDPWM = 3;
    protected static final int STICK = 17;
    
    protected final static boolean TRUE = true;
    protected final static boolean FALSE = false;
    
    protected final static Pin GENERAL = RaspiPin.GPIO_14;//CLOCK
    protected final static Pin MESA = RaspiPin.GPIO_09;//SCL
    protected final static Pin CAMA = RaspiPin.GPIO_08;//SDA
    protected final static Pin CLIMA = RaspiPin.GPIO_13;//MISO
    protected final static Pin MOV = RaspiPin.GPIO_24;
    protected static final Pin FAN = RaspiPin.GPIO_28;
    
    protected final static PinState HIGH = PinState.HIGH;
    protected final static PinState LOW = PinState.LOW;
    
    protected final static PinPullResistance RUP = PinPullResistance.PULL_UP;
    protected final static PinPullResistance RDOWN = PinPullResistance.PULL_DOWN;
	
	
	
	
}
