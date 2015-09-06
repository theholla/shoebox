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
    String sql = "SELECT DISTINCT ON (name) * FROM brands ORDER BY name ASC";
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

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
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

      String joinDeleteQuery = "DELETE FROM stores_brands WHERE brand_id = :brandId";
      con.createQuery(joinDeleteQuery)
      .addParameter("brandId", this.getId())
      .executeUpdate();
    }
  }

  public void addStore(Store store) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (store_id, brand_id) VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
      .addParameter("store_id", store.getId())
      .addParameter("brand_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Store> getStores() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT store_id FROM stores_brands WHERE brand_id = :brand_id";
      List<Integer> storeIds = con.createQuery(sql)
      .addParameter("brand_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Store> stores = new ArrayList<Store>();

      for (Integer storeId : storeIds) {
        String brandQuery = "SELECT DISTINCT ON (company) * FROM stores WHERE id = :storeId";
        Store store = con.createQuery(brandQuery)
        .addParameter("storeId", storeId)
        .executeAndFetchFirst(Store.class);
        stores.add(store);
      }
      return stores;
    }
  }

  public static Brand firstDBEntry() {
    return all().get(0);
  }

  public static int listSize() {
    return all().size();
  }

}
