class Conta {
		    public int numeroConta;
		    public double saldo;
		    public String tipoConta;
		    
		    public double getSaldo(){
		        return this.saldo;
		    }
		    
		    public void depositar(double valor){
		        this.saldo += valor;
		    }
		    
		    public double sacar(double valor){
		        if(this.saldo >= valor){
		            this.saldo -= valor;
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
		        return true;
		    }
		}