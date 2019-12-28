package com.example.springbootswagger2.controller;
import DataBase.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.json.utils.CreateJSON;
import com.example.json.utils.ReadJSON;
import com.example.springbootswagger2.model.Eleve;
import com.example.springbootswagger2.model.ElvJSON;
import com.example.springbootswagger2.model.Groupe;
import com.example.springbootswagger2.model.UeJSON;
import com.example.springbootswagger2.model.Unite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ElevesController")
@RestController
public class ElevesController {
	private final Producer prod;
	private Logger send;
	private com.example.logger.Logger clog;
	private KafkaCheck checker;
	@Autowired
	public ElevesController(Producer prod,@Value("${spring.kafka.consumer.bootstrap-servers}") String serverLink) {
		this.prod =prod ;
		clog = new com.example.logger.Logger("log");
		send =  clog.getLogger();
		checker = new KafkaCheck(serverLink);
		if (!checker.check())
			send.warning("Connection to Kafka server failed :");
		else 
			send.info("Successfully connected to Kafka server");
	}

	@ApiOperation(value = "Get list of Students in the System ", response = Iterable.class, tags = "getEleves")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	


	@KafkaListener(topics = "topictest", group= "group_id" )
	public void consume(String message){
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		if (ReadJSON.isJSONValid(message))
		{
			String cl = ReadJSON.classJSON(message);
			if (cl.equals("Personne"))
			{
				String event = ReadJSON.JSONevent(message);
				Eleve e = ReadJSON.eleveJSON(message);
				if (e == null)
				{
					//TODO write the message in a log file.
					send.warning("Erreur dans Personne: "+message);	
				}
				else {
					
					Eleve inBase = DataBaseManager.selectEleve(e.getId(),e.getNom(), e.getPrenom(), e.getEmail());
					int idpresent = DataBaseManager.VerifId(e.getId(), "Eleve");
					if (event.equals("create"))
					{
						if (inBase == null)
						{
							if (idpresent != 0)
							{
								Eleve sup = DataBaseManager.selectEleveWithId(e.getId());
								DataBaseManager.delete(sup.getId(), "Eleve");
						        UUID uuid = UUID.randomUUID();
						        String randomUUIDString = uuid.toString();
								DataBaseManager.insertEleve(randomUUIDString,sup.getPrenom(),sup.getNom(),sup.getEmail());

							}
							DataBaseManager.insertEleve(e.getId(),e.getPrenom(),e.getNom(),e.getEmail());
						}

					}
					else if (event.equals("delete"))
					{
						if (inBase != null)
						{
							DataBaseManager.delete(inBase.getId(),"Eleve");
						}
					}
					else if (event.equals("update"))
					{
						if (idpresent != 0 )
						{
							DataBaseManager.delete(e.getId(), "Eleve");
							DataBaseManager.insertEleve(e.getId(),e.getPrenom(),e.getNom(),e.getEmail());

						}
					}
					else 
					{
						send.warning("Erreur dans event: "+message);
					}
					
				}	
				
			}
			else if (cl.equals("UE"))
			{
				String event = ReadJSON.JSONevent(message);
				Unite ue = ReadJSON.ueJSON(message);
				if (ue == null)
				{
					//TODO write the message in a log file.
					send.warning("Erreur dans UE: "+message);	
				} 
				else 
				{
					Unite inBase = DataBaseManager.selectUnite(ue.getId(), ue.getCode(), ue.getNom(), ue.getCours(), ue.getTd(), ue.getTp(), ue.getValeur());
					int idpresent = DataBaseManager.VerifId(ue.getId(), "UniteEnseignement");
					if (event.equals("create"))
					{
						if (inBase == null)
						{
							if (idpresent != 0)
							{
								Unite sup = DataBaseManager.selectUniteWithId(ue.getId());
								DataBaseManager.delete(sup.getId(), "UniteEnseignement");
						        UUID uuid = UUID.randomUUID();
						        String randomUUIDString = uuid.toString();
								DataBaseManager.insertUE(randomUUIDString, sup.getNom(), sup.getDescription(), sup.getCode(), sup.getCours(), sup.getTd(), sup.getTp(), sup.getValeur());

							}
							DataBaseManager.insertUE(ue.getId(), ue.getNom(), ue.getDescription(), ue.getCode(), ue.getCours(), ue.getTd(), ue.getTp(), ue.getValeur());
						}
					}
					else if (event.equals("delete"))
					{
						if (inBase != null)
						{
							DataBaseManager.delete(inBase.getId(),"UniteEnseignement");
						}
						
					}
					else if (event.equals("update"))
					{
						if (idpresent != 0 )
						{
							DataBaseManager.delete(ue.getId(), "UniteEnseignement");
							DataBaseManager.insertUE(ue.getId(), ue.getNom(), ue.getDescription(), ue.getCode(), ue.getCours(), ue.getTd(), ue.getTp(), ue.getValeur());

						}
					}
					else 
					{
						//TODO write the message in a log file.
						send.warning("Erreur dans event: "+message);
					}
					
				}
				
			}
			else {
				//TODO write the message in a log file.
				send.warning("Objet inconnu ou JSON pas bien structuré: "+message);	
			}
		}
		else 
		{
			//TODO write the message in a log file.
			send.warning("Format JSON incorrect: "+message);
		}		
	}
	

	
	@RequestMapping(value = "/verifIdEleve/{id}")
	public int verifIdEleve(@PathVariable(value = "id") String id) { 
		return DataBaseManager.VerifId(id, "Eleve");
	}
	
