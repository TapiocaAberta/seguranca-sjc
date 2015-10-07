package org.sjcdigital.seguranca.load;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * Uma classe local para verificar se a leitura do arquivo foi bem sucedida.
 * @author wsiqueir
 *
 */
public class TesteCargaArquivo {
	
	static final String DIRETORIO_CSVS = "/dados/csvs";
	
	public static void main(String[] args) throws IOException {
		String file = TesteCargaArquivo.class.getResource(DIRETORIO_CSVS).getFile();
		Files.walk(Paths.get(file))
			.filter(f -> f.getFileName().toString().endsWith(".csv"))
			.peek(System.out::println)
			.forEach(p -> {
				try {
					Files.lines(p, StandardCharsets.ISO_8859_1)
					.forEach(System.out::println);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
	
}