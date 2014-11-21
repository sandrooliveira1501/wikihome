package br.ufc.quixada.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.config.ChaveEstrangeira;
import br.ufc.quixada.config.DescricaoTabela;
import br.ufc.quixada.config.FuncoesReflection;
import br.ufc.quixada.config.LoaderDescricaoTabelas;
import br.ufc.quixada.config.MontadorObjeto;
import br.ufc.quixada.dao.GenericDao;

public class GenericJDBCDao<T> implements GenericDao<T> {

	protected Class persistenceClass;
	protected DescricaoTabela descricaoTabela;
	protected Connection connection;
	private GenericSQL genericSQL;

	public GenericJDBCDao(Class persistenceClass) {

		this.persistenceClass = persistenceClass;
		LoaderDescricaoTabelas loaderDescricao = new LoaderDescricaoTabelas();
		this.descricaoTabela = loaderDescricao
				.getDescricaoTabela(persistenceClass);
		this.connection = ConnectionFactory.getConnection();
		this.genericSQL = new GenericSQL();

	}

	@Override
	public void save(T entity) {

		try {
			Class classEntidade = entity.getClass();
			String sql = genericSQL.getSqlSave(descricaoTabela);

			List<String> colunas = descricaoTabela.getColunas();
			List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
					.getChavesEstrangeiras();

			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 1;
			FuncoesReflection reflection = new FuncoesReflection();

			for (String coluna : colunas) {
				statement.setObject(index,
						reflection.getAtributoEmObjeto(coluna, entity));
				index++;
			}

			for (ChaveEstrangeira chaveEstrangeira : chavesEstrangeiras) {

				Object atributoChave = reflection.getAtributoEmObjeto(
						chaveEstrangeira.getNomeAtributo(), entity);
				DescricaoTabela descTabelaChave = new LoaderDescricaoTabelas()
						.getDescricaoTabela(Class.forName(chaveEstrangeira
								.getNomeClasseAtributo()));

				statement.setObject(index, reflection.getAtributoEmObjeto(
						descTabelaChave.getColunaChave(), atributoChave));
				index++;
			}

			statement.execute();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(T entity) {

		try {
			String sql = genericSQL.getSqlUpdate(descricaoTabela);
			
			PreparedStatement stmt = connection
					.prepareStatement(sql);
			int index = 1;
			FuncoesReflection reflection = new FuncoesReflection();
			for (String coluna : descricaoTabela.getColunas()) {
				stmt.setObject(index,
						reflection.getAtributoEmObjeto(coluna, entity));
				index++;
			}

			stmt.setObject(
					index,
					reflection.getAtributoEmObjeto(
							descricaoTabela.getColunaChave(), entity));

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public T find(Object id) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			FuncoesReflection funcoesReflection = new FuncoesReflection();
			Object instancia = funcoesReflection.criarInstancia(descricaoTabela
					.getNome());

			List<String> colunas = new ArrayList<String>();
			colunas.add(descricaoTabela.getColunaChave());
			colunas.addAll(descricaoTabela.getColunas());
			List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
					.getChavesEstrangeiras();

			String sql = genericSQL.getSqlFind(descricaoTabela);
			
			stmt = connection.prepareStatement(sql.toString());
			stmt.setObject(1, id);
			resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				for (String coluna : colunas) {
					funcoesReflection.setAtributoEmObjeto(coluna, instancia,
							resultSet.getObject(coluna));
				}

				for (ChaveEstrangeira chaveEstrangeira : chavesEstrangeiras) {
					DescricaoTabela descricaoChave = new LoaderDescricaoTabelas()
							.getDescricaoTabela(Class.forName(chaveEstrangeira
									.getNomeClasseAtributo()));

					MontadorObjeto montador = new MontadorObjeto();
					Object objetoChave = montador.montarObjeto(descricaoChave,
							resultSet, descricaoChave.getNomeTabela());
					funcoesReflection.setAtributoEmObjeto(
							chaveEstrangeira.getNomeAtributo(), instancia,
							objetoChave);

				}

				return (T) instancia;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	@Override
	public void delete(T entity) {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(descricaoTabela.getNomeTabela())
				.append(" where ").append(descricaoTabela.getColunaChave())
				.append(" = ?");

		try {
			PreparedStatement stmt = connection
					.prepareStatement(sql.toString());

			FuncoesReflection reflection = new FuncoesReflection();
			Object id = reflection.getAtributoEmObjeto(
					descricaoTabela.getColunaChave(), entity);
			stmt.setObject(1, id);
			stmt.execute();

			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void begin() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}