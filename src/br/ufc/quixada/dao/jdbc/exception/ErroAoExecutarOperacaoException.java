package br.ufc.quixada.dao.jdbc.exception;

public class ErroAoExecutarOperacaoException extends RuntimeException{

	public ErroAoExecutarOperacaoException(String tipo, String mensagem){
		super("Tipo : " + tipo + "\n" + mensagem);
	}
	
}
