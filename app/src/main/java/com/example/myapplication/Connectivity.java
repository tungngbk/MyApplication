package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.ImageName;
import model.Project;
import model.record;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class Connectivity {
    public static String geturl (String url_esp32){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_esp32)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException error) {
            return null;
        }


    }
    public static record postimage (String url,String base64string, String buildingid) {

//       OkHttpClient client = new OkHttpClient();
//
//        RequestBody formBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("image", base64string)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"image\":\""+base64string+"\",\n" +
                        "    \"building\":\""+buildingid+"\""+
                        "}");

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody = response.body().string();
            JSONObject jObject = new JSONObject(responsebody);
            record record =  new record("","",
                    jObject.has("prediction")?"".equals(jObject.getString("prediction"))?0f:Float.parseFloat(jObject.getString("prediction")):0f,
                    jObject.has("anomaly")?jObject.getString("anomaly"):"",
                    jObject.has("type")?jObject.getString("type"):"",
                    jObject.has("image")?jObject.getString("image"):"");

            return record;
        } catch (IOException e) {
            System.out.println("loi io");
            throw new RuntimeException(e);
        } catch (JSONException e) {
            System.out.println("loi json");
            throw new RuntimeException(e);
        } finally {
            if(response!=null){
                response.close();
            }
        }
    }

    public static List<ImageName> getImageNames () throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"collection\":\"fault_detection\",\n" +
                        "    \"database\":\"thesis\",\n" +
                        "    \"dataSource\":\"Cluster0\",    \n" +
                        "    \"projection\": {\n" +
                        "      \"_id\": 1\n" +
                        "    }\n" +
                        "  \n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/find")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String responsebody =response.body().string();
        //JSONObject jObject = new JSONObject(responsebody);
        JSONObject myjson = new JSONObject(responsebody);
        JSONArray the_json_array = myjson.getJSONArray("documents");
        int size = the_json_array.length();
        //ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
        ArrayList<ImageName> nameList = new ArrayList<ImageName>();
        for (int i = 0; i < size; i++) {
            JSONObject another_json_object = the_json_array.getJSONObject(i);
            //arrays.add(another_json_object);
            nameList.add(new ImageName(another_json_object.getString("_id")));
        }
        return nameList;
    }

    public static Boolean insertNewProject (String id, String building, String des) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        //MediaType mediaType = MediaType.parse("application/json");
        String json = "{\n" +
                "  \"dataSource\":\"Cluster0\",\n" +
                "  \"database\":\"thesis\",\n" +
                "  \"collection\":\"building\",\n" +
                "  \"document\":\n" +
                "  {\n" +
                "      \"building\":\"XXXXC House\",\n" +
                "      \"description\":\"Tung nui Street\",\n" +
                "      \"id\":\"10\"\n" +
                "  }\n" +
                "}";
        RequestBody body = RequestBody.create(json.getBytes(StandardCharsets.UTF_8));


        try {
            final Buffer buffer = new Buffer();
            body.writeTo(buffer);
            System.out.println(buffer.readUtf8());
        }
        catch (final IOException e) {
            System.out.println("not work");
        }
        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/insertOne")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return true;
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        //return false;
    }

    public static ArrayList<Project> getProjects () throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"collection\":\"building\",\n" +
                        "    \"database\":\"thesis\",\n" +
                        "    \"dataSource\":\"Cluster0\",\n" +
                        "    \"projection\": {\n" +
                        "      \"id\": 1,\n" +
                        "      \"building\": 1,\n" +
                        "      \"description\":1\n" +
                        "    }\n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/find")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        Response response=null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody =response.body().string();
            JSONObject myjson = new JSONObject(responsebody);
            JSONArray the_json_array = myjson.getJSONArray("documents");
            int size = the_json_array.length();
            ArrayList<Project> projects = new ArrayList<Project>();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = the_json_array.getJSONObject(i);
                projects.add(new Project(another_json_object.has("id")?another_json_object.getString("id"):""
                        ,another_json_object.has("building")?another_json_object.getString("building"):""
                        ,another_json_object.has("description")?another_json_object.getString("description"):""));
            }
            return projects;
        } catch (IOException e) {
            System.out.println("loi io getproject");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("loi json get project");
            throw new RuntimeException(e);
        } finally {
            if(response!=null) {
                response.close();
            }
        }

    }

    public static List<record> getIdAndType (){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"collection\":\"fault_detection\",\n" +
                        "    \"database\":\"thesis\",\n" +
                        "    \"dataSource\":\"Cluster0\",\n" +
                        "    \"projection\": {\n" +
                        "      \"_id\": 1,\n" +
                        "      \"type\": 1\n" +
                        "    }\n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/find")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        Response response=null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody =response.body().string();
            JSONObject myjson = new JSONObject(responsebody);
            JSONArray the_json_array = myjson.getJSONArray("documents");
            int size = the_json_array.length();
            ArrayList<record> nameList = new ArrayList<record>();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = the_json_array.getJSONObject(i);
                nameList.add(new record(another_json_object.getString("_id"),"",
                        0f,"",another_json_object.has("type")?another_json_object.getString("type"):"",""));
            }
            return nameList;
        } catch (IOException e) {
            System.out.println("loi io");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("loi json");
            throw new RuntimeException(e);
        } finally {
            if(response!=null) {
                response.close();
            }
        }
    }

    public static List<record> getImageOfProject (String projectid){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"collection\":\"fault_detection\",\n" +
                        "    \"database\":\"thesis\",\n" +
                        "    \"dataSource\":\"Cluster0\",\n" +
                        "    \"projection\": {\n" +
                        "      \"date\": 1,\n" +
                        "      \"segment_image\":1\n" +
                        "    },\n" +
                        "    \"filter\" : {\"building\": \""+projectid+"\"}\n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/find")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        Response response=null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody =response.body().string();
            JSONObject myjson = new JSONObject(responsebody);
            JSONArray the_json_array = myjson.getJSONArray("documents");
            int size = the_json_array.length();
            ArrayList<record> nameList = new ArrayList<record>();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = the_json_array.getJSONObject(i);
                nameList.add(new record(another_json_object.getString("_id"),
                        another_json_object.has("date")?another_json_object.getString("date"):"",
                        0f,"","",
                        another_json_object.has("segment_image")?another_json_object.getString("segment_image"):""));
            }
            return nameList;
        } catch (IOException e) {
            System.out.println("loi io");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("loi json");
            throw new RuntimeException(e);
        } finally {
            if(response!=null) {
                response.close();
            }
        }
    }

    public static record getImagedetail (String date){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "    \"collection\":\"fault_detection\",\n" +
                        "    \"database\":\"thesis\",\n" +
                        "    \"dataSource\":\"Cluster0\",\n" +
                        "    \"filter\" : {\"date\": \""+date+"\"}\n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-wlatu/endpoint/data/v1/action/findOne")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "LFyT8MWcEraGxtCsMJpceBO8q72WLX8mInon25j6kbVCgv2j5vSwVYzNVzdxFsqh")
                .build();
        Response response=null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody =response.body().string();
            JSONObject myjson = new JSONObject(responsebody);
            JSONObject another_json_object = myjson.getJSONObject("document");
//            JSONArray the_json_array = myjson.getJSONArray("documents");
//            int size = the_json_array.length();
//            ArrayList<record> nameList = new ArrayList<record>();
//            for (int i = 0; i < size; i++) {
//                JSONObject another_json_object = the_json_array.getJSONObject(i);
            //nameList.add(
            return     new record(another_json_object.getString("_id"),
                    another_json_object.has("date")?another_json_object.getString("date"):"",
                    another_json_object.has("prediction")? Float.parseFloat(another_json_object.getString("prediction")) :0f,
                    another_json_object.has("original_image")?another_json_object.getString("original_image"):"",
                    another_json_object.has("type")?another_json_object.getString("type"):"",
                    another_json_object.has("segment_image")?another_json_object.getString("segment_image"):"");
            //}
            //return nameList;
        } catch (IOException e) {
            System.out.println("loi io");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("loi json");
            throw new RuntimeException(e);
        } finally {
            if(response!=null) {
                response.close();
            }
        }
    }

    public static String control(String x) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(x)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException error) {
            return null;
        }
    }
}