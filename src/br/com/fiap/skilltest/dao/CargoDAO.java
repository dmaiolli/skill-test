package br.com.fiap.skilltest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.skilltest.domain.Cargo;

public class CargoDAO {
	
	private Connection conn;
	
	private void conecta() throws ClassNotFoundException, SQLException {
		this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", 
				"rm84281", "031091");
	}
	
	public List<Cargo> consultaTodos() throws ClassNotFoundException, SQLException {
		List<Cargo> cargo = new ArrayList<>();
		this.conecta();
		
		String sql = "select * from t_skt_cargo";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Long id = rs.getLong("cd_cargo");
			String nome = rs.getString("nm_cargo");
			
			cargo.add(new Cargo(id, nome));
		}
		this.desconecta();		
		return cargo;
	}

	public Cargo consultaPorNumero(Long numero) throws ClassNotFoundException, SQLException {
		Cargo cargo = null;
		this.conecta();
		
		String sql = String.format("select * from t_skt_cargo where cd_cargo = %s", numero);
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			String nome = rs.getString("nm_cargo");
			
			cargo = new Cargo(numero, nome);
		}
		this.desconecta();
		return cargo;
	}
	
	public Cargo salva(Cargo cargo) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = "select sq_cargo.nextval as id from dual";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Long id = null;
		if(rs.next()) id = rs.getLong("id");
		
		if(id == null) throw new SQLException("Não foi possível gerar um identificador do cargo");
		
		cargo.setId(id);
		
		sql = "insert into t_skt_cargo(cd_cargo, nm_cargo)"
				+ "values(?, ?)";
		stmt = this.conn.prepareStatement(sql);
		
		stmt.setLong(1, cargo.getId());
		stmt.setString(2, cargo.getNome());
		
		stmt.executeUpdate();
		
		this.desconecta();
		
		return cargo;
	}

	private void desconecta() throws SQLException {
		if(!conn.isClosed()) conn.close();
	}

}
