package br.com.fiap.skilltest.domain;


public class Cargo {
	private Long id;
	private String nome;

	public Cargo(String nome) {
		this.nome = nome;
	}

	public Cargo(Long id, String nome) {
		this(nome);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return String.format("\tCargo #%s \n\tNome: %s",
				id, nome);
	}
}