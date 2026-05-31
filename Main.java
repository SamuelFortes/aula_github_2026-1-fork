import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static final Map<Integer, Conta> contas = new HashMap<>();

	public static void main(String[] args) {
		Menu mainMenu =  new Menu("Menu Principal", Arrays.asList("Conta", "Cliente", "Operacoes"));
		int selection = mainMenu.getSelection();
		if (selection == 3) {
			executarOperacoes();
		} else {
			System.out.println(selection + " foi selecionada");
		}
		System.out.println("Fim");
	}

	private static void executarOperacoes() {
		Menu operacoesMenu = new Menu("Menu de Operacoes", Arrays.asList("Deposito", "Saque"));
		int operacao = operacoesMenu.getSelection();

		System.out.println("Informe o numero da conta: ");
		int numeroConta = Integer.parseInt(Menu.readLine());
		Conta conta = contas.get(numeroConta);
		if (conta == null) {
			conta = new Conta();
			contas.put(numeroConta, conta);
		}

		System.out.println("Informe o valor: ");
		double valor = Double.parseDouble(Menu.readLine().replace(',', '.'));

		if (operacao == 1) {
			conta.depositar(valor);
			System.out.println("Deposito realizado.");
		} else {
			double saque = conta.sacar(valor);
			if (saque > 0) {
				System.out.println("Saque realizado.");
			} else {
				System.out.println("Saque nao realizado.");
			}
		}

		System.out.println("Saldo atualizado: " + conta.getSaldo());
	}

}