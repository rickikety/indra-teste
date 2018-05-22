package com.ricardo.indraprojeto.repository;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.indraprojeto.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, String>{
	Produto findByCodigo(long id);
}
