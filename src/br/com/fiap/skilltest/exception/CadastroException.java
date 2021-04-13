package br.com.fiap.skilltest.exception;

public class CadastroException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CadastroException() {
		this("Ocorreu um erro durante o cadastro.");
	}
	
	public CadastroException(String msg) {
		super(msg);
	}
}
