import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void brand_instantiatesCorrectly_true() {
    Brand testBrand = new Brand("Merrep", 3, 4);
    assertEquals(true, testBrand instanceof Brand);
  }

  @Test
  public void getName_returnsBrandName() {
    Brand testBrand = new Brand("LA Geared", 5, 3);
    assertEquals("LA Geared", testBrand.getName());
  }

  @Test
  public void all_listEmptyAtFirst_0() {
    assertEquals(Brand.listSize(), 0);
  }

  @Test
  public void all_listContainsAllBrands_1() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    assertEquals(Brand.listSize(), 1);
  }

  @Test
  public void equals_returnsTrueIfDescribedTheSame() {
    Brand testBrand = new Brand("Trucks", 4, 2);
    Brand nextBrand = new Brand("Trucks", 4, 2);
    assertTrue(testBrand.equals(nextBrand));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    Brand savedBrand = Brand.firstDBEntry();
    assertTrue(savedBrand.equals(testBrand));
  }

  @Test
  public void save_assignsIdToObject() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    Brand savedBrand = Brand.firstDBEntry();
    assertEquals(testBrand.getId(), savedBrand.getId());
  }

  @Test
  public void find_findsBrandInDatabase_true() {
    Brand testBrand = new Brand("Trucks", 4, 2);
    testBrand.save();
    Brand savedBrand = Brand.find(testBrand.getId());
    assertTrue(testBrand.equals(savedBrand));
  }

  @Test
  public void updateName_changesName() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    testBrand.updateName("KneeToFeet");
    assertEquals("KneeToFeet", Brand.firstDBEntry().getName());
  }

  @Test
  public void updateStylishness_changesStylishnessRating() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    testBrand.updateStylishness(4);
    assertEquals(4, Brand.firstDBEntry().getStylishness());
  }

  @Test
  public void updatePriciness_changesPricinessRating() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    testBrand.updatePriciness(4);
    assertEquals(4, Brand.firstDBEntry().getPriciness());
  }

  @Test
  public void delete_removesObjectFromDatabase() {
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    testBrand.delete();
    assertEquals(Brand.listSize(), 0);
  }

  @Test
  public void addStore_addsStoreToBrand() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();

    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();

    testBrand.addStore(testStore);
    Store savedStore = testBrand.getStores().get(0);
    assertTrue(testStore.equals(savedStore));
  }

  @Test
  public void getStores_returnsAllStores_ArrayList() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();

    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();

    testBrand.addStore(testStore);
    List savedStores = testBrand.getStores();
    assertEquals(savedStores.size(), 1);
  }

  @Test
  public void delete_deletesAllBrandsAndListAssociations() {
    Store testStore = new Store("RPI", "503-111-2222", "4333 NE First Ave");
    testStore.save();
    Brand testBrand = new Brand("Ankleys", 2, 1);
    testBrand.save();
    testBrand.addStore(testStore);
    testBrand.delete();
    assertEquals(testStore.getBrands().size(), 0);
  }


}
