package com.carlos.stockprice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.stockprice.constants.RabbitMQConstants;
import com.carlos.stockprice.dtos.StockDTO;
import com.carlos.stockprice.services.RabbitMQService;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {

	@Autowired
	private RabbitMQService rabbitMQservice;

	@PutMapping
	private ResponseEntity updateStock(@RequestBody StockDTO stockDTO) {
		this.rabbitMQservice.sendMessage(RabbitMQConstants.STOCK_QUEUE, stockDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
}