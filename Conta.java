import java.util.ArrayList;
import java.util.List;

class Conta {
		    public int numeroConta;
		    public double saldo;
		    public String tipoConta;
		    private final List<String> historico = new ArrayList<>();
		    
		    public double getSaldo(){
		        return this.saldo;
		    }
		    
		    public void depositar(double valor){
		        this.saldo += valor;
		        historico.add("Deposito: +" + valor);
		    }
		    
		    public double sacar(double valor){
		        if(this.saldo >= valor){
		            this.saldo -= valor;
		            historico.add("Saque: -" + valor);
		            return valor;
		            
		        } else {
		            System.out.println("Saldo Insuficiente!");
                    return 0;
		        }
		    }

		    public boolean transferirPara(Conta destino, double valor){
		        if(destino == null || valor <= 0 || this.saldo < valor){
		            return false;
		        }
		        this.saldo -= valor;
		        destino.saldo += valor;
		        historico.add("Transferencia para conta " + destino.numeroConta + ": -" + valor);
		        destino.historico.add("Transferencia recebida da conta " + this.numeroConta + ": +" + valor);
		        return true;
		    }

		    public void exibirHistorico(){
		        if(historico.isEmpty()){
		            System.out.println("Nenhuma transacao registrada.");
		            return;
		        }

		        System.out.println("Historico da conta " + numeroConta + ":");
		        for(String transacao : historico){
		            System.out.println("- " + transacao);
		        }
		    }
		}
