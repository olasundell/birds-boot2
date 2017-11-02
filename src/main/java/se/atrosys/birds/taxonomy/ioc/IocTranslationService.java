package se.atrosys.birds.taxonomy.ioc;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO write documentation
 */
@Component
public class IocTranslationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> translation = null;

	public Map<String, String> read() {
		if (translation != null) {
			return translation;
		}

		List<CSVRecord> records = readRecords();
		translation = new HashMap<>();

		final List<CSVRecord> csvRecords = records.subList(1, records.size());

		for (int i = 0 ; i < csvRecords.size() ; i++) {
			CSVRecord record = csvRecords.get(i);
			// extinct
			if ("EX".equals(record.get(32))) {
				continue;
			}

			// IOC subspecies
			if (!"species".equals(record.get(2))) {
				continue;
			}

			String ioc = record.get(1);

			// record contains error
			if (ioc.equals("Cyanoramphus subflavescens")) {
				continue;
			}

			String clements = record.get(5);

			if (!ioc.isEmpty()) {
				if (!clements.isEmpty()) {
					clements = manualConversions(ioc, clements);
					translation.put(ioc.toLowerCase(), clements.toLowerCase());
				} else {
					String s = csvRecords.get(i + 1).get(5);

					// difficult conversions
					s = manualConversions(ioc, s);

					if (s.trim().isEmpty()) {
						s = csvRecords.get(i - 1).get(5);
						if (s.trim().isEmpty()) {
							throw new IllegalStateException("Can't find substitute for " + ioc + " row " + record.get(0));
						}
					}
					translation.put(ioc.toLowerCase(), removeSsp(s).toLowerCase());
				}
			}
		}

		return translation;
	}

	// difficult conversions
	private String manualConversions(String ioc, String s) {
		switch (ioc) {
			case "Cyanocorax luxuosus":
				return "Cyanocorax yncas";
			case "Iole charlottae":
				return "Iole crypta";
			case "Anous albivittus":
				return "Anous albivitta";
			case "Pseudoscops clamator":
				return "Asio clamator";
			case "Synallaxis propinqua":
				return "Mazaria propinqua";
			case "Sphenocichla roberti":
				return "Stachyris roberti";
			case "Crithagra canicapilla":
				return "Crithagra gularis";
			case "Hemispingus atropileus":
			case "Hemispingus auricularis":
				return "Kleinothraupis atropileus";
			case "Hemispingus calophrys":
				return "Kleinothraupis calophrys";
			case "Hemispingus parodii":
				return "Kleinothraupis parodii";
			case "Hemispingus superciliaris":
				return "Thlypopsis superciliaris";
			case "Hemispingus reyi":
				return "Kleinothraupis reyi";
			case "Hemispingus frontalis":
				return "Sphenopsis frontalis";
			case "Hemispingus melanotis":
			case "Hemispingus ochraceus":
			case "Hemispingus piurae":
				return "Sphenopsis melanotis";
			case "Hemispingus goeringi":
				return "Poospiza goeringi";
			case "Hemispingus rufosuperciliaris":
				return "Poospiza rufosuperciliaris";
			default:
				return s;
		}
	}

	private String removeSsp(String s) {
		String[] split = s.split(" ");

		if (split.length != 3 && split.length != 2) {
			throw new IllegalStateException(s + " won't be split into correct species");
		}

		return split[0] + " " + split[1];
	}


	private List<CSVRecord> readRecords() {
		List<CSVRecord> records = null;

		try {
			Reader reader = new FileReader(new ClassPathResource("ioc-and-other-lists.csv").getFile());
			records = CSVFormat.RFC4180.withDelimiter(';').parse(reader).getRecords();
		} catch (IOException e) {
			logger.error("Could not read csv file", e);
			throw new IllegalStateException(e);
		}
		return records;
	}
}
