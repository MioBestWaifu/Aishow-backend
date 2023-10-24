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
import com.aishow.backend.models.ServiceBundle;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.ServiceSchedule;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online"})
@RestController ()
@RequestMapping("/api/services")
public class ServiceController {
	@GetMapping(produces = "application/json")
	public ServiceInformation getService(@RequestParam("id") String id){
		return new ServiceRequestHandler().handle(null, new String[]{id});
	} 

    @GetMapping(value="answerRequest",produces = "text/plain")
	public String answerRequest(@RequestParam("type")String type, @RequestParam("id") String id, @RequestParam("idProvider") String idProvider){
		return new AnswerRequestHandler().handle(null, new String[]{type,id,idProvider});
	}

	//Parar de recebr o idProvider dps do AUTH
	//PASSED
	@GetMapping(value="agenda",produces = "application/json")
	public ServiceSchedule getAgenda(@RequestParam("id") String id){
		return new ServiceAgendaRequestHandler().handle(null, new String[]{id});
	}

	@GetMapping(value="userRequests",produces = "application/json")
	public ArrayList<ClientServiceInteraction> getUserServiceRequests(@RequestParam("id") String id){
		return new UserMadeServiceRequestsHandler().handle(null, new String[]{id});
	}


	@GetMapping(value="userServices",produces = "application/json")
	public List<ServiceInformation> getAllUserServices(@RequestParam("id") String id){
		return new UserServicesRequestHandler().handle(null, new String[]{id});
	}

	//Transformar isso num delete
	@GetMapping(value = "cancelRequest",produces = "text/plain")
	public String cancelRequest(@RequestParam("id") String id){
		return new CancelRequestHandler().handle(null, new String[]{id});
	}
	@PostMapping(value = "anotherBundle", consumes = "application/json", produces = "application/json")
	public ServiceBundle getAnortherBundle (@RequestBody Integer[] has){
		return new GetBundleHandler().handle(has);
	}

	//Id será desnecessario com o AUTH
	//PASSED
	@PostMapping(value="updateName",consumes = "text/plain", produces = "text/plain")
	public String updateName(@RequestBody String newName,@RequestParam("id") String id) {
		String x = new NameUpdateHandler().handle(newName,new String[]{id});
		return x;
	}

	//Id será desnecessario com o AUTH
	//PASSED
	@PostMapping(value="create",consumes = "application/json", produces = "text/plain")
	public String createService(@RequestBody ServiceInformation info,@RequestParam("id") String id) {
		String x = new CreateServiceHandler().handle(info,new String[]{id});
		return x;
	}

	//PASSED
	@PostMapping(value="schedule",consumes = "application/json", produces = "text/plain")
	public String scheduleService(@RequestBody ClientServiceInteraction info){
		return new ScheduleServiceHandler().handle(info);
	}

	//PASSED
	@PostMapping(value="update",consumes = "application/json", produces = "text/plain")
	public String updateService(@RequestBody ServiceInformation info){
		return new UpdateServiceHandler().handle(info);
	}
}
