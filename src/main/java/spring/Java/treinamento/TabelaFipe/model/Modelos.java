package spring.Java.treinamento.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<Dados> modelos) {
}

//Aqui foi criado uma lista Modelos, cada modelo de carro tem seu codigo e seu nome
//Por isso foi criado esse record para criar uma lista onde cada modelo possui codigo e nome
//Aqui cada Modelo tem seu codigo e nome como um array de objetos
