package br.com.anascoding.eventoapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static List<ItemVideo> fromJson(String json) throws JSONException {
        List<ItemVideo> listaItemVideo = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            String titulo = object.getString("titulo");
            String data = object.getString("data");
            String url = object.getString("url");

            listaItemVideo.add(new ItemVideo(titulo, data, url));
        }

        return listaItemVideo;
    }
}
