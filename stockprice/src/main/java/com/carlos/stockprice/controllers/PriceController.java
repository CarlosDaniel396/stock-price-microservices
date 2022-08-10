package com.carlos.stockprice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.stockprice.services.RabbitMQService;

import constants.RabbitMQConstants;
import dtos.PriceDTO;

@RestController
@RequestMapping(value = "/prices")
public class PriceController {

	@Autowired
	private RabbitMQService rabbitMQService;

	@PutMapping
	private ResponseEntity updatePrice(@RequestBody PriceDTO priceDTO) {
		this.rabbitMQService.sendMessage(RabbitMQConstants.PRICE_QUEUE, priceDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
}