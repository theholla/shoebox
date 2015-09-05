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
    String sql = "SELECT * FROM stores ORDER BY company ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  public static Store firstDBEntry() {
    return all().get(0);
  }

  public static int listSize() {
    return all().size();
  }

}
