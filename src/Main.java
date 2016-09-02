import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by siwaweswongcharoen on 9/2/2016 AD.
 */
public class Main {
    public static void main(String... args) {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("/Users/siwaweswongcharoen/Downloads/work/province copy.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        JsonObject object = gson.fromJson(reader, JsonObject.class);
        JsonArray features = object.getAsJsonArray("features");
        System.out.println("features count: " + features.size());

        for (JsonElement feature : features) {
            JsonObject properties = feature.getAsJsonObject().getAsJsonObject("properties");
            String privinceCode = properties.get("PROV_CODE").getAsString();
            String privinceName = properties.get("PROV_NAME").getAsString();
            String regionCode = properties.get("REG_CODE").getAsString();

            JsonObject geometry = feature.getAsJsonObject().getAsJsonObject("geometry");
            JsonArray coordinates0 = geometry.get("coordinates").getAsJsonArray();
            JsonArray coordinates1 = coordinates0.get(0).getAsJsonArray();
            JsonArray coordinates2 = coordinates1.get(0).getAsJsonArray();

            double[] lats = makeDoubleArray(1, coordinates2);
            double[] lngs = makeDoubleArray(0, coordinates2);

            System.out.println(regionCode + ","
                    + privinceCode + ","
                    + privinceName + ","
                    + min(lats) + "," + min(lngs) + ","
                    + max(lats) + "," + max(lngs)
            );
        }
    }

    public static double[] makeDoubleArray(int position, JsonArray jsonArray) {
        double[] array = new double[jsonArray.size()];
        for (int i=0; i< jsonArray.size(); i++) {
            JsonArray coordinate = jsonArray.get(i).getAsJsonArray();
            array[i] = coordinate.get(position).getAsDouble();
        }

        return array;
    }

    public static double min(double[] array) {
        double min = array[0];
        for (double val : array) {
            if (min > val)
                min = val;
        }
        return min;
    }

    public static double max(double[] array) {
        double max = array[0];
        for (double val : array) {
            if (max < val)
                max = val;
        }
        return max;
    }
}
