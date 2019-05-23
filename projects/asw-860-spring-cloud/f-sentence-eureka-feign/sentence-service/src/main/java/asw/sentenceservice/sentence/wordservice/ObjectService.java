package asw.sentenceservice.sentence.wordservice;

import asw.sentenceservice.sentence.domain.WordService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger; 

@Service 
public class ObjectService implements WordService {

	private final Logger logger = Logger.getLogger(ObjectService.class.toString()); 

	@Autowired 
	private ObjectClient objectClient;
	
	public String getWord() {
		return objectClient.getWord(); 
	}
	
}
