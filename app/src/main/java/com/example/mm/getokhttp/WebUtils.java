package com.example.mm.getokhttp;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class WebUtils {

    private static final MediaType MediaTypeXML = MediaType
            .parse("text/xml; charset=utf-8");

    public static Response fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return doRequest(httpClient, url);

    }

    public static Response delete(int id, String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return mDelete(httpClient, id, url);

    }

    public static Response post(int id, String url, String username, String password, Product product) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return mPost(httpClient, id, url, product);

    }


    private static OkHttpClient createAuthenticatedClient(final String username,
                                                          final String password) {
        // build client with authentication information.
        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                if (responseCount(response) >= 3) {
                    return null;
                }
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return httpClient;
    }

    private static Response doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        //System.out.println(response.body().string());
        return response;
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    private static Response mDelete(OkHttpClient httpclient, int id, String url) throws Exception {
        Request request = new Request.Builder().url(url + "/" + id).delete().build();
        Response response = httpclient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to delete book with id:" + id);
        }
        return response;
    }


    private static Response mPost(OkHttpClient httpclient, int id, String url, Product product) throws Exception {

        Product newProduct = null;
        String myXml = "<prestashop>" +
                "<product>" +
                "<id/>" +
                "<id_manufacturer/>" +
                "<id_supplier/>" +
                "<id_category_default/>" +
                "<new/>" +
                "<cache_default_attribute/>" +
                "<id_default_image/>" +
                "<id_default_combination/>" +
                "<id_tax_rules_group/>" +
                "<position_in_category/>" +
                "<type/><id_shop_default/>" +
                "<reference/>" +
                "<supplier_reference/>" +
                "<location/>" +
                "<width/>" +
                "<height/>" +
                "<depth/>" +
                "<weight/>" +
                "<quantity_discount/>" +
                "<ean13/>" +
                "<upc/>" +
                "<cache_is_pack/>" +
                "<cache_has_attachments/>" +
                "<is_virtual/>" +
                "<on_sale/>" +
                "<online_only/>" +
                "<ecotax/>" +
                "<minimal_quantity/>" +
                "<price>12</price>" +
                "<wholesale_price/>" +
                "<unity/>" +
                "<unit_price_ratio/>" +
                "<additional_shipping_cost/>" +
                "<customizable/>" +
                "<text_fields/>" +
                "<uploadable_files/>" +
                "<active/>" +
                "<redirect_type/>" +
                "<id_product_redirected/>" +
                "<available_for_order/>" +
                "<available_date/>" +
                "<condition/>" +
                "<show_price/>" +
                "<indexed/>" +
                "<visibility/>" +
                "<advanced_stock_management/>" +
                "<date_add/>" +
                "<date_upd/>" +
                "<pack_stock_type/>" +
                "<meta_description><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</meta_description><meta_keywords><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</meta_keywords><meta_title><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</meta_title>" +
                "<link_rewrite>" +
                "<language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</link_rewrite>" +
                "<name><language id=\"1\">eida</language>" +
                "<language id=\"2\">eina</language>" +
                "</name><description><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</description><description_short><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</description_short><available_now><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</available_now><available_later><language id=\"1\"/>" +
                "<language id=\"2\"/>" +
                "</available_later><associations><categories><category><id/>" +
                "</category></categories><images><image><id/>" +
                "</image></images><combinations><combination><id/>" +
                "</combination></combinations><product_option_values><product_option_value><id/>" +
                "</product_option_value></product_option_values><product_features><product_feature><id/>" +
                "<id_feature_value/>" +
                "</product_feature></product_features><tags><tag><id/>" +
                "</tag></tags><stock_availables><stock_available><id/>" +
                "<id_product_attribute/>" +
                "</stock_available></stock_availables><accessories><product><id/>" +
                "</product></accessories><product_bundle><product><id/>" +
                "<quantity/>" +
                "</product></product_bundle></associations></product></prestashop>";


        //ObjectMapper mapper = new ObjectMapper();
        //String jsonBook = mapper.writeValueAsString(product);

        // build a request
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaTypeXML, myXml)).build();
        Response response = httpclient.newCall(request).execute();

        if (response.isSuccessful()) {
            // Get back the response and convert it to a Book object
            return response;
        }
        Log.d("HEREIAM", "cannot post pls halp");
        Log.d("HEREIAM", response.toString());
        return response;
    }

}
