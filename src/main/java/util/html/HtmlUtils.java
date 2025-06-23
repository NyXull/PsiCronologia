package util.html;

public class HtmlUtils {
	
	public static String prepararHtmlParaPDF(String htmlDoEditor) {
		String htmlLimpo = htmlDoEditor
				.replaceAll("<o:p.*?>.*?</o:p>", "")
				.replaceAll("xmlns:o=\"[^\"]*\"", "")
				.replaceAll("&nbsp;", " ")
				.replaceAll("<br>", "<br />")
				.replaceAll("<hr>", "<hr />")
				.replaceAll("<meta([^>]*)(?<!/)>", "<meta$1 />")
				.replaceAll("<img([^>]*)(?<!/)>", "<img$1 />")
				.replaceAll("<input([^>]*)(?<!/)>", "<input$1 />")
				.replaceAll("<col([^>]*)>", "<col$1 />")
				.replaceAll("<link([^>]*)>", "<link$1 />")
				.replaceAll("<base([^>]*)>", "<base$1 />")
				.trim();
		
		String htmlFormatado = """
                <!DOCTYPE html>
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                  <meta charset="UTF-8" />
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      font-size: 12pt;
                      color: #000000;
                      line-height: 1.6;
                      margin: 40px;
                    }
                  </style>
                </head>
                <body>
                """ + htmlLimpo + """
                </body>
                </html>
                """;
		
		return htmlFormatado;
	}
}