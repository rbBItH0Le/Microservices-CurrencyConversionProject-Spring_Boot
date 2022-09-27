package com.we.startproject;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		HashMap<String,String> uriVariables=new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
		ResponseEntity<CurrencyConversion> entity= new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,uriVariables);
		CurrencyConversion currencyconversion= entity.getBody();
		return new CurrencyConversion(currencyconversion.getId(),from, to,currencyconversion.getConversionMultiple(),quantity,quantity.multiply(currencyconversion.getConversionMultiple()),currencyconversion.getEnvironment());
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		CurrencyConversion currencyconversion=proxy.retrieveExchangeValue(from, to);
		return new CurrencyConversion(currencyconversion.getId(),from, to,currencyconversion.getConversionMultiple(),quantity,quantity.multiply(currencyconversion.getConversionMultiple()),currencyconversion.getEnvironment());
	}
	
	
	
	

}

