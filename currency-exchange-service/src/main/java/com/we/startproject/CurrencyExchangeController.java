package com.we.startproject;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currepository;
	
	private Logger logger=LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from,@PathVariable String to)
	{
		//CurrencyExchange currencyexchange= new CurrencyExchange(1000L,from,to,BigDecimal.valueOf(50));
		CurrencyExchange currencyexchange=currepository.findByFromAndTo(from, to);
		if(currencyexchange==null)
		{
			throw new RuntimeException("Unable to find from "+from+"to "+to);
		}
		String port=environment.getProperty("local.server.port");
		currencyexchange.setEnvironment(port);
		logger.info("Retrieve method called with values {} to {}",from,to);
		return currencyexchange;
	}

}
