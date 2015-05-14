package la.clamor.effector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Tabula {
    enum Type{COMBO, SCATTER}
    String nomen;
    File lima;
    String x_unit;
    String[] y_units;
    Collocatio[] collocationes;
    ArrayList<ArrayList<Double>> aestimationes;
    ArrayList<Double> axes;
    Type type;
    public static Tabula combo(String nomen, File lima, String x_unit, String[] y_units, Collocatio... collocationes){
        return new Tabula(Type.COMBO, nomen, lima, x_unit, y_units, collocationes);
    }
    public static Tabula scatter(String nomen, File lima, String x_unit, String y_unit, String collocatio){
        return new Tabula(Type.SCATTER, nomen, lima, x_unit, new String[]{y_unit}, convertCollocationes(new String[]{collocatio}));
    }
    private static Collocatio[] convertCollocationes(String[] collocationes){
        Collocatio[] c = new Collocatio[collocationes.length];
        for(int i = 0;i < collocationes.length;i++){
            c[i] = new Collocatio(collocationes[i], true);
        }
        return c;
    }
    private Tabula(Type type, String nomen, File lima, String x_unit, String[] y_units, Collocatio... collocationes){
        this.type = type;
        this.nomen = nomen;
        this.lima = lima;
        this.x_unit = x_unit;
        this.y_units = y_units;
        this.collocationes = collocationes;
        axes = new ArrayList<>();
        aestimationes = new ArrayList<>();
        for (Collocatio collocatio : collocationes) {
            aestimationes.add(new ArrayList<>());
        }
    }
    public void addo(double axis, Double... doubles){
        axes.add(axis);
        for(int i = 0;i < collocationes.length;i++){
            aestimationes.get(i).add(doubles[i]);
        }
    }
    public static class Collocatio {
        private final String unit;
        private final boolean sinistra;
        public Collocatio(String unit, boolean sinistra){
            this.unit = unit;
            this.sinistra = sinistra;
        }
        public String capioUnit(){
            return unit;
        }
        public boolean sinistraEst(){
            return sinistra;
        }
    }

    public void imprimo(){
        //DecimalFormat nf = new DecimalFormat("###############.###############");
        DecimalFormat nf = new DecimalFormat("###############.####");
        PrintWriter out;
        try {
            out = new PrintWriter(lima);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("  <head>");
        out.println("    <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>");
        out.println("    <title>");
        out.println("      " + nomen);
        out.println("    </title>");
        out.println("    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
        out.println("    <script type=\"text/javascript\">");
        out.println("      google.load('visualization', '1', {packages: ['corechart']});");
        out.println("    </script>");
        out.println("    <script type=\"text/javascript\">");
        out.println("      function drawVisualization() {");
        out.println("        var data = google.visualization.arrayToDataTable([");
        
        out.print("          ['" + x_unit + "'");
        for(Collocatio collocatio:collocationes){
            out.print(", '" + collocatio.unit + "'");
        }
        out.println("],");
        
        int longitudo = axes.size();
        for(int i = 0;i < longitudo;i++){
            out.print("          [" + nf.format(axes.get(i)));
            for(ArrayList<Double> aestimatio:aestimationes){
                Double aes = aestimatio.get(i);
                out.print(", " + (aes == null || aes.isNaN()?"null":nf.format(aes)));
            }
            out.println("],");
        }
        out.println("        ]);");
        out.println("");
        out.println("        var options = {");
        out.println("          title : '" + nomen + "',");
        out.println("          vAxes: {");
        for(int i = 0;i < Math.min(y_units.length, 2);i++){
            out.println("                  " + i + ": {title: '" + y_units[i] + "'},");
        }
        out.println("          },");
        out.println("          hAxis: {title: '" + x_unit + "'},");
        //out.println("          seriesType: 'bars',");
        if(type == Type.COMBO){
            out.println("          series:[");
            for (Collocatio collocatio : collocationes) {
                out.println("              {targetAxisIndex:" + (collocatio.sinistraEst() ? "0" : "1") + ",type: 'line'},");
            }
            out.println("            ],");
        }
        out.println("          ");
        out.println("          interpolateNulls:true,");
        out.println("          isStacked: true,");
        out.println("        };");
        out.println("");
        out.println("        var x = document.documentElement.clientWidth  || document.body.clientWidth  || document.body.scrollWidth;");
        out.println("        var y = document.documentElement.clientHeight || document.body.clientHeight || document.body.scrollHeight;");
        out.println("        var style = \"width: \" + x + \"px; height: \" + y  + \"px\";");
        out.println("        div = document.getElementById(\"chart_div\");");
        out.println("        div.setAttribute(\"style\", style);");
        out.println("        var chart = new google.visualization." + 
                (type == Type.COMBO?"ComboChart":"ScatterChart") + "(document.getElementById('chart_div'));");
        out.println("        chart.draw(data, options);");
        out.println("      }");
        out.println("      google.setOnLoadCallback(drawVisualization);");
        out.println("    </script>");
        out.println("  </head>");
        out.println("  <body align='center'>");
        out.println("    <div id=\"chart_div\" style=\"width: 1024px; height: 700px;\"></div>");
        out.println("  </body>");
        out.println("</html>");
        out.flush();
        out.close();
        
    }
}
