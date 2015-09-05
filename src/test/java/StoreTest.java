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
  public void getCompany_returnsStoreName() {
    Store testStore = new Store("Imeldons", "408-333-2222", "72 NW 45th Ave");
    assertEquals("Imeldons", testStore.getCompany());
  }

  @Test
  public void all_ListEmptyAtFirst_0() {
    assertEquals(Store.listSize(), 0);
  }

}
