package br.ufc.quixada.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.config.LoaderDescricaoTabelas;
import br.ufc.quixada.dao.ComentarioDao;
import br.ufc.quixada.model.Comentario;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class ComentarioJDBCDAO extends GenericJDBCDao<Comentario> implements ComentarioDao{

	private LoaderDescricaoTabelas loaderTabelas = new LoaderDescricaoTabelas();
	
	public ComentarioJDBCDAO() {
		super(Comentario.class);
	}

	@Override
	public List<Comentario> getComentariosLocal(Local local) {

		List<Comentario> comentarios = new ArrayList<Comentario>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from Comentario c ");
		sql.append("inner join Usuario u on u.idUsuario = c.id_usuario ");
		sql.append("inner join Local l on l.id = c.id_local ");
		sql.append("where c.id_local = ?");
		
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = connection.prepareStatement(sql.toString());
			stmt.setLong(1, local.getId());
			
			resultSet = stmt.executeQuery();
			
			while(resultSet.next()){
				
				Comentario comentario = new Comentario();
				Usuario usuarioComentario = new Usuario();
				Local localComentario = new Local();
				
				MontadorObjeto montadorObjeto = new MontadorObjeto();
				
				usuarioComentario = (Usuario)montadorObjeto.montarObjeto(loaderTabelas.getDescricaoTabela(Usuario.class), resultSet);
				
				localComentario = (Local) montadorObjeto.montarObjeto(loaderTabelas.getDescricaoTabela(Local.class), resultSet);

				comentario = (Comentario) montadorObjeto.montarObjeto(descricaoTabela, resultSet);
				comentario.setLocal(localComentario);
				comentario.setUsuario(usuarioComentario);
				
				comentarios.add(comentario);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				resultSet.close();;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return comentarios;
	}
	
}
