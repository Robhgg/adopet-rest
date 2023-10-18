package br.com.alura.adopet.api.validacao.interfaces;

import br.com.alura.adopet.api.model.Adocao;

public interface IValidacao <T> {

    void valida(T obj);

}
