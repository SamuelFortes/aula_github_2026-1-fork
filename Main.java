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
    private static final String CAMINHO_ARQUIVO = "contas.txt";
    private static final Map<Integer, Cliente> clientes = new HashMap<>(); 

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
        Menu operacoesMenu = new Menu("Menu de Operacoes", Arrays.asList("Deposito", "Saque", "Transferencia"));
        int operacao = operacoesMenu.getSelection();

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
                return;
            }

            if (origem.transferirPara(destino, valor)) {
                System.out.println("Transferencia realizada.");
                System.out.println("Saldo atualizado da origem: " + origem.getSaldo());
                System.out.println("Saldo atualizado do destino: " + destino.getSaldo());
            } else {
                System.out.println("Transferencia nao realizada.");
            }
            return;
        }

        System.out.println("Informe o numero da conta: ");
        int numeroConta = Integer.parseInt(Menu.readLine());
        Conta conta = contas.get(numeroConta);

        if (conta == null) {
            conta = new Conta();
            conta.numeroConta = numeroConta;
            contas.put(numeroConta, conta);
        }

        System.out.println("Informe o valor: ");
        double valor = Double.parseDouble(Menu.readLine().replace(',', '.'));

        if (operacao == 1) {
            conta.depositar(valor);
            System.out.println("Deposito realizado. Saldo atualizado: " + conta.getSaldo());
        } else {
            double saque = conta.sacar(valor);
            if (saque > 0) {
                System.out.println("Saque realizado.");
                System.out.println("Saldo atualizado: " + conta.getSaldo());
            } else {
                System.out.println("Saque nao realizado.");
            }
        }
    }

    private static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Cliente cliente : clientes.values()) {
                writer.write("CLIENTE;" + cliente.getCpf() + ";" + cliente.nome);
                writer.newLine();
            }
        
            writer.write("NUMERO_CONTA;SALDO;TIPO_CONTA");
            writer.newLine();
            
          
            for (Map.Entry<Integer, Conta> entry : contas.entrySet()) {
                int numero = entry.getKey();
                Conta conta = entry.getValue();
                writer.write(numero + ";" + conta.getSaldo() + ";" + (conta.tipoConta != null ? conta.tipoConta : "Corrente"));
                writer.newLine();
            }
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
  
                if (linha.trim().isEmpty() || linha.startsWith("NUMERO_CONTA")) {
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length == 3) {
             
                    if (partes[0].equals("CLIENTE")) {
                        Cliente c = new Cliente();
                        int cpf = Integer.parseInt(partes[1]); 
                        c.cpf = cpf;
                        c.nome = partes[2];
                        clientes.put(cpf, c);
                    } 
                 
                    else {
                        int numero = Integer.parseInt(partes[0]);
                        double saldo = Double.parseDouble(partes[1]);
                        String tipo = partes[2];

                        Conta conta = new Conta();
                        conta.numeroConta = numero;
                        conta.saldo = saldo;
                        conta.tipoConta = tipo;
                        contas.put(numero, conta); 
                    }
                }
            }
           
            System.out.println("Dados carregados com sucesso!");
            System.out.println("Clientes recuperados na memoria RAM: " + clientes.size());
            System.out.println("Contas recuperadas na memoria RAM: " + contas.size());
            System.out.println("-----------------------------------------------\n");
        } catch (IOException e) {
            System.out.println("Nenhum historico encontrado. Iniciando banco vazio.");
        } catch (NumberFormatException e) {
            System.out.println("Erro de conversao: um valor numerico no arquivo esta invalido ou o CPF estourou o limite. " + e.getMessage());
        }
    }
}
