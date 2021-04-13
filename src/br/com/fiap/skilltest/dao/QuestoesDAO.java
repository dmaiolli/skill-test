package br.com.fiap.skilltest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.skilltest.domain.Questoes;


public class QuestoesDAO {
	
	private Connection conn;
	
	private void conecta() throws ClassNotFoundException, SQLException {
		this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", 
				"rm84281", "031091");
	}
	
	public List<Questoes> consultaTodos() throws ClassNotFoundException, SQLException {
		List<Questoes> questoes = new ArrayList<>();
		this.conecta();
		
		String sql = "select * from t_skt_questoes";
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			Long id = rs.getLong("cd_questao");
			String descricao = rs.getString("ds_questao");
			Integer peso = rs.getInt("vl_peso_questao");
			String tipo = rs.getString("ds_tipo_questao");
			
			questoes.add(new Questoes(id, descricao, peso, tipo));
		}
		this.desconecta();		
		return questoes;
	}
	
	public Questoes consultaPorNumero(Long numero) throws ClassNotFoundException, SQLException {
		Questoes questao = null;
		this.conecta();
		
		String sql = String.format("select * from t_skt_questoes where cd_questao = %s", numero);
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			String descricao = rs.getString("ds_questao");
			Integer peso = rs.getInt("vl_peso_questao");
			String tipo = rs.getString("ds_tipo_questao");
			
			questao = new Questoes(numero, descricao, peso, tipo);
		}
		this.desconecta();
		return questao;
	}

	
	public Questoes salva(Questoes questao) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = "select sq_questoes.nextval as id from dual";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Long id = null;
		if(rs.next()) id = rs.getLong("id");
		
		if(id == null) throw new SQLException("Não foi possível gerar um identificador de reserva");
		
		questao.setId(id);
		
		sql = "insert into t_skt_questoes(cd_questao, ds_questao, vl_peso_questao, ds_tipo_questao)"
				+ "values(?, ?, ?, ?)";
		stmt = this.conn.prepareStatement(sql);
		
		stmt.setLong(1, questao.getId());
		stmt.setString(2, questao.getDescricao());
		stmt.setInt(3, questao.getPeso());
		stmt.setString(4, questao.getTipo());

		stmt.executeUpdate();
		
		this.desconecta();
		
		return questao;
	}
	
	private void desconecta() throws SQLException {
		if(!conn.isClosed()) conn.close();
	}

}
