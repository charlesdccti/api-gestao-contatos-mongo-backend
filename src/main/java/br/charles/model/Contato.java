package br.charles.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="contato")
public class Contato {
	
	@Id
	private String id;

	@NotNull(message = "O campo 'nome' não pode ser nulo!")
	@NotEmpty(message = "O campo 'nome' não pode ser uma string vazia!")
	@Size(min = 2, message = "O campo 'nome' não pode ser menor que 2 characters")
	private String nome;

	@NotNull(message = "O campo 'canal' não pode ser nulo!")
	@NotEmpty(message = "O campo 'canal' não pode ser uma string vazia!")
	private String canal;

	@NotNull(message = "O campo 'valor' não pode ser nulo!")
	@NotEmpty(message = "O campo 'valor' não pode ser uma string vazia!")
	private String valor;
	
	private String obs;
}