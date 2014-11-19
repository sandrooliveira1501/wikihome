package br.ufc.quixada.dao.jdbc.exception;

public class ErroArquivoConfiguracaoException extends RuntimeException{

	public ErroArquivoConfiguracaoException(){
		super("Erro no arquivo de configuração");
	}
	
}
