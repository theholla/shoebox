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
    Store myStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    myStore.save();
    Store savedStore = Store.firstDBEntry();
    assertEquals(myStore.getId(), savedStore.getId());
  }

  @Test
  public void find_findsStoreInDatabase_true() {
    Store myStore = new Store("Zapatos", "1-800-333-3333", "New York, New York");
    myStore.save();
    Store savedStore = Store.find(myStore.getId());
    assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void delete_removesObjectFromDatabase() {
    Store testStore = new Store("Zapatos", "1-800-333-3333", "New York, New York");
    testStore.save();
    testStore.delete();
    assertEquals(Store.listSize(), 0);
  }


}
