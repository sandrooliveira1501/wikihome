package br.ufc.quixada.dao.jdbc.reflection;

import java.lang.reflect.Field;

public class FuncoesReflection {

	public Object criarInstancia(String nomeClasse) {

		try {
			Class classe = Class.forName(nomeClasse);
			Object obj = classe.newInstance();
			return obj;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void setAtributoEmObjeto(String nomeAtributo, Object instancia,
			Object atributoObjeto) {

			Class classeObjeto = instancia.getClass();
			
			try {
				Field atributo = classeObjeto.getDeclaredField(nomeAtributo);
				atributo.setAccessible(true);
				atributo.set(instancia, atributoObjeto);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public Object getAtributoEmObjeto(String nomeAtributo, Object instancia) {

		Class classeObjeto = instancia.getClass();

		Object objetoRetorno = null;


			try {
				Field atributo = classeObjeto.getDeclaredField(nomeAtributo);
				atributo.setAccessible(true);
				objetoRetorno = atributo.get(instancia);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return objetoRetorno;
	}
	
}
