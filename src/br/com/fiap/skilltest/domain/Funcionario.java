package br.com.fiap.skilltest.domain;

import java.time.LocalDate;

public class Funcionario {
	private Long id;
	private String nome;
	private LocalDate dataNascimento;
	private String email;
	private Integer cpf;
	
	public Funcionario(String nome, LocalDate dataNascimento, String email, Integer cpf) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.cpf = cpf;
	}

	public Funcionario(Long id, String nome, LocalDate dataNascimento, String email, Integer cpf) {
		this(nome, dataNascimento, email, cpf);
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Integer getCpf() {
		return cpf;
	}
	
	@Override
	public String toString() {
		return String.format("\tFuncionario #%s -> {\n\tNome: %s, \n\tCPF: %s, \n\tE-mail: %s",
				id, nome, cpf, email);
	}
}