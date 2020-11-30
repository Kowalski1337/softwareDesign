package com.ifmo.vbaydyuk.console;

import com.ifmo.vbaydyuk.drawingApi.AwtDrawingApi;
import com.ifmo.vbaydyuk.drawingApi.DrawingApi;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {

    private static final String LINE = "line";
    private static final String CIRCLE = "circle";
    private static final Pattern LINE_PATTERN = Pattern.compile(LINE + "\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)\\s*");
    private static final Pattern CIRCLE_PATTERN = Pattern.compile(CIRCLE + "\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)\\s*");

    public void run(DrawingApi api) {
        try (Scanner scanner = new Scanner(System.in);
             PrintWriter writer = new PrintWriter(System.out)) {
            while (true) {
                writer.print("> ");
                writer.flush();
                String next = scanner.nextLine();
                if (next.replaceAll("\\s", "").isEmpty()) continue;
                Matcher matcher = LINE_PATTERN.matcher(next);
                if (matcher.matches()) {
                    double x1 = Double.parseDouble(matcher.group(1));
                    double y1 = Double.parseDouble(matcher.group(3));
                    double x2 = Double.parseDouble(matcher.group(5));
                    double y2 = Double.parseDouble(matcher.group(7));
                    api.drawLine(x1, y1, x2, y2);
                    writer.println(String.format("Drawn line from (%f;%f) to (%f;%f)", x1, y1, x2, y2));
                    writer.flush();
                    continue;
                }
                matcher = CIRCLE_PATTERN.matcher(next);
                if (matcher.matches()) {
                    double centerX = Double.parseDouble(matcher.group(1));
                    double centerY = Double.parseDouble(matcher.group(3));
                    double radius = Double.parseDouble(matcher.group(5));
                    api.drawCircle(centerX, centerY, radius);
                    writer.println(String.format("Drawn circle with (%f;%f) center and %f radius", centerX, centerY, radius));
                    writer.flush();
                    continue;
                }
                if (next.contains("exit")) {
                    writer.println("Bye bye");
                    writer.flush();
                    api.close();
                    break;
                }
                writer.println("Try one of those:\n'line x1 y1 x2 y2'\n'circle centerX centerY radius'\nOr print 'exit' to exit");
                writer.flush();
            }
        } catch (Exception e) {
            System.out.println("Cannot close api");
        }
    }

    public static void main(String[] args) {
        new Console().run(new AwtDrawingApi(600, 600, false));
    }

}