	/* REST */
	@GetMapping(value = "/groupe")
	public List<Groupe> geGroupes() { 
		return DataBaseManager.selectListGroupe();
	}
	
	@GetMapping(value = "/eleve")
	public List<Eleve> getStudents() { 
		return DataBaseManager.selectListEleve();
	}
	@PostMapping("/eleve")
	public void postEleve(@RequestBody ElvJSON newEleve) 
	{ 
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		DataBaseManager.insertEleve(newEleve.getId(),newEleve.getPrenom(),newEleve.getNom(),newEleve.getEmail());
		String msg = CreateJSON.eleveJSON(newEleve.getId(),newEleve.getPrenom(),newEleve.getNom(),newEleve.getEmail(), "create");

		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
		

	}
	
	@PutMapping(value = "/eleve")
	public void putEleve(@RequestBody ElvJSON newEleve)
	{ 
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		DataBaseManager.delete(newEleve.getId(),"Eleve");
		DataBaseManager.insertEleve(newEleve.getId(),newEleve.getPrenom(),newEleve.getNom(),newEleve.getEmail());
		String msg = CreateJSON.eleveJSON(newEleve.getId(),newEleve.getPrenom(),newEleve.getNom(),newEleve.getEmail(), "update");
		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
	}
	@DeleteMapping(value = "/eleve/{id}")
	public void deleteEleve(@PathVariable String id)
	{ 	
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		Eleve e = DataBaseManager.selectEleveWithId(id);
		DataBaseManager.delete(id,"Eleve");
		String msg = CreateJSON.eleveJSON(id, e.getNom(), e.getPrenom(), e.getEmail(), "delete");
		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
	}
	
	@PostMapping(value = "/UE")
	public void postUE(@RequestBody UeJSON unite)
	{ 
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		unite.setDescription(unite.getDescription().replace("_"," "));
		DataBaseManager.insertUE(unite.getId(),unite.getNom(),unite.getDescription(),unite.getCode(),unite.getCours(),unite.getTd(),unite.getTp(),unite.getValeur());
		String msg = CreateJSON.UeJSON(unite.getId(), unite.getCode(), unite.getNom(), unite.getCours(), unite.getTd(), unite.getTp(), unite.getValeur(), "create");
		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
	}
	
	@PutMapping(value = "/UE")
	public void putUE(@RequestBody UeJSON unite)
	{ 
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		unite.setDescription(unite.getDescription().replace("_"," "));
		DataBaseManager.delete(unite.getId(),"UniteEnseignement");
		DataBaseManager.insertUE(unite.getId(),unite.getNom(),unite.getDescription(),unite.getCode(),unite.getCours(),unite.getTd(),unite.getTp(),unite.getValeur());
		String msg = CreateJSON.UeJSON(unite.getId(), unite.getCode(), unite.getNom(), unite.getCours(), unite.getTd(), unite.getTp(), unite.getValeur(), "update");
		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
	}
	
	@DeleteMapping(value = "/UE/{id}")
	public void deletUE(@PathVariable String id)
	{ 
		if (!checker.check())
			send.warning("Connection to Kafka server failed");
		Unite ue = DataBaseManager.selectUniteWithId(id);
		DataBaseManager.delete(id,"UniteEnseignement");
		String msg = CreateJSON.UeJSON(id, ue.getCode(), ue.getNom(), ue.getCours(), ue.getTd(), ue.getTp(), ue.getValeur(), "delete");
		prod.sendMessage(msg);
		send.info("Message produit: "+msg);
	}
	
	/* END REST */


	@RequestMapping(value = "/assignationEleveGroupe/{ideleve}/{idgroupe}")
	public void assignationEleveGroupe(@PathVariable String ideleve,@PathVariable String idgroupe)
	{ 
		DataBaseManager.assignationEleveGroupe(ideleve,idgroupe);
	}
	
}
