#include <SoftwareSerial.h>
SoftwareSerial ArduinoUno(12, 11);
int mode;
String reading;
char charBuf[10];


void setup() {

  Serial.begin(9600);
  // ArduinoUno.begin(4800);
}

void loop() {


  while (ArduinoUno.available() > 0) {
    reading = ArduinoUno.readStringUntil('\n');
    if (reading.toInt() < 5) {
      mode = reading.toInt();
    }
    if (mode == 2 && reading.length() > 1) {
      reading.toCharArray(charBuf, 10);
    }
    //get any data like this
  }

  switch (mode) {
    case 1:
      Serial.println("now in mode 1 free mode");
      break;
    case 2:
      Serial.println("now in mode 2 combinations");
      Serial.print("combination is - ");
      Serial.println(String(charBuf));
      break;
    case 3:
      Serial.println("now in mode 3 bla bla");
      break;
  }
  delay(1000);

  // int a = digitalRead(12);
  // Serial.println(a);

  // if (a == 1) {
  //   Serial.println("hehe");
  // }
}
