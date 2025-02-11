package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

import org.springframework.data.repository.CrudRepository.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	/*
	public ResponseEntity<?> listar() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
	}
	*/
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());	
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	/*
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById(codigo).orElse(null);
	}
	*/
	
	/* ----utilizando map
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById(codigo)
				.map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
	*/
	
	/* ---utilizando isPresent */
	@GetMapping("{/codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		Optional categoria = this.categoriaRepository.findById(codigo);
		return categoria.isPresent() ?
				ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}
	
	/* Status code :  
	 * 2XX : sucesso
	 * 4XX : erro do cliente
	 * 5XX : erro do serviço/servidor
	 * */
	
	
}
