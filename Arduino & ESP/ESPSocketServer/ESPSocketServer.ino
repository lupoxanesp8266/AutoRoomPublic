#include <WiFi.h>
#include "DHTesp.h"

#define DHTpin 12    //D15 of ESP32 DevKit

const char* ssid = "***********";
const char* password =  "**********";

WiFiServer server(5555);
DHTesp dht;

void setup()
{
    Serial.begin(115200);

    delay(10);

    Serial.println();
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);

    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
    pinMode(13,OUTPUT);
    server.begin();
    dht.setup(DHTpin, DHTesp::DHT11); //for DHT11 Connect DHT sensor to GPIO 17
  //dht.setup(DHTpin, DHTesp::DHT22); //for DHT22 Connect DHT sensor to GPIO 17

}

void loop(){
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
            client.println(cadena);
          break;
        }
      }
    }
    client.stop();
    Serial.println("Client Disconnected.");
  }
}
