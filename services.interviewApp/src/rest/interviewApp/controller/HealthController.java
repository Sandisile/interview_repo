package rest.interviewApp.controller;

import libraries.REST.dto.HealthResponseTO;
import libraries.RESTDocs.SpringWebAppConfig;
import rest.interviewApp.doc.CustomVersioningSwaggerConfig;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Import({SpringWebAppConfig.class, CustomVersioningSwaggerConfig.class})
@RequestMapping(value={"/api"})
public class HealthController {

	public HealthController() {
		
	}

	@RequestMapping(value={"/health"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HealthResponseTO> health() {
		return new ResponseEntity<HealthResponseTO>(new HealthResponseTO(), HttpStatus.OK);
	}
}
