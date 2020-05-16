
#include <WiFi.h>
#include "DHTesp.h"

#define DHTpin 14    //D15 of ESP32 DevKit

const char* ssid = "**********";
const char* password =  "*********";

WiFiServer server(5555);
DHTesp dht;

void setup()
{
    Serial.begin(115200);
    pinMode(2,OUTPUT);
    delay(10);

    Serial.println();
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);

    WiFi.begin(ssid, password);
    digitalWrite(2,HIGH);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
    digitalWrite(2,LOW);
    pinMode(13,OUTPUT);
    server.begin();
    dht.setup(DHTpin, DHTesp::DHT11); //for DHT11 Connect DHT sensor to GPIO 17
  //dht.setup(DHTpin, DHTesp::DHT22); //for DHT22 Connect DHT sensor to GPIO 17

}

void loop(){
  if(!WiFi.isConnected()){
    digitalWrite(2,HIGH);
    WiFi.reconnect();
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
    digitalWrite(2,LOW);
  }
 WiFiClient client = server.available(); 

  if (client) {                    
    Serial.println("New Client."); 
    while (client.connected()) {         
      if (client.available()) {
        char c = client.read();
        switch(c){
          case 'H':
            digitalWrite(13,HIGH);
            client.println("on");
          break;
          case 'L':
            digitalWrite(13,LOW);
            client.println("off");
          break;
          case 'D':
            float humidity = dht.getHumidity();
            float temperature = dht.getTemperature();
            delay(100);
            String cadena = dht.getStatusString();
            cadena += ",";
            cadena += temperature;
            cadena += ",";
            cadena += humidity;
            cadena += ",";
            cadena += dht.computeHeatIndex(temperature, humidity, false);
            cadena += ",";
            cadena += (String) WiFi.RSSI();
            client.println(cadena);
          break;
        }
      }
    }
    client.stop();
    Serial.println("Client Disconnected.");
  }
}
