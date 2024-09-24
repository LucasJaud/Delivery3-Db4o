package modelo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public class LowerToUpperConverter implements AttributeConverter<String, String>{

	public String convertToDatabaseColumn(String atributo) {
		//converte para maiusculas na grava��o do banco
		return (atributo == null) ? null : atributo.toUpperCase();  
	}
	
	@Override
	public String convertToEntityAttribute(String coluna) {
		//converte para minusculas na leitura do banco
		return (coluna == null) ? null : coluna.toLowerCase();		
	}
	
}
