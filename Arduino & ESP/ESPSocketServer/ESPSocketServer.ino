/**
 * Este programa pretende crear un servidor para un esp32
 * el cual pueda transmitir y recibir datos de la raspberry
 * así podremos controlar tanto entradas como salidas
 * 
 * @since 05/04/2020
 * @author lupo.xan
 * @version 0.3
 */
#include <WiFi.h>

const char* ssid = "**************";
const char* password =  "**********";

WiFiServer server(*****);

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
    pinMode(35,INPUT);
    server.begin();

}

void loop(){
 WiFiClient client = server.available();   // listen for incoming clients

  if (client) {                             // if you get a client,
    Serial.println("New Client.");           // print a message out the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {            // loop while the client's connected
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();
        if(c == 'H'){
          digitalWrite(13,HIGH);
        }
        if(c == 'L'){
          digitalWrite(13,LOW);
        }
        client.print("Value: ");
        client.println(analogRead(35));
        
        Serial.println("Recibido: ");
        Serial.println(c);

      }
    }
    // close the connection:
    client.stop();
    Serial.println("Client Disconnected.");
  }
}
