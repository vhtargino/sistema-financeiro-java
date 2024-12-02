package fin.service.api.infra.exception;

import lombok.Data;

@Data

public class ErrorResponse {
    String campo;
    String mensagem;

    private ErrorResponse(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public static ErrorResponse mensagemErroCategoria() {
        ErrorResponse errorResponse = new ErrorResponse("idCategoria", "categoria não existe");
        return errorResponse;
    }

    public static ErrorResponse mensagemErroPessoa() {
        ErrorResponse errorResponse = new ErrorResponse("idPessoa", "pessoa não existe");
        return errorResponse;
    }
}
