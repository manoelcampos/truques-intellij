package io.github.manoelcampos.apirestvendas.model;

import lombok.Data;

/**
 * @author Manoel Campos
 */
@Data
public class PessoaFisica {
    /**
     * TODO Usar plugin ChatGPT EasyCode (é preciso logar no menu lateral direito, tem opção de usar conta padrão)
     * para converter pra enum.
     * Prompt: Converta este vetor para um enum mais simples possível.
     */
    private static final String[] SEXO = {"Masculino", "Feminino"};

    private String sexo;

    /**
     * Usando Hibernate Validation, o código inteiro de validação de CPF pode
     * ser substituído pela anotação {@link org.hibernate.validator.constraints.br.CPF}.
     */
    private String cpf;

    public void setSexo(String sexo){
        if (!SEXO[0].equals(sexo) && !SEXO[1].equals(sexo)) {
            throw new IllegalArgumentException("Sexo inválido");
        }

        this.sexo = sexo;
    }

    /**
     * Calcula um dígito verificador de um CPF ou CNPJ utilizando o algoritmo módulo 11.
     *
     * @param totalAlgarismos número de algarismos a serem usados para calcular um dígito do CPF
     * @return o dígito verificador calculado
     */
    private int calculaDigito(final String cpf, final int totalAlgarismos) {
        int soma = 0;
        int algarismo;
        final var vetor = cpf.toCharArray();
        for (int i = 0; i < totalAlgarismos; i++) {
            //converte de char para int usando o código ASCII do char como base
            algarismo = vetor[i] - 48;
            soma += (totalAlgarismos + 1 - i) * algarismo;
        }

        int dig = 11 - (soma % 11);
        return dig > 9 ? 0 : dig;
    }

    /**
     * Verifica se um CPF é válido ou não, utilizando o algorítmo denominado
     * <a href="https://pt.wikipedia.org/wiki/Dígito_verificador#Módulo_11">Módulo 11</a>.
     *
     * @param cpf CPF a ser validado
     * @return true se o CPF for válido, false caso contrário.
     */
    public boolean isCpfValido(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        int d1 = calculaDigito(cpf, 9);
        int d2 = calculaDigito(cpf, 10);

        // Converte os dígitos calculados de int para String e une (concatena) os dois
        // numa só String.
        String digVerificadorCalculado = String.valueOf(d1) + String.valueOf(d2);
        // Copia os 2 últimos dígitos do CPF informado, para comparar com os dígitos
        // calculados
        String digVerificadorExistente = cpf.substring(cpf.length() - 2);

        // Compara os 2 últimos dígitos do CPF com os 2 calculados.
        // Se forem iguais, o CPF é válido.
        return digVerificadorExistente.equals(digVerificadorCalculado);
    }

    public void setCpf(final String cpf) {
        if (!isCpfValido(cpf))
            throw new IllegalArgumentException("CPF inválido");

        this.cpf = cpf;
    }
}
