import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /* Index */
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Brands */
    get("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Brand> brands = Brand.all();
      model.put("brands", brands);
      // model.put("allStores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Stores */
    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      // model.put("allBrands", Brand.all());
      model.put("template", "/templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
