#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <SoftwareSerial.h>

#define FIREBASE_HOST "boxing-project-6f4bd-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "AIzaSyBbM8KNJGjWTcHjW7Y396tfT_q0kR1_N1k"
#define WIFI_SSID "ud_ara_99"
#define WIFI_PASSWORD "udara1234"


SoftwareSerial NodeMCU(D2, D3);
FirebaseData firebaseData;

String read_combinations = "";
String temp_read_confirmations = "";
String reading;

void setup() {

  //setting serial pins
  Serial.begin(9600);
  NodeMCU.begin(4800);
  pinMode(D2, INPUT);
  pinMode(D3, OUTPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }

  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  Firebase.reconnectWiFi(true);

  Serial.println();
  delay(500);
}

void loop() {

  while (NodeMCU.available() > 0) {
    reading = NodeMCU.readStringUntil('\n');
    delay(10);
    if (Firebase.setString(firebaseData, "/reaction", reading)) {
      Serial.println(" Uploaded Successfully");
    } else {
      Serial.println(firebaseData.errorReason());
    }
  }

  if (Firebase.getString(firebaseData, "/combinations")) {

    read_combinations = firebaseData.stringData();

    if (read_combinations.equals(temp_read_confirmations)) {

      //same data is passed so do not send combinations
      Serial.print("not updated - ");
      Serial.println(read_combinations);

    } else if (!(read_combinations.equals(temp_read_confirmations))) {

      //send the new values now
      Serial.print("data is updated - ");
      delay(100);
      Serial.println(read_combinations);
      NodeMCU.print(read_combinations);
      NodeMCU.print("\n");
      temp_read_confirmations = read_combinations;
    }
    Serial.println();

  } else {
    Serial.println(firebaseData.errorReason());
  }
  delay(100);
}
