import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Store {
  private int id;
  private String company;
  private String phone;
  private String address;

  public int getId() {
    return id;
  }

  public String getCompany() {
    return company;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public Store(String company, String phone, String address) {
    this.company = company;
    this.phone = phone;
    this.address = address;
  }

  public static List<Store> all() {
    String sql = "SELECT DISTINCT ON (company) * FROM stores ORDER BY company ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  @Override
  public boolean equals(Object otherStore) {
    if (!(otherStore instanceof Store)) {
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getCompany().equals(newStore.getCompany()) &&
             this.getPhone().equals(newStore.getPhone()) &&
             this.getAddress().equals(newStore.getAddress()) &&
             this.getId() == newStore.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores (company, phone, address) VALUES (:company, :phone, :address)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("company", company)
      .addParameter("phone", phone)
      .addParameter("address", address)
      .executeUpdate()
      .getKey();
    }
  }

  public static Store find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stores where id=:id";
      Store store = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Store.class);
      return store;
    }
  }

  public void updateCompany(String company) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET company = :company WHERE id = :id";
      con.createQuery(sql)
      .addParameter("company", company)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updatePhone(String phone) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET phone = :phone WHERE id = :id";
      con.createQuery(sql)
      .addParameter("phone", phone)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateAddress(String address) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET address = :address WHERE id = :id";
      con.createQuery(sql)
      .addParameter("address", address)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM stores WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM stores_brands WHERE store_id = :store_id";
      con.createQuery(joinDeleteQuery)
      .addParameter("store_id", this.getId())
      .executeUpdate();
    }
  }

  public void addBrand(Brand brand) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (store_id, brand_id) VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
      .addParameter("store_id", this.getId())
      .addParameter("brand_id", brand.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Brand> getBrands() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT DISTINCT brand_id FROM stores_brands WHERE store_id = :store_id";

      List<Integer> brandIds = con.createQuery(sql)
      .addParameter("store_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Brand> brandsInStore = new ArrayList<Brand>();

      for (Integer brandId : brandIds) {
        String brandQuery = "SELECT DISTINCT ON (name) * FROM brands WHERE id = :brand_id";
        Brand brand = con.createQuery(brandQuery)
        .addParameter("brand_id", brandId)
        .executeAndFetchFirst(Brand.class);

        brandsInStore.add(brand);
      }
      return brandsInStore;
    }
  }

  public static Store firstDBEntry() {
    return all().get(0);
  }

  public static int listSize() {
    return all().size();
  }

}
