package br.com.fiap.skilltest.domain;

public class Questoes {
	private Long id;
	private String descricao;
	private String tipo;
	private Integer peso;
	private Double notas;
	
	public Questoes(String descricao, Integer peso, String tipo) {
		this.descricao = descricao;
		this.tipo = tipo;
		this.peso = peso;
		this.notas = 0.0;
	}

	public Questoes(Long id, String descricao, Integer peso, String tipo) {
		this(descricao, peso, tipo);
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getNotas() {
		return notas;
	}

	public void setNotas(Double notas) {
		this.notas = notas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public Integer getPeso() {
		return peso;
	}
	
	@Override
	public String toString() {
		return String.format("\tQuestao #%s -> {\n\tQuestao: %s, \n\tTipo: %s, \n\tPeso %s}\n",
				id, descricao, tipo, peso);
	}
}