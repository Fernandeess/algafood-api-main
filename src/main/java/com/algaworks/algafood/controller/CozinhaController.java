package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontrada;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.CozinhaXmlWrapper;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CozinhaService cozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhaXmlWrapper listarXML() {
		return new CozinhaXmlWrapper(cozinhaRepository.listar());
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Cozinha cozinha = cozinhaRepository.buscar((Long) id);
		return ResponseEntity.ok(cozinha);

		// HttpHeaders headers = new HttpHeaders();
		// headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		// //Redirecionamento

		// return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

	}

	@PostMapping
	public ResponseEntity<Cozinha> Adicionar(@RequestBody Cozinha cozinhaBody) {
		Cozinha cozinha = cozinhaService.salvar(cozinhaBody);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinhaBody) {
		Cozinha cozinhaEntity = cozinhaRepository.buscar(id);
		BeanUtils.copyProperties(cozinhaBody, cozinhaEntity, "id"); // Copiar propriedades de umas instancia para a
																	// outra, terceiro parametro serve para definir o
																	// valor que sera ignorado

		cozinhaEntity = cozinhaService.salvar(cozinhaEntity);

		return ResponseEntity.status(HttpStatus.OK).body(cozinhaEntity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
		try {

			cozinhaService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
