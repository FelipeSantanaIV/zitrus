package br.com.zitrus.enums;

public enum PermissaoEnum {
    PERMITIDO("SIM", "Permitido"),
    NAO_PERMITIDO("NÃO", "Não Permitido");

    private final String valor;
    private final String descricao;

    PermissaoEnum(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PermissaoEnum fromValue(String valor) {
        for (PermissaoEnum permissao : PermissaoEnum.values()) {
            if (permissao.valor.equals(valor)) {
                return permissao;
            }
        }
        throw new IllegalArgumentException("Permissão inválida: " + valor);
    }
}
