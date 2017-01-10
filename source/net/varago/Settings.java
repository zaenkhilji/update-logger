package net.varago;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Settings {

	/**
	 * @author: Zaen Khilji
	 */

	public static final String PATH = "varago-logs.txt";

	private static BufferedReader bufferedReader;

	private static JsonObject jsonObject;

	public static String NAME = "Varago";

	public static Dimension SIZE = new Dimension(750, 550);

	private static String URL = "http://localhost/configuration.json";

	public static void init() throws IOException {
		Gson gson = new GsonBuilder().setLenient().create();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					URL).openStream()));
			jsonObject = (JsonObject) gson.fromJson(bufferedReader,
					JsonObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// NAME = jsonObject.get("name").getAsString();

		bufferedReader.close();
	}
}