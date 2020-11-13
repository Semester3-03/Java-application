package com.BrewMES.demo.model;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class Report{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font textFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static PdfWriter pdfWriter;

	public static void generatePDF(Batch batch){
		try {
			String destination = "test.pdf";
			File file = new File(destination);
			Document document = new Document();
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addTitlePage(document);

			PdfPTable table = new PdfPTable(5);

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
			// addEmptyLine(document, 1);

			// Add bar chart over time in states
			addTimeSection(document);

			// Add Humidity graph and table
			addHumiditySection(document);

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
		addEmptyLine(humidity, 1);

		int width = 500;
		int height = 400;

		// Add line chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(1.0,"humidity", "1.3");
		dataset.addValue(1.7,"humidity", "2.9");
		dataset.addValue(3,"humidity", "3.4");
		dataset.addValue(0.3,"humidity", "4.2");
		dataset.addValue(2.4,"humidity", "5.0");
		JFreeChart lineChart = ChartFactory.createLineChart("Humidity", "Time","Humidity"
				,dataset,PlotOrientation.VERTICAL, true,true,false);
		CategoryPlot plot = lineChart.getCategoryPlot();
		CategoryAxis xAis = lineChart.getCategoryPlot().getDomainAxis();
		xAis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		PdfContentByte contentByte = pdfWriter.getDirectContent();
		PdfTemplate template = contentByte.createTemplate(width, height);
		Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
		Rectangle2D rectangle2d = new Rectangle2D.Double(0 ,0,width, height);

		lineChart.draw(graphics2d, rectangle2d);
		graphics2d.dispose();
		Image chartImage = Image.getInstance(template);
		humidity.add(chartImage);

		// Create the table for Humidity
		PdfPTable table = new PdfPTable(3);
		table.addCell("Minimum");
		table.addCell("Maximum");
		table.addCell("Average");
		table.addCell("?");
		table.addCell("?");
		table.addCell("?");
		// Add table to document underneath the graph

		document.add(table);

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

	public static void addTimeSection(Document document) throws DocumentException {
		Paragraph timeState = new Paragraph();
		addEmptyLine(timeState,1);

		int width = 500;
		int height = 400;

		// Create the method for bar chart over time in states
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(2,"state","Deactivated");
		dataset.setValue(1,"state","Clearing");
		dataset.setValue(1,"state","Stopped");
		dataset.setValue(4,"state","Starting");
		dataset.setValue(0,"state","Idle");
		dataset.setValue(0,"state","Suspended");
		dataset.setValue(7,"state","Execute");
		dataset.setValue(10,"state","Stopping");
		dataset.setValue(2,"state","Aborting");
		dataset.setValue(2,"state","Aborted");
		dataset.setValue(2,"state","Holding");
		dataset.setValue(5,"state","Resetting");
		dataset.setValue(2,"state","Completing");
		dataset.setValue(2,"state","Complete");
		dataset.setValue(2,"state","Deactivating");
		dataset.setValue(8,"state","Activating");
		JFreeChart chart = ChartFactory.createBarChart("Time in states", "State",
					"Time", dataset, PlotOrientation.VERTICAL, false, true, false);
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis xAis = chart.getCategoryPlot().getDomainAxis();
		xAis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);


		PdfContentByte contentByte = pdfWriter.getDirectContent();
		PdfTemplate template = contentByte.createTemplate(width, height);
		Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
		Rectangle2D rectangle2d = new Rectangle2D.Double(0 ,0,width, height);

		chart.draw(graphics2d, rectangle2d);
		graphics2d.dispose();
		Image chartImage = Image.getInstance(template);
		timeState.add(chartImage);

		document.add(timeState);
	}



	// Add line space in text
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++){
			paragraph.add(new Paragraph("\n"));
		}
	}

	// Add line space in document
	private static void addEmptyLine(Document document, int number) {
		for (int i = 0; i < number; i++){
			try {
				document.add(new Paragraph("\n"));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
}
