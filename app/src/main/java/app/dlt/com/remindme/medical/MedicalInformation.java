package app.dlt.com.remindme.medical;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import app.dlt.com.remindme.R;

public class MedicalInformation extends AppCompatActivity {
    HorizontalScrollView ll;
    /* Parameters */
    TableLayout.LayoutParams tableParams;
    TableRow.LayoutParams rowParams;
    TableRow.LayoutParams itemParams;

    /* GetApplication context */
    Context context;

    /* Table view */
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_information);

        String medication = getIntent().getStringExtra("Type");
        medication = medication.substring(24,medication.length());
        Log.e("Algo", medication);

        TextView tv = (TextView) findViewById(R.id.title);
        tv.setText(getResources().getText(getResources().getIdentifier(medication,"string","app.dlt.com.remindme")));

        ll = (HorizontalScrollView) findViewById(R.id.container);
        createTable();
        addInfo(medication);
    }

    private void createTable(){
        context = getApplicationContext();

        tableParams =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
        rowParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 0, 1f);
        itemParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);

        tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(tableParams);
        tableLayout.setBackgroundColor(Color.parseColor("#000000"));

        TableRow tableRowHeader = new TableRow(context);
        tableRowHeader.setLayoutParams(rowParams);

        TextView textViewDenominacion = new TextView(context);
        textViewDenominacion.setLayoutParams(itemParams);
        textViewDenominacion.setText("DENOMINACIÓN COMÚN INTERNACIONAL");
        textViewDenominacion.setTextColor(Color.parseColor("#ffffff"));
        tableRowHeader.addView(textViewDenominacion);

        TextView textViewConcentracion = new TextView(context);
        textViewConcentracion.setLayoutParams(itemParams);
        textViewConcentracion.setText("CONCENTRACIÓN");
        textViewConcentracion.setTextColor(Color.parseColor("#ffffff"));
        tableRowHeader.addView(textViewConcentracion);

        TextView textViewForm = new TextView(context);
        textViewForm.setLayoutParams(itemParams);
        textViewForm.setText("FORMA FARMACÉUTICA");
        textViewForm.setTextColor(Color.parseColor("#ffffff"));
        tableRowHeader.addView(textViewForm);

        TextView textViewVia = new TextView(context);
        textViewVia.setLayoutParams(itemParams);
        textViewVia.setText("VÍA DE ADMINISTRACIÓN");
        textViewVia.setTextColor(Color.parseColor("#ffffff"));
        tableRowHeader.addView(textViewVia);

        tableLayout.addView(tableRowHeader);
    }

    private void addInfo(String medication){
        Log.e("asdas",medication);

        switch (medication) {
            case "analgesico":
                analgesico();
                break;
            case "antiacidos":
                antiacidos();
                break;
            case "antimicoticos":
                antimicoticos();
                break;
            case "dermatologico":
                dermatologico();
                break;
            case "aceites":
                aceites();
                break;
            case "vitaminas":
                vitaminas();
                break;
            default:
                common(medication);
                break;
        }
    }

    private void analgesico() {
        String data[][] = new String[][]{
                {"Paracetamol (Acetaminofén)", "Hasta 1 g",        "Tabletas/Cápsulas/Polvo",   "Oral"},
                {"Paracetamol (Acetaminofén)", "Hasta 160 mg/5ml", "Jarabe",                    "Oral"},
                {"Paracetamol (Acetaminofén)", "100mg/ml",         "Gotas",                     "Oral"},
                {"Paracetamol (Acetaminofén)", "Hasta 250 mg",     "Supositorios",              "Oral"},
                {"Bencidamina",                "Hasta 50 mg",      "Tabletas/Cápsulas",         "Oral"},
                {"Ácido Acetilsalicílico",     "Hasta 500 mg",     "Tabletas",                  "Oral"},
                {"Meloxicam",                  "Hasta 7.5 mg",     "Tabletas/Cápsulas",         "Oral"},
                {"Metocarbamol",               "500mg",            "Tabletas",                  "Oral"},
                {"Ibuprofeno",                 "Hasta 200 mg",     "Tabletas/Grageas/Cápsulas", "Oral"},
                {"Ibuprofeno",                 "100mg/5ml",        "Suspensión",                 "Oral"},
                {"Naproxeno",                  "Hasta 250 mg",     "Tabletas/Cápsulas",         "Oral"}
        };
        for (int row = 0; row < 11; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        tableLayout.addView(addRow("Quedan aprobados todos los antiinflamatorios de uso tópico en presentación de gel, crema, spray y parches."));

        ll.addView(tableLayout);
    }
    private void antiacidos() {
        String data[][] = new String[][]{
                {"Bismuto",                    "------------", "------------",      "Oral"},
                {"Bromuro de Otilonio",        "40mg",         "Tabletas",          "Oral"},
                {"Compuestos de Magnesio",     "------------", "------------",      "Oral"},
                {"Compuestos de Aluminio",     "------------", "------------",      "Oral"},
                {"Loperamida",                 "2 mg",         "Tabletas",          "Oral"},
                {"Bromuro de N-Butilhioscina", "10mg",         "Tabletas",          "Oral"},
                {"Meveberina Clorhidrato",     "200mg",        "Cápsula",           "Oral"},
                {"Clorhidrato de Propinoxato", "Hasta 10mg",   "Tabletas",          "Oral"},
                {"Simeticona",                 "Hasta 125mg",  "Tabletas",          "Oral"},
                {"Bromuro de Clidinio",        "2.5 mg",       "Tabletas",          "Oral"},
                {"Bicarbonato de sodio",       "-----------",  "Polvo",             "Oral"},
                {"Dimenhidrinato",             "50 mg",        "Tabletas",          "Oral"},
                {"Glicerina",                  "Hasta 5.6 g",  "Supositorio",       "Tópico"},
                {"Lansoprazol",                "Hasta 15 mg",  "Tabletas/Cápsulas", "Oral"}
        };
        for (int row = 0; row < 11; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        tableLayout.addView(addRow("El empaque debe incluir las siguientes leyendas:\n" +
                "*Dosis hasta 15 mg al día por 7 días, sin prolongar el tratamiento ni repetir ciclo sin antes consultar a un médico.\n" +
                "*Indicación terapéutica: Alivio del reflujo y ardor de estómago.\n" +
                "*Si los síntomas persisten consulte a su médico.\n" +
                "*Leyenda que advierta sobre la incidencia de efectos secundarios graves para la salud con el uso prolongado del producto."));

        tableLayout.addView(addRow("Ranitidina","Tabletas hasta 150 mg; Jarabe hasta 75mg/5mL","Tabletas/Cápsulas/Jarabe","Oral"));

        tableLayout.addView(addRow("En la presentación en jarabe el empaque debe incluir:\n" +
                "* Indicado únicamente para pacientes geriátricos/adultos; no uso en niños.Se debe evitar en el etiquetado cualquier inductor de su uso en niños (colores llamativos, figuras alusivas, etc.)"));

        data = new String[][]{
                {"Petrolato líquido o aceite mineral","Hasta 30g/100mL","Suspensión/Emulsión",              "Oral"},
                {"Psyllium Plántago",                 "----------",     "Polvo",                            "Oral"},
                {"Preparado paregórico alcanforado",  "Hasta 0.2%",     "Tabletas/Suspensión",              "Oral"},
                {"Kaolin Coloidal",                   "Hasta 1 gr/5mL", "Suspensión",                       "Oral"},
                {"Sucralfato",                        "Hasta 1 g",      "Suspensión/polvo",                 "Oral"},
                {"Saccharomyces Boulardii",           "Hasta 250mg",    "Cápsula/Granulado para suspensión","Oral"},
                {"Sueros de Rehidratación oral",      "-----------",    "Solución oral/Polvo",              "Oral"}
        };
        for (int row = 0; row < 7; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        ll.addView(tableLayout);
    }
    private void antimicoticos() {
        tableLayout.addView(addRow("Fluconazol","Hasta 150 mg","Tableta/Cápsula","Oral"));

        tableLayout.addView(addRow("Aprobado bajo las siguientes condiciones:\n" +
                "* Indicación terapéutica: Candidiasis vaginal\n" +
                "* Una sola dosis\n" +
                "* No hacer alusión al uso para otras indicaciones\n" +
                "* Advertir sobre si padece otras enfermedades, o si los síntomas persisten, consultar con un médico\n" +
                "* Incluir leyenda de contraindicación en pacientes con enfermedades de hígado y no consumir si se ingiere alcohol."));

        tableLayout.addView(addRow("Quedan aprobados todos los antimicóticos y antipruriginosos de uso tópico en presentación de gel, spray, cremas, talcos, lociones y óvulos."));

        ll.addView(tableLayout);
    }

    private void dermatologico() {
        String data[][] = new String[][]{
                {"Neomicina",             "Hasta 1%",                "Gel/Crema/ungüento/solución",        "Tópica"},
                {"Cloruro de Benzalconio","------------",            "------------",                       "Tópica"},
                {"Bacitracina",           "Hasta 30,000 UIpor 100 g","Gel/Crema/ungüento/solución",        "Tópica"},
                {"Ácido Fusídico",        "Hasta 2%",                "Gel/Crema/ungüento/solución/parches","Tópica"},
                {"Yodopovidona",          "Hasta el 10%",            "Gel/Crema/ungüento/solución/jabón",  "Tópica"},
                {"Hidrocortisona",        "Hasta 1%",                "Gel/Crema/Ungüento",                 "Tópica"},
                {"Aciclovir",             "Hasta 5%",                "Gel/Crema/ungüento/solución",        "Tópica"}
        };
        for (int row = 0; row < 7; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        tableLayout.addView(addRow("Quedan aprobados todos los cicatrizantes y regeneradores de tejido que contengan Vitamina A, D, E, Pantenol, Oxido de Zinc, Dióxido de Zinc, Alantoína"));

        ll.addView(tableLayout);
    }

    private void aceites() {
        tableLayout.addView(addRow("Quedan aprobados los preparados que contengan los siguientes aceites esenciales " +
                "(en todas las vías de administración excepto las inyectables): Mentol, alcanfor, " +
                "gomenol, eucaliptol, bálsamo, salicilato de metilo, terpina, trementina, timol."));

        ll.addView(tableLayout);
    }
    private void vitaminas(){
        String data[][] = new String[][]{
                {"Multivitaminas",           "--------------", "Tableta/Cápsula/Gelcaps/Jarabe/ampolla bebible", "Oral"},
                {"Vitamina A",               "--------------", "-----------",                                    "Oral"},
                {"Vitaminas del Complejo B", "--------------", "-----------",                                    "Oral"},
                {"Vitamina C",               "--------------", "-----------",                                    "Oral"},
                {"Vitamina D",               "--------------", "-----------",                                    "Oral"},
                {"Vitamina E",               "--------------", "-----------",                                    "Oral"},
                {"Glucosamina",              "Hasta 1500 mg", "Tabletas/Cápsulas/Polvo",                         "Oral"},
                {"N-acetilcisteina",         "Hasta 600 mg",  "Cápsulas/Tabletas",                               "Oral"}
        };
        for (int row = 0; row < 8; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        tableLayout.addView(addRow("La N-acetilcisteína considerada como antioxidante debe cumplir con lo " +
                "requerido por esta Dirección para los suplementos nutricionales y no hacer " +
                "referencia a otras indicaciones terapéuticas específicas."));
        tableLayout.addView(addRow("Según el Reglamento General de la Ley de Medicamentos en el Art. 3. Literal 43) El término suplemento nutricional es sinónimo de: complemento " +
                "alimenticio, suplemento nutritivo, suplemento dietético y suplemento vitamínico. " +
                "Los suplementos nutricionales serán de venta libre siempre que se " +
                "encuentren dentro de los rangos de ingesta diaria máxima definidos por la " +
                "Dirección Nacional de Medicamentos."));

        ll.addView(tableLayout);
    }

    private void common(String medication){
        int rowNum;
        String data[][] = new String[0][];

        switch (medication)
        {
            case "urologo":
                rowNum = 2;
                data = new String[][]{
                        {"Fenazopiridina", "100mg", "Tabletas", "Oral"},
                        {"Metenamina", "Hasta 500 mg", "Tableta/Polvo", "Oral"}
                };
                break;
            case "antiparasitarios":
                rowNum = 15;
                data = new String[][]{
                        {"Albendazol"         ,"Hasta 400 mg"             ,"Tableta/Cápsula","Oral"  },
                        {"Albendazol"         ,"Hasta 400 mg/10mL ó 20 mL","Suspensión"     ,"Oral"  },
                        {"Benzoato de bencilo","Hasta el 25%"             ,"Solución"       ,"Tópica"},
                        {"Furazolidona"       ,"Hasta 100 mg"             ,"Tableta/Cápsula","Oral"  },
                        {"Furazolidona"       ,"Hasta 50 mg/5mL"          ,"Suspensión"     ,"Oral"  },
                        {"Nitazoxanida"       ,"500mg"                    ,"Tabletas"       ,"Oral"  },
                        {"Nitazoxanida"       ,"100mg/5ml"                ,"Jarabe"         ,"Oral"  },
                        {"Mebendazol"         ,"500mg"                    ,"Tabletas"       ,"Oral"  },
                        {"Mebendazol"         ,"20mg/ml"                  ,"Suspensión"     ,"Oral"  },
                        {"Pamoato de Pirantel","250mg"                    ," Tableta"       ,"Oral"  },
                        {"Permetrina"         ,"5%"                       ,"Crema"          ,"Tópica"},
                        {"Permetrina"         ,"1%"                       ,"Loción"         ,"Tópica"},
                        {"Secnidazol"         ,"500mg"                    ,"Tabletas"       ,"Oral"  },
                        {"Secnidazol"         ,"100mg/5ml"                ,"Suspensión"     ,"Oral"  },
                        {"Tinidazol"          ,"500mg"                    ,"Tabletas"       ,"Oral"  },
                };
                break;
            case "antitusivos":
                rowNum = 23;
                data = new String[][]{
                        {"Ambroxol"                               ,"Hasta 15mg/5ml"  ,"Jarabe"                        ,"Oral"           },
                        {"Carboximetil-cisteína"                  ,"Hasta 150mg/5mL" ,"Jarabe"                        ,"Oral"           },
                        {"Cloruro de Amonio"                      ,"Hasta 100 mg/5mL","Jarabe"                        ,"Oral"           },
                        {"Clorfeniramina"                         ,"4mg"             ,"Tabletas/Gelcaps/Polvo"        ,"Oral"           },
                        {"Clorfeniramina"                         ,"2mg/5ml"         ,"Jarabe"                        ,"Oral"           },
                        {"Cetirizina"                             ,"Hasta 10 mg"     ,"Tabletas/Cápsulas"             ,"Oral"           },
                        {"Cetirizina"                             ,"Hasta 5mg/5mL"   ,"Jarabe"                        ,"Oral"           },
                        {"Cetilpiridinio"                         ,"Hasta 2.5 mg"    ,"Tabletas/Cápsulas/Caramelo"    ,"Oral"           },
                        {"Dextrometorfano"                        ,"Hasta 15mg/5ml"  ,"Jarabe"                        ,"Oral"           },
                        {"Dextrometorfano"                        ,"Hasta 30 mg"     ,"Tableta/Polvo/Gelcaps/Cápsulas","Oral"           },
                        {"Difenhidramina"                         ,"Hasta 25mg"      ,"Tabletas"                      ,"Oral"           },
                        {"Difenhidramina"                         ,"Hasta 12.5mg/5mL","Jarabe"                        ,"Oral"           },
                        {"Difenhidramina"                         ,"Hasta 1%"        ,"Gel/Crema/Ungüento/Loción"     ,"Tópica"         },
                        {"Doxilamina Succinato"                   ,"Hasta 25 mg"     ,"Tabletas/Gelcaps/Jarabe"       ,"Oral"           },
                        {"Fenilefrina"                            ,"Hasta 25mg/5mL"  ,"Jarabe"                        ,"Oral"           },
                        {"Fenilefrina"                            ,"Hasta 10 mg"     ,"Tabletas/Gelcaps/Polvo"        ,"Oral"           },
                        {"Fenilefrina"                            ,"----------"      ,"Gotas"                         ,"Nasal/Oftálmica"},
                        {"Hedera Helix"                           ,"----------"      ,"Jarabe"                        ,"Oral"           },
                        {"Feniramina"                             ,"4mg"             ,"Tabletas"                      ,"Oral"           },
                        {"Guayacolato de Glicerilo o Guaifenasina","Hasta 100mg/5mL" ,"Jarabe"                        ,"Oral"           },
                        {"Loratadina"                             ,"10mg"            ,"Tabletas"                      ,"Oral"           },
                        {"Loratadina"                             ,"10mg/5ml"        ,"Jarabe"                        ,"Oral"           },
                        {"N-acetilcisteína"                       ,"Hasta 100 mg"    ,"Polvo para solución"           ,"Oral"           }
                };
                break;
            case "anestesicos":
                rowNum = 3;
                data = new String[][]{
                        {"Benzocaína","Hasta 10 mg","Tableta masticable/Caramelo","Oral-Tópico"},
                        {"Benzocaína","Hasta el 5%","Soluciones"                 ,"Tópico"      },
                        {"Lidocaína","Hasta el 10%","Crema/Ungüento/aerosol"     ,"Tópico"      },
                };
                break;
            case "descongestivos":
                rowNum =6;
                data = new String[][]{
                        {"Nafazolina"                        ,"----------","----------","Oftálmica"       },
                        {"Antazolina"                        ,"----------","----------","Oftálmica"       },
                        {"Tetrazolina"                       ,"----------","----------","Oftálmica"       },
                        {"Oximetazolina"                     ,"----------","----------","Oftálmica/ Nasal"},
                        {"Xilometazolina"                    ,"----------","----------","Oftálmica"       },
                        {"Cloruro de Sodio (Solución Salina)","Hasta 0.9%","Spray/Gotas/Solución","Tópica"}
                };
                break;
            case "oftalmico":
                rowNum = 2;
                data = new String[][]{
                        {"Hidroxipropil-metilcelulosa","Hasta el 0.5%","Gotas oftálmicas","Tópica"},
                        {"Ácido poli-acrílico","Hasta 2mg","Gel oftálmico","Tópica"}
                };
                break;
            case "otros":
                rowNum = 3;
                data = new String[][]{
                        {"Cafeína","Hasta 200 mg/por unidad de dosis","Tableta/Soluciones orales/ Polvos","Oral"},
                        {"Cafeína","Hasta 40mg/10mL","Jarabes/Soluciones orales","Oral"},
                        {"Cera esteres/Cera Blanca","125 g/ 120g","Crema","Tópica"}
                };
                break;
            default:
                rowNum = 0;
                break;
        }
        for (int row = 0; row < rowNum; row++)
            tableLayout.addView(addRow(data[row][0],data[row][1],data[row][2],data[row][3]));

        ll.addView(tableLayout);
    }

    private TextView cell(String data){
        TextView textView = new TextView(context);
        itemParams.setMargins(1,1,1,1);
        textView.setLayoutParams(itemParams);
        textView.setText(data);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setBackgroundColor(Color.parseColor("#ffffff"));

        return textView;
    }
    private TableRow.LayoutParams setFull()
    {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 4;
        params.setMargins(1,1,1,1);
        return params;
    }

    private TableRow addRow(String denom, String con, String forma, String via)
    {
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(rowParams);
        tableRow.addView(cell(denom));
        tableRow.addView(cell(con));
        tableRow.addView(cell(forma));
        tableRow.addView(cell(via));
        return tableRow;
    }

    private TableRow addRow(String data)
    {
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(rowParams);
        tableRow.addView(cell(data),setFull());
        return tableRow;
    }
}
