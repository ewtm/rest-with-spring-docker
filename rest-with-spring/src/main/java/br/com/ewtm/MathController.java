package br.com.ewtm;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ewtm.exception.ResourceNotFoundException;

@RestController
public class MathController {

	
	@RequestMapping(value="/sum/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double sum(
			@PathVariable("numberOne") String numberOne, 
			@PathVariable("numberTwo") String numberTwo) throws Exception{
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value");
		}
		Double sum  = convertToDouble(numberOne) + convertToDouble(numberTwo);
		return sum;
	}
	
	@RequestMapping(value="/multi/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double multi(
			@PathVariable("numberOne") String numberOne, 
			@PathVariable("numberTwo") String numberTwo) throws Exception{
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value");
		}
		Double sum  = convertToDouble(numberOne) * convertToDouble(numberTwo);
		return sum;
	}
	
	@RequestMapping(value="/div/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double divisao(
			@PathVariable("numberOne") String numberOne, 
			@PathVariable("numberTwo") String numberTwo) throws Exception{
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value");
		}
		Double sum  = convertToDouble(numberOne) - convertToDouble(numberTwo);
		return sum;
	}
	
	@RequestMapping(value="/med/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double media(
			@PathVariable("numberOne") String numberOne, 
			@PathVariable("numberTwo") String numberTwo) throws Exception{
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value");
		}
		Double sum  = (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
		return sum;
	}
	
	@RequestMapping(value="/squareRoot/{numberOne}",method = RequestMethod.GET)
	public Double raizQuadrada(
			@PathVariable("numberOne") String numberOne )throws Exception{
		
		if(!isNumeric(numberOne) ) {
			throw new ResourceNotFoundException("Please set a numeric value");
		}
		Double sum  = (Double) Math.sqrt(convertToDouble(numberOne));
		return sum;
	}
	
	
	private Double convertToDouble(String strNumber) {
		if(strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if(isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}
	
	private boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
}
