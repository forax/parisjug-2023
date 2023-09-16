// $JAVA_HOME/bin/java --source 21 --enable-preview src/main/java/_3_sealed_types_instanceof.java

import java.util.List;

interface _3_sealed_types_instanceof {

  sealed interface MilitaryUnit {
  }

  record Soldier(String name, int firepower) implements MilitaryUnit {
  }

  record Carrier(List<MilitaryUnit> units) implements MilitaryUnit {
  }

  static int firepower(MilitaryUnit unit) {
    if (unit instanceof Soldier soldier) {
      return soldier.firepower();
    }
    if (unit instanceof Carrier carrier) {
      return carrier.units().stream()
          .mapToInt(u -> firepower(u))
          .sum();
    }
    throw new MatchException("oops", null);
  }

  static void main(String[] args) {
    var joe = new Soldier("Joe", 200);
    var jane = new Soldier("Jane", 200);
    var carrier = new Carrier(List.of(joe, jane));
    System.out.printf("""
        Jane firepower:    %d
        carrier firepower: %d
        """, firepower(jane), firepower(carrier));
  }
}