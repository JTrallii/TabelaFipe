package spring.Java.treinamento.TabelaFipe.principal;

import spring.Java.treinamento.TabelaFipe.model.Dados;
import spring.Java.treinamento.TabelaFipe.model.Modelos;
import spring.Java.treinamento.TabelaFipe.model.Veiculos;
import spring.Java.treinamento.TabelaFipe.service.ConsumoAPI;
import spring.Java.treinamento.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
                ***  OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar !
                """;

        System.out.println(menu);

        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumoAPI.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consultar:");

        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoAPI.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);
        //Aqui criamos uma lista com nome e codigo


        System.out.println("\nModelos dessa marca: ");
        System.out.println(modeloLista);

        //O modeloLista é representado por uma lista de modelos onde tem nome e codigo
        //Quando chamamos o modeloLista.modelos()
        //Estamos chamando o json
        // {"modelos": [{ "codigo": 437, "nome": "Gol bola" }]
        //Então o modeloLista chama a lista contendo o codigo e nome de cada modelo
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados !");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores de avaliações.");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        //Aqui eu chamo a API passando o link da API correspondente ao carro
        json = consumoAPI.obterDados(endereco);
        List<Dados> anos = conversor.obterLista( json, Dados.class);

        List<Veiculos> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            //Aqui eu chamo a API passando o link da API correspondente ao carro
            json = consumoAPI.obterDados(enderecoAnos);

            //Aqui eu converto os dados obtidos da API em um record Veiculos contendo tudo sobre o veiculo
            Veiculos veiculo = conversor.obterDados(json, Veiculos.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}





















































































