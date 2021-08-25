package edu.escuelaing.WebApp;

import spark.Request;
import spark.Response;
import java.io.IOException;
import static spark.Spark.*;
import edu.escuelaing.WebApp.HttpConnectionExample;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

  private static ConcurrentHashMap<String, String> alphavantageCache = new ConcurrentHashMap<>();
  private static ConcurrentHashMap<String, String> iextradingCache = new ConcurrentHashMap<>();


  public static void main(String[] args) {
    port(getPort());
    staticFiles.location("/public");
    get("/alphavantage", (req, res) -> alphavantagePage(req, res));
    get("/iextrading", (req, res) -> iextradingPage(req, res));
  }

  private static JSONObject alphavantagePage(Request req, Response res) throws ParseException {

    String result = "None";
    String id = req.queryParams("id");
    HttpConnectionExample connection = new HttpConnectionExample();

    if (!alphavantageCache.containsKey(id)) {
      try {
        result = connection.getAlphavantageResponse(id);
        alphavantageCache.put(id, result);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    result = alphavantageCache.get(id);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(result);
    return json;
  }

  private static String iextradingPage(Request req, Response res) {

    String result = "None";
    String id = req.queryParams("id");
    HttpConnectionExample connection = new HttpConnectionExample();

    if (!iextradingCache.containsKey(id)) {
      try {
        result = connection.getIextradingResponse(id);
        iextradingCache.put(id, result);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    result = iextradingCache.get(id);
    return result;
  }

  private static int getPort()
  {
    if (System.getenv("PORT") != null)
    {
      return Integer.parseInt(System.getenv("PORT"));
    }
    return 4567;
  }
}
