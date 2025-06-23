package util.html;

import java.io.FileOutputStream;

import org.xhtmlrenderer.pdf.ITextRenderer;

public class PdfExporter {

	public static void exportarComoPdf(String htmlFormatado, String nomeArquivoCompletoComCaminho) throws Exception {
		ITextRenderer renderer = new ITextRenderer();
		
		if (htmlFormatado == null || htmlFormatado.trim().isEmpty() || !htmlFormatado.toLowerCase().contains("<html")) {
			throw new IllegalArgumentException("Conteúdo HTML inválido ou vazio. Verifique o conteúdo do editor.");
		}
		
		renderer.setDocumentFromString(htmlFormatado);
		renderer.layout();
		
		try (FileOutputStream os = new FileOutputStream(nomeArquivoCompletoComCaminho)) {
			renderer.createPDF(os);
		}
	}
}