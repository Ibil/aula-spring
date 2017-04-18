package pt.ulisboa.tecnico.softeng.bank.controller;

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
	
	public Bank bank;

	@RequestMapping(method = RequestMethod.GET)
	public String showClients(Model model, @PathVariable String code) {
		logger.info("showClients code:{}", code);
		
		 if(bank == null){
			 bank = Bank.getBankByCode(code);
		 }
		
		model.addAttribute("bank", bank);
		return "clients";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String clientSubmit(Model model, @ModelAttribute Bank bank, @ModelAttribute Client client) {
		logger.info("clientSubmit bankname:{}, code:{}, clientName:{}, clientId:{}", 
				bank.getName(), bank.getCode(), client.getName(), client.getId());

		try {
			new Client(bank, client.getId(), client.getName(), client.getAge());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the client");
			model.addAttribute("bank", bank);
			return "redirect:/clients";
		}
		model.addAttribute("error", "");
		model.addAttribute("bank", bank);
		return "redirect:/clients";
	}
//
//	@RequestMapping(value = "/bank/{code}", method = RequestMethod.GET)
//	public String showBank(Model model, @PathVariable String code) {
//		logger.info("showBank code:{}", code);
//
//		Bank bank = Bank.getBankByCode(code);
//
//		new Client(bank, "ID01", "Zé", 22);
//		new Client(bank, "ID02", "Manel", 44);
//
//		model.addAttribute("bank", bank);
//		return "bank";
//	}
}
