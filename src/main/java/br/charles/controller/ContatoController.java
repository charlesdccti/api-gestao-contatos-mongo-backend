package br.charles.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.charles.model.Contato;
import br.charles.model.ContatoCreate;
import br.charles.model.ContatoUpdate;
import br.charles.service.ContatoService;


@CrossOrigin
@RestController
//@RequestMapping(value = "/api/")  // => http://localhost:8080/swagger-ui.html
public class ContatoController {
	
	private static final Logger LOG = null;
	@Autowired
	private ContatoService contatoService;
	private Contato contatoResult;
	private Page<Contato> pageList;
	
	
	
	@GetMapping(value = "/{idContato}")
	public ResponseEntity getContato(@PathVariable(required = true) Integer idContato) {
		this.contatoResult = contatoService.getContato(String.valueOf(idContato));
		return new ResponseEntity<>(this.contatoResult, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/{idContato}")
	public ResponseEntity updateContato(@PathVariable(required = true) Integer idContato, @Valid @RequestBody ContatoUpdate contato) {
		this.contatoResult = contatoService.updateContato(String.valueOf(idContato), contato);
		return new ResponseEntity<>(this.contatoResult, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{idContato}")
	public ResponseEntity deleteContato(@PathVariable(required = true) Integer idContato) {
		
		this.contatoResult	= contatoService.deleteContato(String.valueOf(idContato));
		return new ResponseEntity<>("Contato removido com sucesso!", HttpStatus.NO_CONTENT);  // HttpStatus.NO_CONTENT impede de retornar a string
	}
	
	
	@PostMapping("/")
	public ResponseEntity createContato(@Valid @RequestBody ContatoCreate contato) {
		this.contatoResult = contatoService.createContato(contato);
		return new ResponseEntity<>(this.contatoResult, HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/")
	public ResponseEntity contatosPageable(@RequestParam(required = false) Integer size, @RequestParam(required = false) Integer page, 
		 @PageableDefault(page = 0, size = 10) Pageable pageable) {

		this.pageList = contatoService.listContatosByPage(pageable);
		
		return new ResponseEntity<>( pageList.getContent(), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/all")
	private ResponseEntity findAll() {

		return new ResponseEntity<>(contatoService.listContatos() , HttpStatus.OK);
	}
	
	
}