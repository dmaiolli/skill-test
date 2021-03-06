package br.com.fiap.skilltest.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static String toText(LocalDate date) {
		return formatter.format(date);
	}
	
	public static LocalDate toDate(String value) {
		return LocalDate.parse(value, formatter);
	}
	
	public static Boolean ehAnteriorAtual(LocalDate data) {
		return data.isBefore(LocalDate.now());
	}
}
