import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static final Map<Integer, Conta> contas = new HashMap<>();
    private static final Map<Integer, Cliente> clientes = new HashMap<>();

    private static final String CAMINHO_CONTAS = "contas.txt";
    private static final String CAMINHO_CLIENTES = "clientes.txt";

    public static void main(String[] args) {
        carregarDados();

        Menu mainMenu = new Menu("Menu Principal", Arrays.asList("Conta", "Cliente", "Operacoes"));
        int selection = mainMenu.getSelection();

        if (selection == 2) {
            System.out.println("--- CADASTRO DE CLIENTE ---");

            System.out.println("Informe o nome do cliente: ");
            String nome = Menu.readLine();

            System.out.println("Informe o CPF do cliente: ");
            int cpf = Integer.parseInt(Menu.readLine());

            Cliente c = new Cliente();
            c.nome = nome;
            c.cpf = cpf;

            clientes.put(cpf, c);

            System.out.println("Cliente cadastrado com sucesso!");

            salvarDados();
        } 
        else if (selection == 3) {
            executarOperacoes();
            salvarDados();
        } 
        else {
            System.out.println(selection + " foi selecionada");
        }

        System.out.println("Fim");
    }

    private static void executarOperacoes() {
        Menu operacoesMenu = new Menu(
            "Menu de Operacoes",
            Arrays.asList("Deposito", "Saque", "Transferencia", "Historico", "Sair")
        );

        while (true) {
            int operacao = operacoesMenu.getSelection();

            if (operacao == 5) {
                return;
            }

            if (operacao == 3) {
                System.out.println("Informe o numero da conta de origem: ");
                int numeroOrigem = Integer.parseInt(Menu.readLine());

                System.out.println("Informe o numero da conta de destino: ");
                int numeroDestino = Integer.parseInt(Menu.readLine());

                System.out.println("Informe o valor: ");
                double valor = Double.parseDouble(Menu.readLine().replace(',', '.'));

                Conta origem = contas.get(numeroOrigem);
                Conta destino = contas.get(numeroDestino);

                if (origem == null || destino == null) {
                    System.out.println("Transferencia nao realizada. Conta de origem ou destino inexistente.");
                    continue;
                }

                if (origem.transferirPara(destino, valor)) {
                    System.out.println("Transferencia realizada.");
                    System.out.println("Saldo atualizado da origem: " + origem.getSaldo());
                    System.out.println("Saldo atualizado do destino: " + destino.getSaldo());
                } else {
                    System.out.println("Transferencia nao realizada.");
                }

                continue;
            }

            System.out.println("Informe o numero da conta: ");
            int numeroConta = Integer.parseInt(Menu.readLine());

            Conta conta = contas.get(numeroConta);

            if (operacao == 4) {
                if (conta == null) {
                    System.out.println("Conta inexistente.");
                    continue;
                }

                conta.exibirHistorico();
                continue;
            }

            if (conta == null) {
                conta = new Conta();
                conta.numeroConta = numeroConta;
                contas.put(numeroConta, conta);
            }

            System.out.println("Informe o valor: ");
            double valor = Double.parseDouble(Menu.readLine().replace(',', '.'));

            if (operacao == 1) {
                conta.depositar(valor);
                System.out.println("Deposito realizado.");
            } 
            else if (operacao == 2) {
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

    private static void salvarDados() {
        salvarClientes();
        salvarContas();
    }

    private static void carregarDados() {
        carregarClientes();
        carregarContas();
    }

    private static void salvarClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_CLIENTES))) {
            for (Cliente cliente : clientes.values()) {
                writer.write(cliente.cpf + ";" + cliente.nome);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    private static void carregarClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_CLIENTES))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";", 2);

                if (partes.length == 2) {
                    Cliente cliente = new Cliente();
                    cliente.cpf = Integer.parseInt(partes[0]);
                    cliente.nome = partes[1];

                    clientes.put(cliente.cpf, cliente);
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe. Não é erro grave.
        }
    }

    private static void salvarContas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_CONTAS))) {
            for (Conta conta : contas.values()) {
                writer.write(conta.numeroConta + ";" + conta.getSaldo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    private static void carregarContas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_CONTAS))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");

                if (partes.length == 2) {
                    int numeroConta = Integer.parseInt(partes[0]);
                    double saldo = Double.parseDouble(partes[1]);

                    Conta conta = new Conta();
                    conta.numeroConta = numeroConta;

                    if (saldo > 0) {
                        conta.depositar(saldo);
                    }

                    contas.put(numeroConta, conta);
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe. Não é erro grave.
        }
    }
}