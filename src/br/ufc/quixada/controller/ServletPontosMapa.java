package br.ufc.quixada.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.daoimpl.LocalJDBCDAO;
import br.ufc.quixada.model.Local;

/**
 * Servlet implementation class ServletPontosMapa
 */
@WebServlet("/ServletPontosMapa")
public class ServletPontosMapa extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JSONArray array = null;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPontosMapa() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LocalDao dao = new LocalJDBCDAO();
		List<Local> lista = dao.getLocais();
		
			array = new JSONArray();
			for (Local local : lista) {
				
				JSONObject objeto = new JSONObject();				
				objeto.put("id", local.getId());
				objeto.put("latitude", local.getLatitude());
				objeto.put("longitude", local.getLongitude());
				objeto.put("endereco", local.getEndereco());
				//DescricaoLocal descricao = local.getDescricao();
				//objeto.put("tipo", descricao.getTipo());
				array.add(objeto);
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(array);
			out.flush();
			
	}
	
}
