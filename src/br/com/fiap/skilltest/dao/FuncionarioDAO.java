package br.com.fiap.skilltest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.skilltest.domain.Cargo;
import br.com.fiap.skilltest.domain.FuncaoFuncionario;
import br.com.fiap.skilltest.domain.Funcionario;
import br.com.fiap.skilltest.helper.DateHelper;

public class FuncionarioDAO {
	
	private Connection conn;
	
	private void conecta() throws ClassNotFoundException, SQLException {
		this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", 
				"rm84281", "031091");
	}
	
	public List<FuncaoFuncionario> consultaTodos() throws ClassNotFoundException, SQLException {
		List<FuncaoFuncionario> funcao = new ArrayList<>();
		this.conecta();
		
		String sql = "select * from t_skt_func_cargo";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Long cdFunc = rs.getLong("cd_funcionario");
			Long cdCargo = rs.getLong("cd_cargo");
			LocalDate dtEntrada = rs.getDate("dt_entrada").toLocalDate();
			LocalDate dtSaida = rs.getDate("dt_saida").toLocalDate();
			Funcionario funcionario = new FuncionarioDAO().consultaPorNumero(cdFunc);
			Cargo cargo = new CargoDAO().consultaPorNumero(cdCargo);
			funcao.add(new FuncaoFuncionario(funcionario, cargo, dtEntrada, dtSaida));
		}
		
		this.desconecta();		
		return funcao;
	}

	public Funcionario consultaPorNumero(Long numero) throws ClassNotFoundException, SQLException {
		Funcionario funcionario = null;
		this.conecta();
		
		String sql = String.format("select * from t_skt_funcionario where cd_funcionario = %s", numero);
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			String nome = rs.getString("nm_funcionario");
			LocalDate dtNasc = rs.getDate("dt_nascimento").toLocalDate();
			String email = rs.getString("ds_email");
			Integer cpf = rs.getInt("nr_cpf");
			
			funcionario = new Funcionario(numero, nome, dtNasc, email, cpf);
		}
		this.desconecta();
		return funcionario;
	}
	
	public Funcionario salva(Funcionario funcionario) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = "select sq_funcionario.nextval as id from dual";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Long id = null;
		if(rs.next()) id = rs.getLong("id");
		
		if(id == null) throw new SQLException("Não foi possível gerar um identificador de funcionario");
		
		funcionario.setId(id);
		
		sql = "insert into t_skt_funcionario(cd_funcionario, nm_funcionario, dt_nascimento, ds_email, nr_cpf)"
				+ "values(?, ?, ?, ?, ?)";
		stmt = this.conn.prepareStatement(sql);
		
		stmt.setLong(1, funcionario.getId());
		stmt.setString(2, funcionario.getNome());
		stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
		stmt.setString(4, funcionario.getEmail());
		stmt.setInt(5, funcionario.getCpf());
		
		stmt.executeUpdate();
		
		this.desconecta();
		
		return funcionario;
	}
	
	public void salvaFuncionarioCargo(FuncaoFuncionario funcao) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = String.format("insert into t_skt_func_cargo(cd_funcionario, cd_cargo, dt_entrada, dt_saida)"
				+ "values(%s, %s, to_date('%s', 'dd/mm/yyyy'), to_date('%s', 'dd/mm/yyyy'))", 
				funcao.getFuncionario().getId(), funcao.getCargo().getId(), DateHelper.toText(funcao.getDataEntrada()), DateHelper.toText(funcao.getDataSaida()));
		
		Statement stmt = this.conn.createStatement();
		stmt.executeUpdate(sql);
		
		this.desconecta();	
	}	

	private void desconecta() throws SQLException {
		if(!conn.isClosed()) conn.close();
	}
}