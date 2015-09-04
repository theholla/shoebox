import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void brand_instantiatesCorrectly_true() {
    Brand testBrand = new Brand("ShoeChic", 5, 3);
    assertEquals(true, testBrand instanceof Brand);
  }

}
