package br.ufrn.monitoramentotemperatura.model;

import java.io.Serializable;

import br.ufrn.monitoramentotemperatura.rmi.TempClientInterface;

public class Request implements Serializable {
	private Operacao operacao;
	private int x;
	private int y;
	private br.ufrn.monitoramentotemperatura.rmi.TempClientInterface cliente;
	
	
	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Request(Operacao operacao, int x, TempClientInterface cliente) {
		super();
		this.operacao = operacao;
		this.x = x;
		this.cliente = cliente;
	}
	
	public Request(Operacao operacao, int x, int y, TempClientInterface cliente) {
		super();
		this.operacao = operacao;
		this.x = x;
		this.y = y;
		this.cliente = cliente;
	}
	public Operacao getOperacao() {
		return operacao;
	}
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public TempClientInterface getCliente() {
		return cliente;
	}
	public void setCliente(TempClientInterface cliente) {
		this.cliente = cliente;
	}
	
	
	
	
}
