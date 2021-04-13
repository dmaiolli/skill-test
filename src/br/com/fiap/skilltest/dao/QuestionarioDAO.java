package br.com.fiap.skilltest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.skilltest.domain.Questionario;
import br.com.fiap.skilltest.domain.Questoes;


public class QuestionarioDAO {
private Connection conn;
	
	private void conecta() throws ClassNotFoundException, SQLException {
		this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", 
				"rm84281", "031091");
	}
	
	public void salva(Questionario questionario) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = "select sq_questionario.nextval as id from dual";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Long id = null;
		if(rs.next()) id = rs.getLong("id");
		
		if(id == null) throw new SQLException("Não foi possível gerar um identificador de questionario");
		
		questionario.setId(id);
		
		sql = "insert into t_skt_questionario(cd_questionario)"
				+ "values(?)";
		stmt = this.conn.prepareStatement(sql);
		
		stmt.setLong(1, questionario.getId());
		
		stmt.executeUpdate();
		
		this.desconecta();
	}		

	public void salvaQuestoesQuestionario(Questionario questionario, Questoes questao) throws ClassNotFoundException, SQLException {
		this.conecta();
		
		String sql = String.format("insert into t_skt_question_questoes(cd_questionario, cd_questao)"
				+ "values(%s, %s)", questionario.getId(), questao.getId());
		
		Statement stmt = this.conn.createStatement();
		stmt.executeUpdate(sql);
		
		this.desconecta();	
	}	

	public List<Questionario> consultaTodos() throws ClassNotFoundException, SQLException {
		List<Questionario> questionario = new ArrayList<>();
		this.conecta();
		
		String sql = "select * from t_skt_question_questoes";
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Long cdQuestion = rs.getLong("cd_questionario");
			Long cdQuestao = rs.getLong("cd_questao");
			Questoes questao1 = new QuestoesDAO().consultaPorNumero(cdQuestao);
			questionario.add(new Questionario(cdQuestion, questao1));
		}
		this.desconecta();		
		return questionario;
	}

	private void desconecta() throws SQLException {
	if(!conn.isClosed()) conn.close();
	}
}