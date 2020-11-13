package com.BrewMES.demo.model;

import com.itextpdf.text.*;

import java.io.File;
import java.util.Date;

public class Report {
	// public static String pdf = "c:/Reports/BatchReport.pdf";
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font textFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public static File generatePDF(Batch batch){
		return null;
	}

	private static void addPage (Document document) throws DocumentException {
		Paragraph page = new Paragraph();
		// Write the title
		page.add(new Paragraph(Element.ALIGN_CENTER, "Batch Report", titleFont));

		// Create a timestamp for report created
		page.add(new Paragraph(Element.ALIGN_CENTER,"The report is generated at: " + new Date(), textFontBold));

		// Add 3 line spaces
		addEmptyLine(page, 3);
		// Add information text
		page.add(new Paragraph("",textFont));
		// Add 3 line spaces after paragraph
		addEmptyLine(page, 3);

		// Create table


		// Add preface to document
		document.add(page);
	}

	// private static void createtable() {}

	// Add line space in text
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++){
			paragraph.add(new Paragraph(""));
		}
	}
}
