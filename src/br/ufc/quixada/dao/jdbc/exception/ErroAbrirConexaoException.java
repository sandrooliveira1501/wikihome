package br.ufc.quixada.dao.jdbc.exception;

public class ErroAbrirConexaoException extends RuntimeException{

	public ErroAbrirConexaoException(){
		super("Erro ao abrir conexão com o banco de dados");
	}
	
}
