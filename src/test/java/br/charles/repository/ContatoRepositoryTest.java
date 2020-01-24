package br.charles.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.charles.model.Contato;

@SpringBootTest
class ContatoRepositoryTest {

	private Contato contato;
		
	@Autowired
	private ContatoRepository contatoRepository;

	
	
    @BeforeAll
    public static void init(){
        System.out.println("Before All init() method called");
    }
	
    
	@DisplayName("Test hello Junit 5")
	@Test
	void testGet() {
		assertThat("Hello JUnit 5");
	}

	
	@DisplayName("Test:Cria um novo objeto do tipo Contato")
	@Test
	public void create_Should_PersistData() {

		this.contato = new Contato("1", "charles", "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		this.contato = this.contatoRepository.save(contato);
		assertThat(contato.getId()).isNotNull();
		assertThat(contato.getNome()).isEqualTo("charles");
		assertThat(contato.getCanal()).isEqualTo("Email");
		assertThat(contato.getValor()).isEqualTo("charlesdccti@gmail.com");
		assertThat(contato.getObs()).isEqualTo("Desenvolvedor Java");
	}

	@DisplayName("Test:nao deveria Criar um novo Contato sem passar o nome")
	@Test
	public void create_not_Should_Persist_Contact_with_Name_equal_null() {

		this.contato = new Contato("1", null, "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		this.contato = this.contatoRepository.save(contato);
		
		assertThat(contato).isNotNull();

	}


	
	
	@DisplayName("Test:Apaga um objeto do tipo Contato")
	@Test
	public void delete_Should_RemoveData() {
		this.contato = new Contato("1", "charles", "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		this.contato = this.contatoRepository.save(contato);
		this.contatoRepository.delete(contato);
		assertThat( contatoRepository.findById(contato.getId()) ).isEmpty();		
	}
	
	
	@DisplayName("Test: Altera um objeto do tipo Contato")
	@Test
	public void update_Should_ChangeAndPersistData() {
		this.contato = new Contato("1", "charles", "Email", "charlesdccti@gmail.com", "Desenvolvedor Java");
		this.contato = this.contatoRepository.save(contato);
		contato.setNome("Charles222");
		contato.setCanal("Telefone");
		this.contatoRepository.save(contato);
		contato = contatoRepository.findById(contato.getId()).get();
		assertThat(contato.getNome()).isEqualTo("Charles222");
		assertThat(contato.getCanal()).isEqualTo("Telefone");
	}

}
