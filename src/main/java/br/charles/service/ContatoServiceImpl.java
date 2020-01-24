package br.charles.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.charles.exception.ContatoNotFoundException;
import br.charles.model.Contato;
import br.charles.model.ContatoCreate;
import br.charles.model.ContatoUpdate;
import br.charles.repository.ContatoRepository;



@Service
public class ContatoServiceImpl implements ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;
	private List<Contato> contatoList;
	private static Integer idMaior = 0;


	@SuppressWarnings("finally")
	private Contato findOne(String Id) {
		Contato instance = null;
		try {
			List<Contato> contatoList = contatoRepository.findAll();
			for (Contato contato : contatoList) {
				if (contato.getId().equals(Id)) {
					instance = contato;
					break;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {

			if(instance != null)
				return instance;
			else{
				throw new ContatoNotFoundException("Contato não encontrado para o id = " + Id);
				//throw new ContatoNotFoundException("Contato não existe no banco de dados!");
			}
		}
	}

	@Override
	public Contato createContato(ContatoCreate contatoDTO) {

		this.contatoList = contatoRepository.findAll();


		for (Contato contato : contatoList) {
			Integer idcontato = Integer.parseInt(contato.getId());
			if ( idcontato > idMaior) {
				idMaior = idcontato;
			}
		}

		idMaior++; 

		Contato contato = new Contato();
		contato.setId(String.valueOf(idMaior));	// Salvar o sucessor do "id de maior valor inteiro"
		contato.setNome(contatoDTO.getNome());
		contato.setCanal(contatoDTO.getCanal());
		contato.setValor(contatoDTO.getValor());
		contato.setObs(contatoDTO.getObs());

		return contatoRepository.save(contato);
	}

	@Override
	public List<Contato> listContatos() {
		return contatoRepository.findAll();
	}


	@Override
	public Contato updateContato(String id, ContatoUpdate contatoDTO) {
		Contato updateInstance = this.findOne(id);
		if(updateInstance != null){
			updateInstance.setNome(contatoDTO.getNome());
			updateInstance.setCanal(contatoDTO.getCanal());
			updateInstance.setValor(contatoDTO.getValor());
			updateInstance.setObs(contatoDTO.getObs());
		}
		return contatoRepository.save(updateInstance);
	}


	@Override
	public Contato deleteContato(String id) {
		Contato instance = findOne(id);
		contatoRepository.delete(instance);
		return instance;
	}


	@Override
	public Contato getContato(String id) {
		return this.findOne(id);
	}

	
	@Override
	public Page<Contato> listContatosByPage(Pageable pageable) {
		return contatoRepository.findAll(pageable);
	}

	@Override
	public List<Contato> listContatosByPageTeste(Pageable pageable) {
		return contatoRepository.findAll(pageable).getContent();
	}

}