import java.util.ArrayList;
import java.util.List;

class Cliente {
		    public String nome;
		    private int cpf;
		    List<Conta> contas = new ArrayList<>();
		    
		    public void adicionarConta(int numeroConta, double saldo, String tipoConta){
		        Conta novaConta = new Conta();
		        novaConta.numeroConta = numeroConta;
		        novaConta.saldo = saldo;
		        novaConta.tipoConta = tipoConta;
		        
		        contas.add(novaConta);
		    }
		    
		    public void listarContas(){
		        for(int i = 0; i < contas.size(); i++){
		            System.out.println("Conta " + i + ":");
		            System.out.println("N° da conta: " + contas.get(i).numeroConta);
		            System.out.println("Saldo da conta: " + contas.get(i).saldo);
		            System.out.println("Tipo da conta: " + contas.get(i).tipoConta);
		        }
		    }
		    }