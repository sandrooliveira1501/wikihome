package br.ufc.quixada.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.model.Usuario;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class LoaderDescricaoTabelas {
	
	private List<DescricaoTabela> descricaoTabelas;
	private XStream stream;
	
	
	public LoaderDescricaoTabelas(){
		this.stream = new XStream(new DomDriver());
	}
	
	private void configurarClassesParaAlias(){
		stream.alias("DescricaoTabela", DescricaoTabela.class);
		stream.alias("ChaveEstrangeira", ChaveEstrangeira.class);
		stream.alias("String", String.class);
		stream.alias("List", List.class);
	}
	
	
	private void carregarElementosXMLLista(){
		
		File file = new File(LoaderDescricaoTabelas.class.getResource("/META-INF/persistenceJDBC.xml").getPath());
		
		if(!file.exists()){
			throw new RuntimeException("Erro ao abrir arquivo xml");
		}
		
		try{
			this.descricaoTabelas =  (ArrayList) stream.fromXML(file);
		}catch(Exception e){
			throw new RuntimeException("Erro ao ler XML");
		}
	}

	private void carregarReader() {
		configurarClassesParaAlias();
		carregarElementosXMLLista();
	}

	public DescricaoTabela getDescricaoTabela(Class persistenceClass){
		carregarReader();
		carregarElementosXMLLista();
		
		for (DescricaoTabela descricaoTabela : descricaoTabelas) {
			
			if(descricaoTabela.getNome().equals(persistenceClass.getName())){
				return descricaoTabela;
			}
			
		}
		
		return null;
		
	}
	
}
