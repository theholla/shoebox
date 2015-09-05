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
    Brand myBrand = new Brand("Ankleys", 2, 1);
    myBrand.save();
    Brand savedBrand = Brand.firstDBEntry();
    assertEquals(myBrand.getId(), savedBrand.getId());
  }

  @Test
  public void find_findsBrandInDatabase_true() {
    Brand myBrand = new Brand("Trucks", 4, 2);
    myBrand.save();
    Brand savedBrand = Brand.find(myBrand.getId());
    assertTrue(myBrand.equals(savedBrand));
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
    // Store myStore = new Store("Mark Twain");
    // myStore.save();
    //
    // Brand myBrand = new Brand("Huckleberry Finn");
    // myBrand.save();
    //
    // myBrand.addStore(myStore);
    // Store savedStore = myBrand.getStores().get(0);
    // assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void getStores_returnsAllStores_ArrayList() {
    // Store myStore = new Store("Mark Twain");
    // myStore.save();
    //
    // Brand myBrand = new Brand("Huckleberry Finn");
    // myBrand.save();
    //
    // myBrand.addStore(myStore);
    // List savedStores = myBrand.getStores();
    // assertEquals(savedStores.size(), 1);
  }

  @Test
  public void delete_deletesAllBrandsAndListAssociations() {
    // Store myStore = new Store("Mark Twain");
    // myStore.save();
    //
    // Brand myBrand = new Brand("Huckleberry Finn");
    // myBrand.save();
    //
    // myBrand.addStore(myStore);
    // myBrand.delete();
    // assertEquals(myStore.getBrands().size(), 0);
  }


}
