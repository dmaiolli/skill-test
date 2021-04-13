package br.com.fiap.skilltest.domain;


public class Questionario {
	private long id;
	private Questoes questao;
	
	public Questionario() {
	}

	public Questionario(long id, Questoes questao) {
		this.id = id;
		this.questao = questao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Questoes getQuestao() {
		return questao;
	}
	
	public void setQuestao(Questoes questao) {
		this.questao = questao;
	}

	@Override
	public String toString() {
		return String.format("\tQuestionario #%s\n%s",
				id, questao);
	}
}