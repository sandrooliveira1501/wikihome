package br.ufc.quixada.dao.jdbc;

import java.util.List;

import br.ufc.quixada.config.ChaveEstrangeira;
import br.ufc.quixada.config.DescricaoTabela;
import br.ufc.quixada.config.LoaderDescricaoTabelas;

public class GenericSQL {

	public String getSqlSave(DescricaoTabela descricaoTabela) {
		
		StringBuilder sql = new StringBuilder();
		List<String> colunas = descricaoTabela.getColunas();
		List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
				.getChavesEstrangeiras();

		sql.append("insert into ");
		sql.append(descricaoTabela.getNomeTabela());
		sql.append(" (");
		for (String coluna : colunas) {
			sql.append(coluna);
			sql.append(",");
		}
		for (ChaveEstrangeira chaveEstrangeira : chavesEstrangeiras) {
			sql.append(chaveEstrangeira.getNomeChave());
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);

		sql.append(") values (");
		for (int i = 0; i < (colunas.size() + chavesEstrangeiras.size()); i++) {
			sql.append("?,");
		}

		sql.deleteCharAt(sql.length() - 1);
		sql.append(");");

		return sql.toString();

	}

	public String getSqlFind(DescricaoTabela descricaoTabela)
			throws ClassNotFoundException {
		StringBuilder sql = new StringBuilder();

		List<String> colunas = descricaoTabela.getColunas();
		List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
				.getChavesEstrangeiras();

		colunas.add(descricaoTabela.getColunaChave());
		colunas.addAll(descricaoTabela.getColunas());

		sql.append("select *");
		sql.append(" from ");
		sql.append(descricaoTabela.getNomeTabela());

		for (ChaveEstrangeira chaveEstrangeira : chavesEstrangeiras) {
			DescricaoTabela descricaoChave = new LoaderDescricaoTabelas()
					.getDescricaoTabela(Class.forName(chaveEstrangeira
							.getNomeClasseAtributo()));

			sql.append(" inner join ");
			sql.append(descricaoChave.getNomeTabela());
			sql.append(" on ").append(chaveEstrangeira.getNomeChave())
					.append("=").append(descricaoChave.getColunaChave());

		}

		sql.append(" where ");
		sql.append(descricaoTabela.getColunaChave());
		sql.append(" = ?;");

		return sql.toString();
	}

	public String getSqlUpdate(DescricaoTabela descricaoTabela) {

		List<String> colunas = descricaoTabela.getColunas();
		List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
				.getChavesEstrangeiras();

		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE ").append(descricaoTabela.getNomeTabela())
				.append(" set ");

		for (String coluna : descricaoTabela.getColunas()) {
			sql.append(coluna);
			sql.append("=?,");
		}
		sql.deleteCharAt(sql.length() - 1); // Remove vírgula que sobra

		sql.append(" where ");
		sql.append(descricaoTabela.getColunaChave());
		sql.append(" = ?;");

		return sql.toString();
	}

	public String getSqlDelete(DescricaoTabela descricaoTabela) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM ").append(descricaoTabela.getNomeTabela())
				.append(" where ").append(descricaoTabela.getColunaChave())
				.append(" = ?");
	
		return sql.toString();
	}

}
