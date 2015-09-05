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

  public static List<Brand> all() {
    String sql = "SELEcT * FROM brands ORDER BY name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  @Override
  public boolean equals(Object otherBrand) {
    if (!(otherBrand instanceof Brand)) {
      return false;
    } else {
      Brand newBrand = (Brand) otherBrand;
      return this.getName().equals(newBrand.getName()) &&
             this.getPriciness() == newBrand.getPriciness() &&
             this.getStylishness() == newBrand.getStylishness() &&
             this.getId() == newBrand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO brands (name, priciness, stylishness) VALUES (:name, :priciness, :stylishness)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("priciness", priciness)
      .addParameter("stylishness", stylishness)
      .executeUpdate()
      .getKey();
    }
  }

  public static Brand find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM brands where id=:id";
      Brand brand = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Brand.class);
      return brand;
    }
  }

  public void updatePriciness(int priciness) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET priciness = :priciness WHERE id = :id";
      con.createQuery(sql)
      .addParameter("priciness", priciness)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateStylishness(int stylishness) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET stylishness = :stylishness WHERE id = :id";
      con.createQuery(sql)
      .addParameter("stylishness", stylishness)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM brands WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static Brand firstDBEntry() {
    return all().get(0);
  }

  public static int listSize() {
    return all().size();
  }

}
