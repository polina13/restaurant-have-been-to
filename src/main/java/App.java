import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object>model =  new HashMap<String, Object>();
      model.put("restaurants", request.session().attribute("restaurants"));

      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants", (request, response) -> {
      HashMap<String, Object>model= new HashMap<String, Object>();

      ArrayList<Restaurants> restaurants = request.session().attribute("restaurants");

      if ( restaurants == null) {
        restaurants = new ArrayList<Restaurants>();
        request.session().attribute("restaurants", restaurants);
      }

      String restaurantName = request.queryParams("restaurantName");
      Restaurants newRestaurant = new Restaurants(restaurantName);

      restaurants.add(newRestaurant);

      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
