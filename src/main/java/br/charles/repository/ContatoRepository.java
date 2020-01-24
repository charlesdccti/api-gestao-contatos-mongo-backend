package br.charles.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.charles.model.Contato;

@Repository
public interface ContatoRepository extends MongoRepository<Contato, String> {

}