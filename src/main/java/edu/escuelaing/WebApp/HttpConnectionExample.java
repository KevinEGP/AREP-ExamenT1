package edu.escuelaing.WebApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionExample {

    private static final String USER_AGENT = "Mozilla/5.0";
    //private static final String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=fb&apikey=Q1QZFVJQ21K7C6XM";
    private final String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    private final String GET_URL2 = "https://cloud.iexapis.com/stable/stock/market/batch/time_series?symbols=";
    private final String API_KEY = "V9G35RET2PICR1YC";
    private final String TOKEN = "pk_c283a36c3e64499e809005a0f4e28bb9";

    public String getAlphavantageResponse(String id) throws IOException {

        String res;
        URL obj = new URL(GET_URL + id + "&apikey=" + API_KEY);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        //System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            res = response.toString();
        } else {
            res = "GET request not worked";
        }

        return res;
    }

    public String getIextradingResponse(String id) throws IOException {

        String res;
        URL obj = new URL(GET_URL2 + id + "&types=quote,chart&range=" + API_KEY + "&token=pk_04b714a99e6d4d6394657768c1274360");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        //System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            res = response.toString();
        } else {
            res = "GET request not worked";
        }

        return res;
    }

}
