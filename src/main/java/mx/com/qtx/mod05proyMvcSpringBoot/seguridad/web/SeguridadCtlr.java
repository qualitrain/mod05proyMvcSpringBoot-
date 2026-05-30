package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeguridadCtlr {
	
	private static Logger bitacora =  LoggerFactory.getLogger(SeguridadCtlr.class);

	  
	public SeguridadCtlr() {
		bitacora.info("Controlador instanciado:LoginCtlr()");
	}

	@GetMapping("/login")
	String login() {
		bitacora.info("login()");
		return "seguridad/login";
	}
	
	@GetMapping("/logout")
	String logout() {
		bitacora.info("logout()");
		return "seguridad/logout";
	}

}