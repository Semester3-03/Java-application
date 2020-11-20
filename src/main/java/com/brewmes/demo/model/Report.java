package com.brewmes.demo.model;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfPrinterGraphics2D;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Report {
    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font textFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static PdfWriter pdfWriter;
    private static Batch currentBatch;

    public static void generatePDF(Batch batch) {
        try {
            currentBatch = batch;
            String destination = "batch_report.pdf";
            File file = new File(destination);
            Document document = new Document();
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Add title and timestamp
            addTitlePage(document);

            // Create a table with 5 columns
            PdfPTable table = new PdfPTable(5);
            // Create the table content
            table.addCell("Beer type");
            table.addCell("Amount to produce");
            table.addCell("Total produced");
            table.addCell("Acceptable products");
            table.addCell("Defect products");
            table.addCell(batch.getProductType());
            table.addCell("?");
            table.addCell(batch.getTotalProducts() + "");
            table.addCell(batch.getAcceptableProducts() + "");
            table.addCell(batch.getDefectProducts() + "");
            // Add table to document
            document.add(table);

            // Add bar chart over time in states
            addTimeSection(document);
            // Add a new page
            document.newPage();

            // Add Humidity graph and table
            addHumiditySection(document);
            // Add a new page
            document.newPage();

            // Add Vibration graph and table
            addVibrationSection(document);
            // Add a new page
            document.newPage();

            // Add Temperature graph and table
            addTemperatureSection(document);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        // Write the title
        preface.add(new Paragraph("Batch Report", titleFont));
        addEmptyLine(preface, 1);

        // Create a timestamp for report created
        preface.add(new Paragraph("The report is generated at: " + new Date(), textFontBold));
        addEmptyLine(preface, 2);

        // Add preface to document
        document.add(preface);
    }

    public static void addHumiditySection(Document document) throws DocumentException {
        Paragraph humidity = new Paragraph();
        addEmptyLine(humidity, 1);

        int width = 500;
        int height = 400;

        // Create line chart of humidity over time
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (currentBatch.getHumidity().keySet().stream().findFirst().isPresent()) {
            LocalDateTime startTime;
            startTime = currentBatch.getHumidity().keySet().stream().findFirst().get();

            for (LocalDateTime time : currentBatch.getHumidity().keySet()) {
                dataset.addValue(currentBatch.getHumidity().get(time), "humidity",
                        Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX)) + "");
            }
        }

        JFreeChart lineChart = ChartFactory.createLineChart("Humidity", "Time", "Humidity"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, humidity, width, height, lineChart);
    }

    public static void addVibrationSection(Document document) throws DocumentException {
        Paragraph vibration = new Paragraph();
        addEmptyLine(vibration, 1);

        int width = 500;
        int height = 400;

        // Create line chart of vibration over time
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (currentBatch.getVibration().keySet().stream().findFirst().isPresent()) {
			LocalDateTime startTime;
			startTime = currentBatch.getVibration().keySet().stream().findFirst().get();

			for (LocalDateTime time : currentBatch.getVibration().keySet()) {
				dataset.addValue(currentBatch.getVibration().get(time), "vibration",
						Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX)) + "");
			}
		}
        JFreeChart lineChart = ChartFactory.createLineChart("Vibration", "Time", "Vibration"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, vibration, width, height, lineChart);
    }

    private static void makeTables(Document document, Paragraph paragraph, int width, int height, JFreeChart lineChart) throws DocumentException {
        lineChart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        PdfContentByte contentByte = pdfWriter.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

        lineChart.draw(graphics2d, rectangle2d);
        graphics2d.dispose();
        Image chartImage = Image.getInstance(template);
        paragraph.add(chartImage);

        // Create the table for minimum, maximum and average.
        PdfPTable table = new PdfPTable(3);
        table.addCell("Minimum");
        table.addCell("Maximum");
        table.addCell("Average");
        table.addCell("?");
        table.addCell("?");
        table.addCell("?");
        // Add table to document underneath the graph
        addEmptyLine(paragraph, 1);
        paragraph.add(table);

        document.add(paragraph);
    }

    public static void addTemperatureSection(Document document) throws DocumentException {
        Paragraph temperature = new Paragraph();
        addEmptyLine(temperature, 1);

        int width = 500;
        int height = 400;

        // Create line chart of temperature over time
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (currentBatch.getTemperature().keySet().stream().findFirst().isPresent()) {
			LocalDateTime startTime;
			startTime = currentBatch.getTemperature().keySet().stream().findFirst().get();

			for (LocalDateTime time : currentBatch.getTemperature().keySet()) {
				dataset.addValue(currentBatch.getTemperature().get(time), "Temperature",
						Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX)) + "");
			}
		}
        JFreeChart lineChart = ChartFactory.createLineChart("Temperature", "Time", "Temperature"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, temperature, width, height, lineChart);
    }

    public static void addTimeSection(Document document) throws DocumentException {
        Paragraph timeState = new Paragraph();
        addEmptyLine(timeState, 1);

        int width = 500;
        int height = 400;

        // Create the bar chart for time in states
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(2, "state", "Deactivated");
        dataset.setValue(1, "state", "Clearing");
        dataset.setValue(1, "state", "Stopped");
        dataset.setValue(4, "state", "Starting");
        dataset.setValue(0, "state", "Idle");
        dataset.setValue(0, "state", "Suspended");
        dataset.setValue(7, "state", "Execute");
        dataset.setValue(10, "state", "Stopping");
        dataset.setValue(2, "state", "Aborting");
        dataset.setValue(2, "state", "Aborted");
        dataset.setValue(2, "state", "Holding");
        dataset.setValue(5, "state", "Resetting");
        dataset.setValue(2, "state", "Completing");
        dataset.setValue(2, "state", "Complete");
        dataset.setValue(2, "state", "Deactivating");
        dataset.setValue(8, "state", "Activating");
        JFreeChart chart = ChartFactory.createBarChart("Time in states", "State",
                "Time", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAis = chart.getCategoryPlot().getDomainAxis();
        xAis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);


        PdfContentByte contentByte = pdfWriter.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

        chart.draw(graphics2d, rectangle2d);
        graphics2d.dispose();
        Image chartImage = Image.getInstance(template);
        timeState.add(chartImage);

        document.add(timeState);
    }

    // Add line space in text
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph("\n"));
        }
    }
}