package com.ricardo.indraprojeto.repository;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.indraprojeto.model.Fornecedor;
import com.ricardo.indraprojeto.model.Produto;

public interface FornecedorRepository extends CrudRepository<Fornecedor, String>{
	Iterable<Fornecedor> findByProduto(Produto produto);
}
