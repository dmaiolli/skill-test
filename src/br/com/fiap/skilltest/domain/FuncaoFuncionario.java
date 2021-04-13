package br.com.fiap.skilltest.domain;

import java.time.LocalDate;

public class FuncaoFuncionario {
	private Funcionario funcionario;
	private Cargo cargo;
	private LocalDate dataEntrada;
	private LocalDate dataSaida;
	
	public FuncaoFuncionario(Funcionario funcionario, Cargo cargo, LocalDate dataEntrada, LocalDate dataSaida) {
		super();
		this.funcionario = funcionario;
		this.cargo = cargo;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}
	
	@Override
	public String toString() {
		return String.format("#%s \n%s, \n\tData de entrada: %s, \n\tData de saida: %s}\n",
				funcionario, cargo, dataEntrada, dataSaida);
	}

}
