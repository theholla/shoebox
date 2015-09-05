import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test public void store_instantiatesCorrectly_true() {
    Store testStore = new Store("Imeldons", "408-333-2222", "72 NW 45th Ave");
    assertEquals(true, testStore instanceof Store);
  }

  @Test
  public void getCompany_returnsStoreCompany() {
    Store testStore = new Store("Imeldons", "408-333-2222", "72 NW 45th Ave");
    assertEquals("Imeldons", testStore.getCompany());
  }

  @Test
  public void all_ListEmptyAtFirst_0() {
    assertEquals(Store.listSize(), 0);
  }

  @Test
  public void all_listContainsAllStores_1() {
    Store testStore = new Store("Next Horizon", "503-444-3333", "71 SE Marlin Looper Drive");
    testStore.save();
    assertEquals(Store.listSize(), 1);
  }

  @Test
  public void equals_returnsTrueIfDescribedTheSame() {
    Store testStore = new Store("Next Horizon", "503-444-3333", "71 SE Marlin Looper Drive");
    Store nextStore = new Store("Next Horizon", "503-444-3333", "71 SE Marlin Looper Drive");
    assertTrue(testStore.equals(nextStore));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();
    Store savedStore = Store.firstDBEntry();
    assertTrue(savedStore.equals(testStore));
  }

  @Test
  public void save_assignsIdToObject() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();
    Store savedStore = Store.firstDBEntry();
    assertEquals(testStore.getId(), savedStore.getId());
  }

  @Test
  public void find_findsStoreInDatabase_true() {
    Store testStore = new Store("Zapatos", "1-800-333-3333", "New York, New York");
    testStore.save();
    Store savedStore = Store.find(testStore.getId());
    assertTrue(testStore.equals(savedStore));
  }

  @Test
  public void updatePhone_changesPhone() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();
    testStore.updatePhone("503-000-2222");
    assertEquals("503-000-2222", Store.firstDBEntry().getPhone());
  }

  @Test
  public void updateAddress_changesAddress() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();
    testStore.updateAddress("4333 NE Second Ave");
    assertEquals("4333 NE Second Ave", Store.firstDBEntry().getAddress());
  }


  @Test
  public void delete_removesObjectFromDatabase() {
    Store testStore = new Store("Zapatos", "1-800-333-3333", "New York, New York");
    testStore.save();
    testStore.delete();
    assertEquals(0, Store.listSize());
  }


  @Test
  public void addBrand_addsBrandToStore() {
    Store testStore = new Store("Imeldons", "408-333-2222", "72 NW 45th Ave");
    testStore.save();
    Brand testBrand = new Brand("Trucks", 4, 2);
    testBrand.save();
    testStore.addBrand(testBrand);
    Brand savedBrand = testStore.getBrands().get(0);
    assertTrue(testBrand.equals(savedBrand));
  }

  @Test
  public void getBrands_returnsAllBrands_ArrayList() {
    Store testStore = new Store("Imeldons", "408-333-2222", "72 NW 45th Ave");
    testStore.save();
    Brand testBrand = new Brand("Trucks", 4, 2);
    testBrand.save();
    testStore.addBrand(testBrand);
    List savedBrands = testStore.getBrands();
    assertEquals(1, savedBrands.size());
  }

  @Test
  public void delete_deletesAllListAssociations() {
    Store testStore = new Store("Zapatos", "1-800-333-3333", "New York, New York");
    testStore.save();
    Brand testBrand = new Brand("Trucks", 4, 2);
    testBrand.save();
    testStore.addBrand(testBrand);
    testStore.delete();
    assertEquals(0, testBrand.getStores().size());
  }

}
