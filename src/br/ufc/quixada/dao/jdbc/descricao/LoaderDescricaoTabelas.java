package br.ufc.quixada.dao.jdbc.descricao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ufc.quixada.model.Usuario;

public class LoaderDescricaoTabelas {
	
	private Map<String,DescricaoTabela> descricaoTabelas;
	
	public LoaderDescricaoTabelas(){
		
	}

	public DescricaoTabela getDescricaoTabela(Class persistenceClass){
		
		List<DescricaoTabela> d = new ArrayList();
		DescricaoTabela descricaoTabela = 
				new DescricaoTabela("br.ufc.quixada.model.Usuario", "usuario", "idUsuario", "email", "senha", "senhaTemporaria", "adm");
		d.add(descricaoTabela);

		DescricaoTabela descricaoTabela2 = 
				new DescricaoTabela("br.ufc.quixada.model.Local", "local", "id","id", "latitude", "longitude","banheiros",
						"garagem", "preco","qtdApartamentos","qtdQuartos","quintal","telefoneContato","endereco");
		ChaveEstrangeira chave = new ChaveEstrangeira();
		chave.setNomeAtributo("usuario");
		chave.setNomeChave("is_usuario");
		chave.setNomeClasseAtributo(Usuario.class.getName());
		List<ChaveEstrangeira> chaves = new ArrayList<ChaveEstrangeira>();
		chaves.add(chave);
		descricaoTabela2.setChavesEstrangeiras(chaves);
		d.add(descricaoTabela2);

		
		String nome = persistenceClass.getName();
		System.out.println(nome);
		if(nome.equals(Usuario.class.getName())){
			return descricaoTabela;
		}else{
			return descricaoTabela2;
		}
		
		
	}
	
}
