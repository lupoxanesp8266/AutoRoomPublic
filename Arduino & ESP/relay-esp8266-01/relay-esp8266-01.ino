#include <ESP8266WiFi.h>

#ifndef STASSID
#define STASSID "********"
#define STAPSK  "********"
#endif

const char* ssid = STASSID;
const char* password = STAPSK;

WiFiServer server(5555);
void setup() {
  Serial.begin(9600);
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.print("RSSI: ");
  Serial.print(WiFi.RSSI());
  Serial.println(" dbm");
  
  server.begin();
}

void loop() {
  if(!WiFi.isConnected()){
    WiFi.reconnect();
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
  }
  WiFiClient client = server.available(); 

  if (client) {                    
    Serial.println("New Client."); 
    while (client.connected()) {         
      if (client.available()) {
        char c = client.read();
        switch(c){
          case 'H':
            relayOn();
            client.println("on");
          break;
          case 'L':
            relayOff();
            client.println("off");
          break;
          case 'I':
            client.println((String)WiFi.RSSI());
          break;
        }
      }
    }
    client.stop();
    Serial.println("Client Disconnected.");
  }
}

void relayOn(){
  const byte relayOn[] = {0xA0, 0x01, 0x01, 0xA2};
  Serial.write(relayOn,sizeof(relayOn));
}

void relayOff(){
  const byte relayOff[] = {0xA0, 0x01, 0x00, 0xA1};
  Serial.write(relayOff,sizeof(relayOff));
}
