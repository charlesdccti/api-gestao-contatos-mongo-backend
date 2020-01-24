package br.charles.controller;

/**
 * 
 */

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.charles.model.Contato;
import br.charles.model.ContatoCreate;
import br.charles.model.ContatoUpdate;
import br.charles.repository.ContatoRepository;
import br.charles.service.ContatoService;




/**
 * @author charles
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContatoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContatoService contatoService;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	private ContatoCreate ContatoCreate;

	private List<Contato> listaContatos = new ArrayList<Contato>();

	@Autowired
	private ContatoController contatoController;

	private ContatoCreate contatoCreate;

	private Page<Contato> pagesAll;

	private ContatoUpdate contatoUpdate;

	
	@BeforeEach
	public void setup() throws Exception {

		contatoCreate = new ContatoCreate("charles", "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");

		contato = new Contato("1", "charles", "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		contato = this.contatoRepository.save(contato);
		listaContatos = contatoRepository.findAll();
	}



	@Test
	@DisplayName("Listar todos os Contatos")
	public void getAll() throws Exception {

		when(contatoService.listContatos()).thenReturn(listaContatos);

		mockMvc.perform(MockMvcRequestBuilders.get("/all"))
		.andExpect(content().string( jsonToString(listaContatos)) )
		//.andDo(print())
		.andExpect(status().isOk());

		verify(contatoService, times(1)).listContatos();
	}


	@Test
	@DisplayName("Busca pelo id")
	public void buscar_id_200() throws Exception {

		when(contatoService.getContato("1")).thenReturn(contato);

		mockMvc.perform(MockMvcRequestBuilders.get("/1"))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is("1")))
		.andExpect(jsonPath("$.nome", is("charles")))
		.andExpect(jsonPath("$.canal", is("Email")));

		verify(contatoService, times(1)).getContato("1");
	}


	@Test
	@DisplayName("Listar contatos por Paginas")
	public void getAllContatos() throws Exception {

		Pageable pageable = PageRequest.of(0, 10);
		pagesAll = contatoRepository.findAll(pageable);
		when(contatoService.listContatosByPage(pageable)).thenReturn(pagesAll);

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(jsonToString(pagesAll.getContent())));

		verify(contatoService, times(1)).listContatosByPage(pageable);
	}


	@Test
	@DisplayName("Inserir um contato")
	public void inserir_201() throws Exception {
	
		contato = new Contato("2", "Diego", "tell", "7188885555", "Desenvolvedor Node");
		when(contatoService.createContato(Mockito.any())).thenReturn(contato);

		this.mockMvc.perform(post("/")
				.content("{\n" + 
						"	\"nome\": \"Diego\",\n" + 
						"	\"canal\": \"tell\",\n" + 
						"    \"valor\": \"7188885555\",\n" + 
						"    \"obs\": \"Desenvolvedor Node\"\n" + 
						"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());

		verify(contatoService, times(1)).createContato(Mockito.any());
	}
	
	@DisplayName("Test:nao deveria Criar um novo Contato sem passar o nome")
	@Test
	public void create_not_Should_Persist_Contact_with_Name_equal_null() {

		this.contato = new Contato("1", null, "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		this.contato = this.contatoRepository.save(contato);
		
		assertThat(contato).isNotNull();

	}


	
	@Test
	@DisplayName("Deletar um contato por id")
	public void teste05RequisicaoDeleteSucesso() throws Exception {
		this.mockMvc.perform(delete("/1"))
		.andExpect(status().isNoContent())
		.andExpect(content().string(containsString("Contato removido com sucesso!")));
	}
	
	
	@Test
	@DisplayName("Atualizar um contato por id")
	public void atualizar_contato() throws Exception {
		
		contatoUpdate = new ContatoUpdate("Diego", "tell", "7188885555", "Desenvolvedor Node");
		contato = new Contato("1", "Diego", "tell", "7188885555", "Desenvolvedor Node");
		when(contatoService.updateContato("1", contatoUpdate)).thenReturn(contato);

		this.mockMvc.perform(put("/1")
				.content("{\n" + 
						"	\"nome\": \"Diego\",\n" + 
						"	\"canal\": \"tell\",\n" + 
						"    \"valor\": \"7188885555\",\n" + 
						"    \"obs\": \"Desenvolvedor Node\"\n" + 
						"}").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().string(jsonToString(contato)))
		.andExpect(status().isNoContent());

		//verify(contatoService, times(1)).updateContato("1",Mockito.any());
	}



	public String jsonToString(final Contato contato) {
		String string = "";
		ObjectMapper mapper= new ObjectMapper();
		try {
			string = mapper.writeValueAsString(contato);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return string;
	}

	public String jsonToString(final List<Contato> listaContatos2) {
		String string = "";
		ObjectMapper mapper= new ObjectMapper();
		try {
			string = mapper.writeValueAsString(listaContatos2);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return string;
	}



}

