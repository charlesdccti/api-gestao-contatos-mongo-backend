package br.charles.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.charles.model.Contato;
import br.charles.model.ContatoCreate;
import br.charles.model.ContatoUpdate;

public interface ContatoService {
	
	Contato createContato(ContatoCreate contato);
	Contato updateContato(String id, ContatoUpdate contato);
	Contato deleteContato(String id);
	Contato getContato(String id);
	List<Contato> listContatos();

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Contato> listContatosByPage(Pageable pageable);
	List<Contato> listContatosByPageTeste(Pageable pageable);
	

}