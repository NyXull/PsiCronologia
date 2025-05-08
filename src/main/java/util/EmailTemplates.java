package util;

public class EmailTemplates {

	public static String getEmailVerificacao(String nomePsico, String codigoVerificacao) {
		return String.format("""
			<html>
			<body>
			<h2 style='font-weight: bold; font-family: Comfortaa; color: #182052;'>Olá, %s!</h2>
			<p style='font-family: Nunito; font-size: 18px;'>Obrigado por se cadastrar no PsiCronologia.</p>
			<p style='font-family: Nunito; font-size: 18px;'>Este é o seu código de verificação para ativar sua conta:</p>
			<h3 style='font-family: Comfortaa; font-size: 28px; color: #182052;'>%s</h3>			
			<p style='font-family: Nunito; font-size: 18px;'>Se você não se cadastrou, ignore este e-mail.</p>
			</body>
			</html>
			""", nomePsico, codigoVerificacao);
	}
}