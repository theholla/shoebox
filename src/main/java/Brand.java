import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Brand {
  private int id;
  private String name;
  private int priciness;
  private int stylishness;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPriciness() {
    return priciness;
  }

  public int getStylishness() {
    return stylishness;
  }

  public Brand(String name, int priciness, int stylishness) {
    this.name = name;
    this.priciness = priciness;
    this.stylishness = stylishness;
  }

}
