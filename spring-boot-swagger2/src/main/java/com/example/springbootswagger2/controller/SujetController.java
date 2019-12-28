package com.example.springbootswagger2.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootswagger2.model.SujJSON;
import com.example.springbootswagger2.model.Sujet;

import DataBase.DataBaseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "SujetController")
@RestController
public class SujetController {
          	
	@ApiOperation(value = "Get list of Students in the System ", response = Iterable.class, tags = "getEleves")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	
	@RequestMapping(value = "/verifIdSujet/{id}")
	public int verifIdSujet(@PathVariable(value = "id") String id) { 
		return DataBaseManager.VerifId(id, "Sujet");
	}
	
	/*REST*/
	@PostMapping(value = "/sujet")
	public void postSujet(@RequestBody SujJSON sujet)
	{ 
		sujet.setDescription(sujet.getDescription().replace("_"," "));
		DataBaseManager.insertSujet(sujet.getId(),sujet.getNom(),sujet.getDescription());
	}
	
	@GetMapping(value = "/sujet")
	public List<Sujet> geSujet() { 
		return DataBaseManager.selectListSujet();
	}
	
	@DeleteMapping(value = "/sujet/{id}")
	public void deletSujet(@PathVariable String id)
	{ 
		DataBaseManager.delete(id,"Sujet");
	}
	/*REST END*/

}