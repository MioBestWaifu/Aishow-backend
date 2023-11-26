package com.aishow.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.appinteraction.GetBundleHandler;
import com.aishow.backend.handlers.appinteraction.ReviewRequestHandler;
import com.aishow.backend.handlers.personalinteraction.AnswerRequestHandler;
import com.aishow.backend.handlers.personalinteraction.NameUpdateHandler;
import com.aishow.backend.handlers.serviceinteraction.CancelRequestHandler;
import com.aishow.backend.handlers.serviceinteraction.CreateServiceHandler;
import com.aishow.backend.handlers.serviceinteraction.ScheduleServiceHandler;
import com.aishow.backend.handlers.serviceinteraction.ServiceAgendaRequestHandler;
import com.aishow.backend.handlers.serviceinteraction.ServiceRequestHandler;
import com.aishow.backend.handlers.serviceinteraction.UpdateServiceHandler;
import com.aishow.backend.handlers.serviceinteraction.UserMadeServiceRequestsHandler;
import com.aishow.backend.handlers.serviceinteraction.UserServicesRequestHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.aishow.backend.models.ReviewInfomation;
import com.aishow.backend.models.ServiceBundle;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.ServiceSchedule;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online","http://localhost:4200","168.232.228.88"})
@RestController ()
@RequestMapping("/api/services")
public class ServiceController {
	@GetMapping(produces = "application/json")
	public ServiceInformation getService(@RequestParam("id") String id){
		return new ServiceRequestHandler().handle(null, new String[]{id});
	} 

	@GetMapping(value="reviews",produces = "application/json")
	public ArrayList<ReviewInfomation> getReviews(@RequestParam("id") String id){
		return new ReviewRequestHandler().handle(null, new String[]{"services",id});
	}

	//OK
    @GetMapping(value="answerRequest",produces = "text/plain")
	public String answerRequest(@RequestParam("type")String type, @RequestParam("id") String id, @RequestParam("idProvider") String idProvider){
		return new AnswerRequestHandler().handle(null, new String[]{type,id,idProvider});
	}

	//Parar de recebr o idProvider dps do AUTH
	//OK
	@GetMapping(value="agenda",produces = "application/json")
	public ServiceSchedule getAgenda(@RequestParam("id") String id){
		return new ServiceAgendaRequestHandler().handle(null, new String[]{id});
	}

	//OK
	@GetMapping(value="userRequests",produces = "application/json")
	public ArrayList<ClientServiceInteraction> getUserServiceRequests(@RequestParam("id") String id){
		return new UserMadeServiceRequestsHandler().handle(null, new String[]{id});
	}

	//OK
	@GetMapping(value="userServices",produces = "application/json")
	public List<ServiceInformation> getAllUserServices(@RequestParam("id") String id){
		return new UserServicesRequestHandler().handle(null, new String[]{id});
	}

	//Transformar isso num delete
	//OK
	@GetMapping(value = "cancelRequest",produces = "text/plain")
	public String cancelRequest(@RequestParam("id") String id){
		return new CancelRequestHandler().handle(null, new String[]{id});
	}

	//OK
	@PostMapping(value = "anotherBundle", consumes = "application/json", produces = "application/json")
	public ServiceBundle getAnotherBundle (@RequestBody Integer[] has) throws Exception{
		ServiceBundle toReturn = new GetBundleHandler().handle(has);
		if (toReturn == null)
			throw new Exception();
		return toReturn; 
	}

	//Id será desnecessario com o AUTH
	//OK
	@PostMapping(value="create",consumes = "application/json", produces = "text/plain")
	public String createService(@RequestBody ServiceInformation info,@RequestParam("id") String id) {
		String x = new CreateServiceHandler().handle(info,new String[]{id});
		return x;
	}

	//OK
	@PostMapping(value="schedule",consumes = "application/json", produces = "text/plain")
	public String scheduleService(@RequestBody ClientServiceInteraction info){
		return new ScheduleServiceHandler().handle(info);
	}

	//OK
	@PostMapping(value="update",consumes = "application/json", produces = "text/plain")
	public String updateService(@RequestBody ServiceInformation info){
		return new UpdateServiceHandler().handle(info);
	}
}
