package baiwa.common.rest.adapter;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LongTypeAdapter extends TypeAdapter<Long> {

	private static class SingletonHolder {
		private static final LongTypeAdapter instance = new LongTypeAdapter();
	}
	
	public static LongTypeAdapter getInstance() {
		return SingletonHolder.instance;
	}
	
	@Override
	public void write(JsonWriter writer, Long value) throws IOException {
		writer.value(value);
	}

	@Override
	public Long read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String value = reader.nextString();
		return NumberUtils.isParsable(value) ? new Long(value) : null;
	}
}
