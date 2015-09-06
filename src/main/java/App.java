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
      model.put("stores", Store.all());
      model.put("brands", Brand.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> list of Brands */
    get("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> list of Stores */
    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "/templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  /* List of Stores -> POST a new store*/
    post("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String company = request.queryParams("company");
      String phone = request.queryParams("phone");
      String address = request.queryParams("address");
      Store newStore = new Store(company, phone, address);
      newStore.save();
      response.redirect("/stores");
      return null;
    });

    /* List of Brands -> POST a new brand*/
    post("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int priciness = Integer.parseInt(request.queryParams("priciness"));
      int stylishness = Integer.parseInt(request.queryParams("stylishness"));
      Brand newBrand = new Brand(name, priciness, stylishness);
      newBrand.save();
      response.redirect("/brands");
      return null;
    });

    /* List of stores --> view an individual store */
    get("/stores/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store store = Store.find(id);
      model.put("store", store);
      model.put("brands", Brand.all());
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* List of brands --> view an individual brand */
    get("/brands/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand brand = Brand.find(id);
      model.put("brand", brand);
      model.put("stores", Store.all());
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Individual brand --> POST a store */
    post("/add_stores", (request, response) -> {
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store store = Store.find(storeId);
      Brand brand = Brand.find(brandId);
      brand.addStore(store);
      response.redirect("/brands/" + brandId);
      return null;
    });

    /* Individual store --> POST a brand */
    post("/add_brands", (request, response) -> {
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      Brand brand = Brand.find(brandId);
      Store store = Store.find(storeId);
      store.addBrand(brand);
      response.redirect("/stores/" + storeId);
      return null;
    });

    /* Individual brand --> get edit page */
    get("/brands/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand editBrand = Brand.find(id);
      model.put("editBrand", editBrand);
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Brand edit page --> UPDATE details */
    post("/brands/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand editBrand = Brand.find(id);
      String name = request.queryParams("name");
      int priciness = Integer.parseInt(request.queryParams("priciness"));
      int stylishness = Integer.parseInt(request.queryParams("stylishness"));
      editBrand.updateName(name);
      editBrand.updatePriciness(priciness);
      editBrand.updateStylishness(stylishness);
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Individual store --> get edit page */
    get("/stores/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store editStore = Store.find(id);
      model.put("editStore", editStore);
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Store edit page --> UPDATE details */
    post("/stores/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store editStore = Store.find(id);
      String company = request.queryParams("company");
      String phone = request.queryParams("phone");
      String address = request.queryParams("address");
      editStore.updateCompany(company);
      editStore.updatePhone(phone);
      editStore.updateAddress(address);
      model.put("brands", Brand.all());
      model.put("stores", Store.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /*  List of stores --> DELETE store */
    get("/stores/:id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store deleteStore = Store.find(id);
      deleteStore.delete();
      model.put("store", Store.all());
      model.put("brand", Brand.all());
      response.redirect("/stores");
      return null;
    });

  }
}
