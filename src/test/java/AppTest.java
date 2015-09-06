import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @ClassRule
  public static DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource().contains("Shoe Box"));
  }

  @Test
    public void brandFormIsDisplayed() {
      goTo("http://localhost:4567/");
      click("a", withText("see your brands"));
      // Link text changes based on list size. Form works but test won't pass if list is empty.
      assertThat(pageSource()).contains("Hey shoebox,");
    }

    @Test
    public void storeFormIsDisplayed() {
      goTo("http://localhost:4567/");
      click("a", withText("add a store"));
      assertThat(pageSource()).contains("there's a new store");
    }

    @Test
    public void brandNameIsDisplayedInListWhenCreated() {
      goTo("http://localhost:4567/brands");
      fill("#name").with("Zeds");
      fill("#priciness").with("4");
      fill("#stylishness").with("5");
      submit(".btn");
      assertThat(pageSource()).contains("Zeds");
    }

    @Test
    public void storeCompanyIsDisplayedInListWhenCreated() {
      goTo("http://localhost:4567/stores");
      fill("#company").with("J.Shoo");
      submit(".btn");
      assertThat(pageSource()).contains("J.Shoo");
    }

    @Test
    public void brandHasItsOwnPage() {
      Brand testBrand = new Brand("Zoo", 5, 3);
      testBrand.save();
      goTo("http://localhost:4567/brands");
      click("a", withText("Zoo"));
      assertThat(pageSource()).contains("Brand: Zoo");
    }

    @Test
    public void storeHasItsOwnPage() {
      Store testStore = new Store("Shoe Palace", "(555) 555-5555", "124 NE Alphabet St");
      testStore.save();
      goTo("http://localhost:4567/stores");
      click("a", withText("Shoe Palace"));
      assertThat(pageSource()).contains("Store: Shoe Palace");
    }

}
