package com.MissionsAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

public class JSONSerializer {

    private transient Gson gson;

    public JSONSerializer() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.VOLATILE, Modifier.TRANSIENT)
                .create();
    }

    public JSONSerializer(Map<Type,Object> map) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.VOLATILE, Modifier.TRANSIENT);
        if (map != null) {
            for (Map.Entry<Type,Object> typeObjectEntry : map.entrySet())
                gsonBuilder.registerTypeAdapter(typeObjectEntry.getKey(), typeObjectEntry.getValue());
        }
        gson = gsonBuilder.create();
    }

    public <T extends iJsonable> void save(T object) {
        File a = new File(object.getParent(),object.getChild());
        try {
            if (!a.exists()) a.createNewFile();
            FileOutputStream fos = new FileOutputStream(a);
            fos.write(gson.toJson(object).getBytes());
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public <V extends iJsonable> V load(File a,Class<V> clazz) {
        try {
            a.getParentFile().mkdirs();
            if (!a.exists()) a.createNewFile();
            return gson.fromJson(new FileReader(a),clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Gson getGson() {
        return gson;
    }
}