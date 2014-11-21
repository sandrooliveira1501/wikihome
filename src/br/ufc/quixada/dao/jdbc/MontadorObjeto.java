package br.ufc.quixada.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.config.DescricaoTabela;
import br.ufc.quixada.config.FuncoesReflection;

public class MontadorObjeto <T>{
	
	public T montarObjeto(DescricaoTabela descricaoTabela, ResultSet resultSet) throws SQLException{
		
		FuncoesReflection reflection = new FuncoesReflection();
		
		T entity = (T) reflection.criarInstancia(descricaoTabela.getNome());
		
		List<String> nomeAtributos = new ArrayList<String>();
		nomeAtributos.add(descricaoTabela.getColunaChave());
		nomeAtributos.addAll(descricaoTabela.getColunas());

		Class classeEntidade = entity.getClass();

		for (String nomeAtributo : nomeAtributos) {
			reflection.setAtributoEmObjeto(nomeAtributo, entity, resultSet.getObject(nomeAtributo));	
		}
		
		return entity;
	}
	
	public T montarObjeto(DescricaoTabela descricaoTabela, ResultSet resultSet, String alias) throws SQLException{
		
		FuncoesReflection reflection = new FuncoesReflection();
		
		T entity = (T) reflection.criarInstancia(descricaoTabela.getNome());
		
		List<String> nomeAtributos = new ArrayList<String>();
		nomeAtributos.add(descricaoTabela.getColunaChave());
		nomeAtributos.addAll(descricaoTabela.getColunas());

		Class classeEntidade = entity.getClass();

		for (String nomeAtributo : nomeAtributos) {
			reflection.setAtributoEmObjeto(nomeAtributo, entity, resultSet.getObject(nomeAtributo));	
		}
		
		return entity;
	}

}
