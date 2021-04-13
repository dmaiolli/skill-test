package br.com.fiap.skilltest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.fiap.skilltest.dao.CargoDAO;
import br.com.fiap.skilltest.dao.FuncionarioDAO;
import br.com.fiap.skilltest.dao.QuestionarioDAO;
import br.com.fiap.skilltest.dao.QuestoesDAO;
import br.com.fiap.skilltest.domain.Cargo;
import br.com.fiap.skilltest.domain.FuncaoFuncionario;
import br.com.fiap.skilltest.domain.Funcionario;
import br.com.fiap.skilltest.domain.Questionario;
import br.com.fiap.skilltest.domain.Questoes;
import br.com.fiap.skilltest.exception.CadastroException;
import br.com.fiap.skilltest.helper.DateHelper;
import br.com.fiap.skilltest.validation.CadastroValidator;

public class App {

	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			int opcao = 0;
			do {
				menu();
				System.out.print("> ");
				opcao = scan.nextInt();

				switch (opcao) {
					case 1 -> cadastraFuncionario(scan);
					case 2 -> cadastraCargo(scan);
					case 3 -> cadastraQuestoes(scan);
					case 4 -> cadastraQuestionario(scan);
					case 5 -> consultaFuncionario();
					case 6 -> consultaCargo();
					case 7 -> consultaQuestoes();
					case 8 -> consultaQuestionario();
				}

				System.out.println("\n\n");
			} while (opcao != 0);

			System.out.println("#--- Programa finalizado com sucesso ---#");
		}
	}
	
	private static void cadastraFuncionario(Scanner scan) {
		System.out.println("\n#---> Cadastro de Funcionario");
		scan.nextLine();
		
		System.out.print("\nInforme o nome do funcionario: > ");
		String nome = scan.nextLine();
		
		System.out.print("\nInforme a data de nascimento do funcionario: > ");
		LocalDate dtNascimento = DateHelper.toDate(scan.nextLine());

		System.out.print("\nInforme o email do funcionario: > ");
		String email = scan.nextLine();
		
		System.out.print("\nInforme o cpf do funcionario: > ");
		Integer cpf = scan.nextInt();
		
		Funcionario funcionario = new Funcionario(nome, dtNascimento, email, cpf);

		try {
			new FuncionarioDAO().salva(funcionario);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.print("\nInforme em quantos cargos o funcionario ira trabalhar: > ");
		Integer qtdeCargo = scan.nextInt();

		for(int i = 0; i < qtdeCargo; i++) {
			System.out.print("\nInforme o codigo do cargo do funcionario: > ");
			Long cdCargo = scan.nextLong();
			scan.nextLine();
			
			System.out.print("\nInforme a data de entrada do funcionario no cargo: > ");
			LocalDate dataEntrada = DateHelper.toDate(scan.nextLine());
			
			System.out.print("\nInforme a data de saida do funcionario no cargo: > ");
			LocalDate dataSaida = DateHelper.toDate(scan.nextLine());
			try {
				Cargo cargo = new CargoDAO().consultaPorNumero(cdCargo);
				FuncaoFuncionario funcao = new FuncaoFuncionario(funcionario, cargo, dataEntrada, dataSaida);
				CadastroValidator.validaDataEntrada(funcao);
				new FuncionarioDAO().salvaFuncionarioCargo(funcao);
				} catch (ClassNotFoundException | SQLException | CadastroException e) {
					e.printStackTrace();
			}
		}
			
		System.out.println("\nFuncionario cadastrado com sucesso #---> ");
	}

	private static void cadastraCargo(Scanner scan) {
		System.out.println("\n#---> Cadastro de Cargo");
		scan.nextLine();
		System.out.print("\nInforme o cargo: > ");
		String nome = scan.nextLine();

		Cargo cargo = new Cargo(nome);

		try {
			new CargoDAO().salva(cargo);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nCargo cadastrado com sucesso #---> ");
	}

	private static void cadastraQuestoes(Scanner scan) {
		System.out.println("\n#---> Cadastro de Questoes");
		scan.nextLine();
		System.out.print("\nInsira uma questao: > ");
		String nome = scan.nextLine();
		
		System.out.println("\nInforme o tipo da questao.");
		System.out.print("Digite: 1 - HABILIDADE | 2 - PERGUNTA > ");
		String tipo = scan.nextInt() == 1 ? "HABILIDADE" : "PERGUNTA";
		
		System.out.print("\nInforme o peso da questao na nota final: > ");
		Integer peso = scan.nextInt();

		Questoes questao = new Questoes(nome, peso, tipo);

		try {
			new QuestoesDAO().salva(questao);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nQuestao cadastrada com sucesso #---> ");
	}
	
	private static void cadastraQuestionario(Scanner scan) {
		System.out.println("\n#---> Cadastro de Questionario");
		
		Questionario questionario = new Questionario();
		try {
			new QuestionarioDAO().salva(questionario);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.print("\nDigite a quantidade de questoes no questionario: > ");
		Integer qtdeQuestoes = scan.nextInt();
		scan.nextLine();
		for(int i = 0; i < qtdeQuestoes; i++) {
			System.out.print("\nInforme o codigo da questao: > ");
			Long cdQuestao = scan.nextLong();

			try {
				Questoes questao = new QuestoesDAO().consultaPorNumero(cdQuestao);
				new QuestionarioDAO().salvaQuestoesQuestionario(questionario, questao);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\nQuestionario cadastrado com sucesso #---> ");
	}

	private static void consultaFuncionario() {
		System.out.println("\n#---> Consulta Funcionarios\n");

		try {
			List<FuncaoFuncionario> funcionarios = new FuncionarioDAO().consultaTodos();
			funcionarios.forEach(System.out::println);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nConsulta finalizada com sucesso #---> ");
	}

	private static void consultaCargo() {
		System.out.println("\n#---> Consulta Cargos\n");

		try {
			List<Cargo> cargos = new CargoDAO().consultaTodos();
			cargos.forEach(System.out::println);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nConsulta finalizada com sucesso #---> ");
	}

	private static void consultaQuestoes() {
		System.out.println("\n#---> Consulta Questoes\n");

		try {
			List<Questoes> questoes = new QuestoesDAO().consultaTodos();
			questoes.forEach(System.out::println);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nConsulta finalizada com sucesso #---> ");
	}

	private static void consultaQuestionario() {
		System.out.println("\n#---> Consulta Questionarios\n");

		try {
			List<Questionario> questionarios = new QuestionarioDAO().consultaTodos();
			questionarios.forEach(System.out::println);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("\nConsulta finalizada com sucesso #---> ");
	}

	private static void menu() {
		System.out.println("|-----------------------------|");
		System.out.println("|          SkillTest          |");
		System.out.println("|                             |");
		System.out.println("| Digite a opção desejada:    |");
		System.out.println("| 1 - Cadastrar funcionario   |");
		System.out.println("| 2 - Cadastrar cargo         |");
		System.out.println("| 3 - Cadastrar questoes      |");
		System.out.println("| 4 - Cadastrar questionario  |");
		System.out.println("| 5 - Consultar funcionario   |");
		System.out.println("| 6 - Consultar cargo         |");		
		System.out.println("| 7 - Consultar questoes      |");
		System.out.println("| 8 - Consultar questionario  |");
		System.out.println("| 0 - Sair do sistema         |");
		System.out.println("|-----------------------------|");
	}
}