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
import br.ufc.quixada.dao.GenericDao;
import br.ufc.quixada.dao.jdbc.exception.ErroAoExecutarOperacaoException;

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
		saveId(entity);
	}
	
	protected int saveId(T entity){
		
		int chaveGerada = -1;
		PreparedStatement statement = null;
		try {
			Class classEntidade = entity.getClass();
			String sql = genericSQL.getSqlSave(descricaoTabela);

			List<String> colunas = descricaoTabela.getColunas();
			List<ChaveEstrangeira> chavesEstrangeiras = descricaoTabela
					.getChavesEstrangeiras();

			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
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
			ResultSet keyResultSet = statement.getGeneratedKeys();
			if (keyResultSet.next()) {
				chaveGerada = (int) keyResultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new ErroAoExecutarOperacaoException("Inserir entidade", e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ErroAoExecutarOperacaoException("Buscar Entidade - Erro de mapeamento", e.getMessage());			
		}finally{
			try {
				statement.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());
			}

		}
		
		return chaveGerada;
	}

	@Override
	public void update(T entity) {


		PreparedStatement stmt = null;
		try {
			String sql = genericSQL.getSqlUpdate(descricaoTabela);
			stmt = connection
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

		} catch (SQLException e) {
			throw new ErroAoExecutarOperacaoException("ALterar dados da entidade", e.getMessage());
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());
			}
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
			throw new ErroAoExecutarOperacaoException("Buscar entidade", e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ErroAoExecutarOperacaoException("Classe não encontrada, erro no mapeamento xml", e.getMessage());
		} finally {
			try {
				resultSet.close();
				stmt.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());
			}

		}
	}

	@Override
	public void delete(T entity) {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(descricaoTabela.getNomeTabela())
				.append(" where ").append(descricaoTabela.getColunaChave())
				.append(" = ?");
		
		PreparedStatement stmt = null;
		
		try {
			stmt = connection
					.prepareStatement(sql.toString());

			FuncoesReflection reflection = new FuncoesReflection();
			Object id = reflection.getAtributoEmObjeto(
					descricaoTabela.getColunaChave(), entity);
			stmt.setObject(1, id);
			stmt.execute();


		} catch (SQLException e) {
			throw new ErroAoExecutarOperacaoException("Remover entidade", e.getMessage());
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());
			}
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
