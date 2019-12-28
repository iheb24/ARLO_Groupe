package com.example.springbootswagger2.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootswagger2.model.Eleve;
import com.example.springbootswagger2.model.grpJSON;

import DataBase.DataBaseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "GroupeController")
@RestController
public class GroupeController {
          	
	@ApiOperation(value = "Get list of Students in the System ", response = Iterable.class, tags = "getEleves")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	


	/*REST*/
	@PostMapping(value = "/groupe")
	public void postGroupes(@RequestBody grpJSON groupe)
	{ 
		DataBaseManager.insertGroupe(groupe.getId(),groupe.getNom());
	}
	@DeleteMapping(value = "/groupe/{id}")
	public void deletGroupe(@PathVariable String id)
	{ 
		DataBaseManager.delete(id,"Groupe");
	}
	/*REST END*/

	@RequestMapping(value = "/verifIdGroupe/{id}")
	public int verifIdGroupe(@PathVariable(value = "id") String id) { 
		return DataBaseManager.VerifId(id, "Groupe");
	}
	
	@RequestMapping(value = "/eleveNumberWithoutGroupe")
	public int eleveNumberWithoutGroupe()
	{ 
		return DataBaseManager.EleveNumberWithoutGroupe();
	}
	
	@RequestMapping(value = "/lastId")
	public int lastId()
	{ 
		return DataBaseManager.lastId("Groupe");
	}
	
	@RequestMapping(value = "/aleaId/{string}")
	public int aleaId(@PathVariable String string)
	{ 
		return DataBaseManager.AleaId(string) ;
	}
	
	@RequestMapping(value = "/assignationSujetGroupe/{idSujet}/{idGroupe}")
	public void assignationSujetGroupe(@PathVariable String idSujet,@PathVariable String idGroupe)
	{ 
		DataBaseManager.assignationSujetGroupe(idSujet,idGroupe);
	}
	
	@RequestMapping(value = "/assignationUEGroupe/{idUE}/{idGroupe}")
	public void assignationUEGroupe(@PathVariable int idUE,@PathVariable int idGroupe)
	{ 
		DataBaseManager.assignationUEGroupe(idUE,idGroupe);
	}
	
	@RequestMapping(value = "/AleaEleveWithoutGroupe")
	public Eleve AleaEleveWithoutGroupe()
	{ 
		return DataBaseManager.AleaEleveWithoutGroupe();
	}
}