/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.learning.school.entity.*;
import javafx.collections.ObservableList;

/**
 *
 * @author hromov
 */
public class PDFProvider {

    public static void genPDF(String query, ObservableList<ClassAvgGrade> ol1, ObservableList<ClassAvgStud> ol2,
                       ObservableList<ClassNameAvg> ol3, ObservableList<SubjTeacher> ol4,
                       ObservableList<StudAvgPerf> ol5, ObservableList<TeacherCountAttest> ol6) {

        PdfWriter writer = null;
        try {
            var cur_time = LocalTime.now().getHour() + "-" + LocalTime.now().getMinute() + "-" + LocalTime.now().getSecond();
            File myObj = new File("C:\\Users\\hromov\\Desktop\\" + query.toUpperCase()+"_"+cur_time+".pdf");
            myObj.createNewFile();
            writer = new PdfWriter(myObj.getPath());
            // Creating a PdfDocument object
            PdfDocument pdf = new PdfDocument(writer);
            // Creating a Document object
            Document doc = new Document(pdf);
            // Creating a table
            Table table = null;
            switch (query) {
                case "Q9" -> {
                    Paragraph title = new Paragraph("Average degree among classes on specific subject");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Class");
                    table.addCell("Average");
                    for (var i : ol1) {
                        table.addCell(i.getNumber());
                        table.addCell(i.getAvg().toString());
                    }
                }
                case "Q10" -> {
                    Paragraph title = new Paragraph("Amount of students among classes");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Class");
                    table.addCell("Amount");
                    for (var i : ol2) {
                        table.addCell(i.getNumber());
                        table.addCell(i.getCount().toString());
                    }
                }
                case "Q13" -> {
                    Paragraph title = new Paragraph("The best student for each class");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Class");
                    table.addCell("Name");
                    table.addCell("Average");
                    for (var i : ol3) {
                        table.addCell(i.getNumber());
                        table.addCell(i.getName());
                        table.addCell(i.getAvg().toString());
                    }
                }
                case "Q14" -> {
                    Paragraph title = new Paragraph("The most experienced teacher for each speciality");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Subject");
                    table.addCell("Name");
                    table.addCell("Category");
                    for (var i : ol4) {
                        table.addCell(i.getSubject());
                        table.addCell(i.getName());
                        table.addCell(i.getMax().toString());
                    }
                }
                case "Q17" -> {
                    Paragraph title = new Paragraph("Distribute students of the specific class on performance");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Name");
                    table.addCell("Average");
                    table.addCell("Performance");
                    for (var i : ol5) {
                        table.addCell(i.getName());
                        table.addCell(i.getAvg().toString());
                        table.addCell(i.getPerf());
                    }
                }
                case "Q18" -> {
                    Paragraph title = new Paragraph("Distribute teachers on attestation category amount");
                    doc.add(title);
                    float[] pointColumnWidths = {150F, 150F, 150F};
                    table = new Table(pointColumnWidths);
                    table.addCell("Name");
                    table.addCell("Amount");
                    table.addCell("Experience");
                    for (var i : ol6) {
                        table.addCell(i.getName());
                        table.addCell(i.getCount().toString());
                        table.addCell(i.getExp());
                    }
                }
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFProvider.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(PDFProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
