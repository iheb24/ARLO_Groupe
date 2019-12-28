package com.example.springbootswagger2.controller;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import DataBase.DataBaseManager;

@SpringBootApplication
public class MainApp{
    
    public static void main(String[] args) {
    	
    	DataBaseManager.initialisationBD();
		SpringApplication.run(MainApp.class, args);
    }
}
