package com.mewtwo.rabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mewtwo.rabbit.constantes.JsonConstante;
import com.mewtwo.rabbit.model.JsonDto;
import com.mewtwo.rabbit.service.RabbitMqService;

@RestController
@RequestMapping(value = "json")
public class JsonController {
	
	@Autowired
	private RabbitMqService rabbitService;
	
	@PutMapping
	private ResponseEntity<Object> alteraJson(@RequestBody JsonDto jsonDto){
		this.rabbitService.enviaMensagem(JsonConstante.FILA_JSON, jsonDto);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
