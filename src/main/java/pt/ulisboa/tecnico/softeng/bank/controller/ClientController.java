package pt.ulisboa.tecnico.softeng.bank.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientController {
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	

	@RequestMapping(method = RequestMethod.GET)
	public String showClients(Model model, @PathVariable String code) {
		logger.info("showClients code:{}", code);
		
		Bank bank = Bank.getBankByCode(code);
		 
		 //passar para a view um new client vazio
		 model.addAttribute("client", new Client());
		 
		model.addAttribute("bank", bank);
		return "clients";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String clientSubmit(Model model, @ModelAttribute Client client, @PathVariable String code) { //
		Bank bank = Bank.getBankByCode(code);
		logger.info("clientSubmit bankname:{}, code:{}, clientName:{}, clientId:{}", 
				bank.getName(), bank.getCode(), client.getName(), client.getId());

		try {
			//suposto guardar agora o obj na BD
			new Client(bank, client.getId(), client.getName(), client.getAge());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the client, probably the id already exists");
			model.addAttribute("bank", bank);
			return "clients";
		}
		return "redirect:/banks/bank/{code}/clients";
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public String showBank(Model model, @PathVariable String code, @PathVariable String id) {
		logger.info("showClients code:{} ; id:{}", code, id);
		
		Bank bank = Bank.getBankByCode(code);
		Client cl = null;
		
		for(Client clTemp : bank.getClients()){
			if(clTemp.getId().equals(id)){
				cl = clTemp;
				model.addAttribute("client", cl);
				break;
			}
		}
		if(cl==null){
			model.addAttribute("error", "Error: client doesn't exist");
		}
		return "client";
	}
}
