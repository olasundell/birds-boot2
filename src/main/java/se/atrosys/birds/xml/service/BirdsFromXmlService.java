package se.atrosys.birds.xml.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.model.XmlFamily;
import se.atrosys.birds.xml.model.XmlGenus;
import se.atrosys.birds.xml.model.XmlOrder;
import se.atrosys.birds.xml.model.XmlSpecies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class BirdsFromXmlService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public IocList readIocList() {
		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance(XmlFamily.class,
				XmlOrder.class,
				IocList.class,
				XmlGenus.class,
				XmlSpecies.class);

			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final ClassPathResource classPathResource = new ClassPathResource("ioc-names-fmt.xml");
			final StreamSource streamSource = new StreamSource(classPathResource.getInputStream());
			return unmarshaller.unmarshal(streamSource, IocList.class).getValue();
		} catch (JAXBException e) {
			logger.error("Could not create JAXB context", e);
			throw new IllegalStateException(e);
		} catch (IOException e) {
			logger.error("Could not get input stream", e);
			throw new IllegalStateException(e);
		}
	}

	public IocList readCsv(IocList iocList) {
		Map<String, XmlSpecies> birdsByName = iocList.allBirdsByName();

		List<CSVRecord> records = readRecords();

		Map<Integer, String> header = createHeader(records.subList(0, 3));
		XmlSpecies currentBird = null;

		// since the bird names are spread out over three lines, we'll be fugly about it
		int counter = 0;

		for (CSVRecord record : records.subList(3, records.size())) {
			// order and family are declared at the following record positions,
			// thus no bird names on the current line
			if (!record.get(1).isEmpty() || !record.get(2).isEmpty()) {
				continue;
			}

			// a new bird!
			if (counter == 0) {
				final String name = record.get(3);
				currentBird = birdsByName.get(name);
			}

			currentBird.getNames().putAll(parseForNames(header, record));

			// we've parsed all three lines, time to move on to another bird
			if (++counter == 3) {
				counter = 0;
			}
		}

		return iocList;
	}

	private Map<String, String> parseForNames(Map<Integer, String> header, CSVRecord record) {
		Map<String, String> result = new HashMap<>();

		// record.get(0) is the line number
		for (int i = 1; i < record.size(); i++) {
			final String s = record.get(i);
			if (!s.isEmpty()) {
				final String lang = header.get(i);
				result.put(lang, s);
			}
		}

		return result;
	}

	private List<CSVRecord> readRecords() {
		List<CSVRecord> records = null;

		try {
			Reader reader = new FileReader(new ClassPathResource("names.csv").getFile());
			records = CSVFormat.RFC4180.withDelimiter(';').parse(reader).getRecords();
		} catch (IOException e) {
			logger.error("Could not read csv file", e);
			throw new IllegalStateException(e);
		}
		return records;
	}

	private Map<Integer, String> createHeader(List<CSVRecord> firstRows) {
		Map<Integer, String> map = new HashMap<>();
		for (CSVRecord record : firstRows) {
			for (int i = 0; i < record.size(); i++) {
				if (record.get(i) != null && !record.get(i).isEmpty()) {
					map.put(i, record.get(i));
				}
			}
		}

		return map;
	}

	public Set<String> languages(IocList iocList) {
		return iocList.getOrders()
			.stream()
			.flatMap(o -> o.getFamilies().stream())
			.flatMap(f -> f.getGenus().stream())
			.flatMap(g -> g.getSpecies().stream())
			.flatMap(s -> s.getNames().keySet().stream())
			.distinct()
			.collect(Collectors.toSet());
	}
}
