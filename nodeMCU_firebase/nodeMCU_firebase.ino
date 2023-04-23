#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

#define FIREBASE_HOST "boxing-project-6f4bd-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "AIzaSyBbM8KNJGjWTcHjW7Y396tfT_q0kR1_N1k"
#define WIFI_SSID "shavinda"
#define WIFI_PASSWORD "shavinda@2001"

FirebaseData firebaseData;

String read_combinations = "";
String temp_read_confirmations = "";

void setup() {

  Serial.begin(9600);

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


  // if (send_data == "ON")
  //   send_data = "OFF";
  // else
  //   send_data = "ON";

  // if (Firebase.setString(firebaseData, "/data", send_data)) {

  //   Serial.print("data ");
  //   Serial.print("'");
  //   Serial.print(send_data);
  //   Serial.print("'");
  //   Serial.println(" Uploaded Successfully");

  // }

  // else {
  //   Serial.println(firebaseData.errorReason());
  // }

  // delay(1000);

  if (Firebase.getString(firebaseData, "/combinations")) {

    read_combinations = firebaseData.stringData();

    if (read_combinations.equals(temp_read_confirmations)) {

      //same data is passed so do not send combinations
      Serial.print("not updated - ");
      Serial.println(read_combinations);

    } else if (!(read_combinations.equals(temp_read_confirmations))) {

      //send the new values now
      Serial.print("data is updated - ");
      Serial.println(read_combinations);
      delay(5000);
      temp_read_confirmations = read_combinations;
    }
    Serial.println();

  } else {
    Serial.println(firebaseData.errorReason());
  }
  delay(100);
}