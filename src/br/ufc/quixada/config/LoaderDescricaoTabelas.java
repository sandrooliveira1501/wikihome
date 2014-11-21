package br.ufc.quixada.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.daoimpl.LocalJDBCDAO;
import br.ufc.quixada.dao.jdbc.descricao.ChaveEstrangeira;
import br.ufc.quixada.dao.jdbc.descricao.DescricaoTabela;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class LoaderDescricaoTabelas {
	
	private Map<String,DescricaoTabela> descricaoTabelas;
	
	public LoaderDescricaoTabelas(){
		
	}

	public DescricaoTabela getDescricaoTabela(Class persistenceClass){
		
		DescricaoTabela d1 = new DescricaoTabela("br.ufc.quixada.model.Usuario", "usuario", "idUsuario","idUsuario", "nome", "senha","senhaTemporaria", "email");
		DescricaoTabela d2 = new DescricaoTabela("br.ufc.quixada.model.Local", "local", "id","endereco", "latitude", "longitude","banheiros", "garagem", "quintal", "emailContato", "preco", "qtdQuartos", "qtdApartamentos", "telefoneContato", "tipo");
		ChaveEstrangeira c = new ChaveEstrangeira();
		c.setNomeChave("id_usuario");
		c.setNomeAtributo("usuario");
		c.setNomeClasseAtributo("br.ufc.quixada.model.Usuario");
		List<ChaveEstrangeira> l = new ArrayList<ChaveEstrangeira>();
		l.add(c);
		d2.setChavesEstrangeiras(l);
		
		if(persistenceClass.getName().equals(Usuario.class.getName())){
			return d1;
		}else{
			return d2;
		}
		
	}
	
}
