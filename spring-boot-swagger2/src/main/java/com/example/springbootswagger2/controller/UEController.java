package com.example.springbootswagger2.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.springbootswagger2.model.Unite;

import DataBase.DataBaseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "UEController")
@RestController
public class UEController {
          	
	@ApiOperation(value = "Get list of Students in the System ", response = Iterable.class, tags = "getEleves")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), 
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	
	@RequestMapping(value = "/verifIdUE/{id}")
	public int verifIdUE(@PathVariable(value = "id") String id) { 
		return DataBaseManager.VerifId(id, "UniteEnseignement");
	}
	
	
	@GetMapping(value = "/UE")
	public List<Unite> getSujet() { 
		return DataBaseManager.selectListUnite();
	}
	
}