package se.atrosys.birds.xml.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdName;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.model.XmlFamily;
import se.atrosys.birds.xml.model.XmlGenus;
import se.atrosys.birds.xml.model.XmlOrder;
import se.atrosys.birds.xml.model.XmlSpecies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
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

		List<CSVRecord> records = null;

		try {
			Reader reader = new FileReader(new ClassPathResource("names.csv").getFile());
			records = CSVFormat.RFC4180.withDelimiter(';').parse(reader).getRecords();
		} catch (IOException e) {
			logger.error("Could not read csv file", e);
			throw new IllegalStateException(e);
		}

		Map<Integer, String> header = createHeader(records.subList(0, 3));
		XmlSpecies currentBird = null;

		for (CSVRecord record : records.subList(3, records.size())) {
			if (!record.get(1).isEmpty() || !record.get(2).isEmpty()) {
				currentBird = null;
				continue;
			}
			if (currentBird == null) {
				currentBird = birdsByName.get(record.get(3));
			}

			for (int i = 0; i < record.size(); i++) {
				if (!record.get(i).isEmpty()) {
					currentBird.getNames().put(header.get(i), record.get(i));
				}
			}

		}

		return iocList;
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
