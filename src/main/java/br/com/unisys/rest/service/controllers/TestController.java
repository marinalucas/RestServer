package br.com.unisys.rest.service.controllers;

import br.com.unisys.rest.service.entities.Pessoa;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private Map<String, Pessoa> dbPessoaMap = new HashMap<>();

    @GetMapping(value = "/loaddb")
    public void loadDb () {

        final Pessoa pedro = new Pessoa();
        final Pessoa marina = new Pessoa();

        pedro.setNome("Pedro");
        pedro.setSobrenome("Sovenhi");
        pedro.setIdade(33);

        marina.setNome("Marina");
        marina.setSobrenome("Lucas");
        marina.setIdade(43);

        dbPessoaMap.put(pedro.getNome(), pedro);
        dbPessoaMap.put(marina.getNome(), marina);

    }

    @RequestMapping(value = "/{nome}", method = RequestMethod.GET)
    public String test(@PathVariable("nome") final String nome) {

        return "Olá " + nome + "! Tudo bem?";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String test() {
        return "Olá estranho. Tudo bem?";

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Pessoa printPost(@RequestBody final Pessoa pessoa) {

        Pessoa p = new Pessoa();

        p.setNome(pessoa.getNome());
        p.setSobrenome(pessoa.getSobrenome());
        p.setIdade(pessoa.getIdade());

        return p;
    }

    @GetMapping(value = "/{nome}/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa getPessoaByName (@PathVariable("nome") final String nome) {

        return dbPessoaMap.entrySet().stream()
                .filter(p -> p.getKey().equalsIgnoreCase(nome))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }
}
