package org.prgms.gccoffee;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {

	public static UUID toUUID(byte[] bytes){
		final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	// null 처리?
	public static LocalDateTime toLocalDateTime(Timestamp timestamp){
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
