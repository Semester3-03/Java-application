package com.BrewMES.demo.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class Report{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font textFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public static void generatePDF(Batch batch){
		try {
			String destination = "test.pdf";
			File file = new File(destination);
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addTitlePage(document);

			PdfPTable table = new PdfPTable(5);
			Paragraph space = new Paragraph();

			// Create the table content
			table.addCell("Beer type");
			table.addCell("Amount to produce");
			table.addCell("Total produced");
			table.addCell("Acceptable products");
			table.addCell("Defect products");
			table.addCell(batch.getProductType());
			table.addCell("?");
			table.addCell(batch.getTotalProducts() +"");
			table.addCell(batch.getAcceptableProducts() + "");
			table.addCell(batch.getDefectProducts() + "");
			// Add table to document
			document.add(table);

			// Add Humidity as header
			addHumiditySection(document);
			// Create the table for Humidity
			PdfPTable table2 = new PdfPTable(3);
			table2.addCell("Minimum");
			table2.addCell("Maximum");
			table2.addCell("Average");
			table2.addCell("?");
			table2.addCell("?");
			table2.addCell("?");
			// Add table to document
			document.add(table2);
			addEmptyLine(space,3);
			// Add graph for humidity


			// Add Vibration as header
			addVibrationSection(document);
			// Create the table for vibration
			PdfPTable table3 = new PdfPTable(3);
			table3.addCell("Minimum");
			table3.addCell("Maximum");
			table3.addCell("Average");
			table3.addCell("?");
			table3.addCell("?");
			table3.addCell("?");
			// Add table to document
			document.add(table3);
			// Add graph for vibration

			// Add Temperature as header
			addTemperatureSection(document);
			// Create the table for temperature
			PdfPTable table4 = new PdfPTable(3);
			table4.addCell("Minimum");
			table4.addCell("Maximum");
			table4.addCell("Average");
			table4.addCell("?");
			table4.addCell("?");
			table4.addCell("?");
			// Add table to document
			document.add(table4);
			// Add graph for temperature

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void addTitlePage (Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		// Write the title
		preface.add(new Paragraph("Batch Report", titleFont));
		addEmptyLine(preface, 1);

		// Create a timestamp for report created
		preface.add(new Paragraph("The report is generated at: "+ new Date(), textFontBold));
		addEmptyLine(preface, 2);

		// Add preface to document
		document.add(preface);
	}

	public static void addHumiditySection(Document document) throws DocumentException {
		Paragraph humidity = new Paragraph();
		humidity.add(new Paragraph("Humidity", subFont));
		addEmptyLine(humidity, 1);
		document.add(humidity);
	}

	public static void addVibrationSection(Document document) throws DocumentException {
		Paragraph vibration = new Paragraph();
		vibration.add(new Paragraph("Vibration", subFont));
		addEmptyLine(vibration, 1);
		document.add(vibration);
	}

	public static void addTemperatureSection(Document document) throws DocumentException {
		Paragraph temperature = new Paragraph();
		temperature.add(new Paragraph("Temperature", subFont));
		addEmptyLine(temperature, 1);
		document.add(temperature);
	}
	
	// Add line space in text
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++){
			paragraph.add(new Paragraph("\n"));
		}
	}
}
