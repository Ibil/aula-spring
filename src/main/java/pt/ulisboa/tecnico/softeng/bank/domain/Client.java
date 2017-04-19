package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Client {
	private Bank bank;
	private String id;
	private String name;
	private int age;

	public Client() {
	}

	public Client(Bank bank, String id, String name, int age) {
		for(Client cl : bank.getClients()){
			if(cl.getId().equals(id)){
				throw new BankException();
			}
		}
		this.bank = bank;
		this.id = id;
		this.name = name;
		this.age = age;

		bank.addClient(this);
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
