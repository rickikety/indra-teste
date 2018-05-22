package com.ricardo.indraprojeto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricardo.indraprojeto.model.Fornecedor;
import com.ricardo.indraprojeto.model.Produto;
import com.ricardo.indraprojeto.repository.FornecedorRepository;
import com.ricardo.indraprojeto.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private FornecedorRepository fr;
	
	@RequestMapping(value="/cadastrarProduto", method=RequestMethod.GET)
	public String form() {
		return "formProduto";
	}
	
	@RequestMapping(value="/cadastrarProduto", method=RequestMethod.POST)
	public String form(@Valid Produto produto, BindingResult result, RedirectAttributes atributo) {
		if(result.hasErrors()) {
			atributo.addFlashAttribute("mensagemErro", "Todos os campos devem ser preenchidos");
			return "redirect:/cadastrarProduto";
		}
		
		pr.save(produto);
		atributo.addFlashAttribute("mensagem", "Produto inserido com sucesso!");
		return "redirect:/cadastrarProduto";
	}
	
	
	@RequestMapping("/produtos")
	public ModelAndView ListaProdutos() {
		ModelAndView modelAndView = new ModelAndView("index");
		Iterable<Produto> produtos = pr.findAll();
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesProduto(@PathVariable("codigo") long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		ModelAndView modelAndView = new ModelAndView("detalhesProduto");
		modelAndView.addObject("produto", produto);
		
		Iterable<Fornecedor> fornecedores = fr.findByProduto(produto);
		modelAndView.addObject("fornecedores", fornecedores);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/deletar")
	public String deletarProduto(long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		Iterable<Fornecedor> fornecedores = fr.findByProduto(produto);
		
		if(fornecedores != null) {
			fr.delete(fornecedores);
		}
		
		pr.delete(produto);
		
		return "redirect:/produtos";
	}
	
	@RequestMapping("/editar")
	public ModelAndView atualizarProduto(long codigo) {
		ModelAndView modelAndView = new ModelAndView("atualizarProduto");
		Produto produto = pr.findByCodigo(codigo);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	@RequestMapping(value="/editar", method = RequestMethod.POST)
	public String atualizar(@Valid Produto produto, BindingResult result, RedirectAttributes attribute) {
		if(result.hasErrors()) {
			attribute.addFlashAttribute("mensagemErro", "Todos os campos devem ser preenchidos");
			return "redirect:/cadastrarProduto";
		}
		pr.save(produto);
		attribute.addFlashAttribute("mensagem", "Produto inserido com sucesso!");
		return "redirect:/produtos";
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalheProduto(@PathVariable("codigo") long codigo, @Valid Fornecedor fornecedor,BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			if(fornecedor.getNome().isEmpty() && fornecedor.getTelefone().isEmpty()) {
				attributes.addFlashAttribute("mensagemErro", "Verifique os campos!");
			}
			else if(fornecedor.getNome().isEmpty() && !fornecedor.getTelefone().isEmpty()) {
				attributes.addFlashAttribute("mensagemErro", "campo NOME vazio!");
			}
			else if(!fornecedor.getNome().isEmpty() && fornecedor.getTelefone().isEmpty()) {
				attributes.addFlashAttribute("mensagemErro", "campo TELEFONE vazio!");
			}
			return "redirect:/{codigo}";
		}
		
		Produto produto = pr.findByCodigo(codigo);
		fornecedor.setProduto(produto);
		fr.save(fornecedor);
		attributes.addFlashAttribute("mensagem", "Adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/sair")
	public String sair() {
		return "redirect:/logout";
	}
}
