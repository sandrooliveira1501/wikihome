package br.ufc.quixada.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.config.DescricaoTabela;
import br.ufc.quixada.config.LoaderDescricaoTabelas;
import br.ufc.quixada.dao.GenericDao;
import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.exception.ErroAoExecutarOperacaoException;
import br.ufc.quixada.model.DescricaoLocal;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class LocalJDBCDAO extends GenericJDBCDao<Local> implements LocalDao{
	
	private MontadorObjeto montadorObjeto = new MontadorObjeto();
	
	public LocalJDBCDAO() {
		super(Local.class);
	}
	
	@Override
	public List<Local> getLocais() {

		List<Local> locais = new ArrayList<Local>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from Local l inner join Usuario u on u.idUsuario = l.id_usuario inner join DescricaoLocal d on d.idDescricao = l.id_descricao");

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.prepareStatement(sql.toString());
			resultSet = stmt.executeQuery();

			while(resultSet.next()){
				Local local = (Local) montadorObjeto.montarObjeto(descricaoTabela, resultSet);
				DescricaoTabela descricaoChaveUsuario = new LoaderDescricaoTabelas().getDescricaoTabela(Usuario.class);
				DescricaoTabela descricaoChave = new LoaderDescricaoTabelas().getDescricaoTabela(DescricaoLocal.class);
				
				Usuario usuarioLocal = (Usuario) montadorObjeto.montarObjeto(descricaoChaveUsuario, resultSet);
				local.setUsuario(usuarioLocal);

				DescricaoLocal descricao = (DescricaoLocal) montadorObjeto.montarObjeto(descricaoChave, resultSet);
				local.setDescricao(descricao);
				
				locais.add(local);
			}
		
		} catch (SQLException e) {
			throw new ErroAoExecutarOperacaoException("Pegar todos os locais", e.getMessage());

		}finally{
			try {
				stmt.close();
				resultSet.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());
			}
		}

		return locais;
	}

	@Override
	public List<Local> getLocalPorUsuario(Usuario usuario) {

		List<Local> locais = new ArrayList<Local>();

		StringBuilder sql = new StringBuilder();
		sql.append("select * from Local l inner join Usuario u on u.idUsuario = l.id_usuario where u.idUsuario = ?");

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.prepareStatement(sql.toString());
			stmt.setLong(1, usuario.getIdUsuario());
			resultSet = stmt.executeQuery();

			while(resultSet.next()){
				Local local = (Local) montadorObjeto.montarObjeto(descricaoTabela, resultSet);
				DescricaoTabela descricaoChave = new LoaderDescricaoTabelas().getDescricaoTabela(Usuario.class);
				
				Usuario usuarioLocal = (Usuario) montadorObjeto.montarObjeto(descricaoChave, resultSet);
				local.setUsuario(usuarioLocal);
				System.out.println(local);
				locais.add(local);
			}
		
		} catch (SQLException e) {
			throw new ErroAoExecutarOperacaoException("Pegar locais por usuário", e.getMessage());
		}finally{
			try {
				stmt.close();
				resultSet.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Erro na conexão", e.getMessage());

			}
		}
		
		return locais;
				
	}
	
	@Override
	public void save(Local local) {

		GenericJDBCDao<DescricaoLocal> genericDao = new GenericJDBCDao<DescricaoLocal>(DescricaoLocal.class);
		
		DescricaoLocal descricaoLocal = local.getDescricao();
		
		int idDescricao = genericDao.saveId(descricaoLocal);
		descricaoLocal.setIdDescricao(idDescricao);
		local.setDescricao(descricaoLocal);
		
		saveId(local);
		
	}
	
	
}
