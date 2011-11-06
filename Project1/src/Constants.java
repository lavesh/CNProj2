
public enum Constants {
 TRANSMISSION_DELAY(80), PROPOGATION_DELAY(10), TRANSMISSION_TIME(90), FRAME_SLOT(500), T(10), NODE_A(0),NODE_B(1);
 private int code;

 private Constants(int c) {
   code = c;
 }

 public int getConstants() {
   return code;
 }
}