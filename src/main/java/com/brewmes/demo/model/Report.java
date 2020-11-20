package com.brewmes.demo.model;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
            batch.setMinimums();
            batch.setMaxes();
            batch.setAverages();
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
            table.addCell(String.valueOf(batch.getTotalProducts()));
            table.addCell(String.valueOf(batch.getTotalProducts()));
            table.addCell(String.valueOf(batch.getAcceptableProducts()));
            table.addCell(String.valueOf(batch.getDefectProducts()));
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
    private Report() {

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
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("humidity");
        long totalTime = 0L;
        if (currentBatch.getHumidity().keySet().stream().findFirst().isPresent()) {
            LocalDateTime startTime;
            startTime = currentBatch.getHumidity().keySet().stream().findFirst().get();

            for (LocalDateTime time : currentBatch.getHumidity().keySet()) {
                long timeElapsed = Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX));
                if (totalTime < timeElapsed) {
                    totalTime = timeElapsed;
                }
                series.add((Number) Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX)), currentBatch.getHumidity().get(time));
            }
        }
        dataset.addSeries(series);
        JFreeChart lineChart = ChartFactory.createXYLineChart("Humidity", "Time", "Humidity"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, humidity, width, height, lineChart, currentBatch.getAvgHumidity(), currentBatch.getMinHumidity(), currentBatch.getMaxHumidity(), totalTime);
    }

    public static void addVibrationSection(Document document) throws DocumentException {
        Paragraph vibration = new Paragraph();
        addEmptyLine(vibration, 1);

        int width = 500;
        int height = 400;

        // Create line chart of vibration over time
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Vibration");
        long totalTime = 0L;
		if (currentBatch.getVibration().keySet().stream().findFirst().isPresent()) {
			LocalDateTime startTime;
			startTime = currentBatch.getVibration().keySet().stream().findFirst().get();
			for (LocalDateTime time : currentBatch.getVibration().keySet()) {
			    long timeElapsed = Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX));
                if (totalTime < timeElapsed) {
                    totalTime = timeElapsed;
                }

				series.add((Number)(Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX))), currentBatch.getVibration().get(time));
			}
		}
		dataset.addSeries(series);
        JFreeChart lineChart = ChartFactory.createXYLineChart("Vibration", "Time", "Vibration"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, vibration, width, height, lineChart, currentBatch.getAvgVibration(), currentBatch.getMinVibration(), currentBatch.getMaxVibration(), totalTime);
    }

    private static void makeTables(Document document, Paragraph paragraph, int width, int height, JFreeChart lineChart, double average, double min, double max, long totalTime) throws DocumentException {

        NumberAxis domain = (NumberAxis) lineChart.getXYPlot().getDomainAxis();
        NumberAxis range = (NumberAxis) lineChart.getXYPlot().getRangeAxis();
        domain.setTickUnit(new NumberTickUnit(totalTime/10));
        domain.setRange(0, totalTime);
        range.setRange(0, max);
        range.setTickUnit(new NumberTickUnit(max/2));

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
        table.addCell(String.valueOf(min));
        table.addCell(String.valueOf(max));
        table.addCell(String.valueOf(average));
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

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Temperature");
        long totalTime = 0;
		if (currentBatch.getTemperature().keySet().stream().findFirst().isPresent()) {
			LocalDateTime startTime;
			startTime = currentBatch.getTemperature().keySet().stream().findFirst().get();
			for (LocalDateTime time : currentBatch.getTemperature().keySet()) {
                long timeElapsed = Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX));
                if (totalTime < timeElapsed) {
                    totalTime = timeElapsed;
                }
				series.add((Number) Math.abs(startTime.toEpochSecond(ZoneOffset.MAX) - time.toEpochSecond(ZoneOffset.MAX)), currentBatch.getTemperature().get(time));
			}
		}
		dataset.addSeries(series);
        JFreeChart lineChart = ChartFactory.createXYLineChart("Temperature", "Time", "Temperature"
                , dataset, PlotOrientation.VERTICAL, true, true, false);
        makeTables(document, temperature, width, height, lineChart, currentBatch.getAvgTemp(), currentBatch.getMinTemp(), currentBatch.getMaxTemp(), totalTime);
    }

    public static void addTimeSection(Document document) throws DocumentException {
        Paragraph timeState = new Paragraph();
        addEmptyLine(timeState, 1);

        int width = 500;
        int height = 400;

        // Create the bar chart for time in states
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (currentBatch.getTimeInStates().keySet().stream().findFirst().isPresent()) {

            for (int state : currentBatch.getTimeInStates().keySet()) {
                dataset.addValue(currentBatch.getTimeInStates().get(state), "states",
                        String.valueOf(state));
            }
        }


        JFreeChart chart = ChartFactory.createBarChart("Time in states", "State",
                "Time", dataset, PlotOrientation.VERTICAL, false, true, false);

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