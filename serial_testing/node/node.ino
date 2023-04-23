#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>

// SoftwareSerial NodeMCU(D2, D3);

void setup() {
  Serial.begin(9600);
  // NodeMCU.begin(4800);
  // pinMode(D2, INPUT);
  pinMode(D3, OUTPUT);
}

void loop() {
  digitalWrite(D3, HIGH);
  delay(1000);
  digitalWrite(D3, LOW);
  delay(1000);
}