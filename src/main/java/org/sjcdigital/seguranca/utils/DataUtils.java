package org.sjcdigital.seguranca.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 
 * Base copiada de http://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
 * @author wsiqueir
 *
 */
public class DataUtils {

	/**
	 * Utilizado para datas que o dia não é importante (ano e mês)
	 * 
	 * @param ano
	 * @param mes
	 * @return
	 */
	public static Date anoMes(int ano, int mes) {
		return asDate(LocalDate.of(ano, mes, 1));
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault())
				.toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime())
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
