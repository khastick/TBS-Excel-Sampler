@Grab(group='org.apache.commons', module='commons-collections4', version='4.1')

@Grab(group='org.apache.xmlbeans', module='xmlbeans', version='2.6.0')

@Grab(group='org.apache.poi', module='poi', version='3.15')
@Grab(group='org.apache.poi', module='poi-ooxml', version='3.15')
@Grab(group='org.apache.poi', module='poi-ooxml-schemas', version='3.15')

@Grab(group='org.apache.logging.log4j', module='log4j-core', version='2.6.2')
@Grab(group='org.apache.logging.log4j', module='log4j-api', version='2.6.2')

@Grab(group='com.google.guava', module='guava', version='21.0')


import org.apache.poi.xssf.usermodel.*
import org.apache.poi.xssf.streaming.*

import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import java.util.stream.*

import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Created by Khama on 2017-03-01.
 * To Do : Manually set the number of QA's per person
 */

/*
ztable
names
 */



// open excel
// open stream
// copy into an array
class Excel{
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    File file;
    String directory;

    Excel(String path){
        file = new File(path);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
    }

    Excel(File aFile){
        file = aFile;
        workbook = new XSSFWorkbook(aFile);
        sheet = workbook.getSheetAt(0);
        directory =aFile.getParent();
    }

    void close(){
        workbook.close();
    }

    void save(){
        FileOutputStream out = new FileOutputStream(directory + "\\output.xlsx");
        workbook.write(out);
        out.close();

    }

    def getExcelColumnNumber = { column ->
        int result = 0;

        for (int i = 0; i < column.length(); i++) {
            result *= 26;
            result += column.charAt(i) - (char)'A' + 1;
        }

        return result - 1;
    };

    def getExcelColumnName = { number ->
        String col = ""
        int num = number - 1;

        while (num >=	0) {
            int numChar = (num % 26)	+ 65;
            col = col + String.valueOf((char)numChar);
            num = (num	/ 26) - 1;
        }

        return new StringBuilder(col).reverse().toString();
    };

    def getCellValue = { cell, evaluator ->
        def cellValue = null;

        if (cell != null) {
            def cellRef = cell.getReference();

            switch (cell.getCellType()) {
                case cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case cell.CELL_TYPE_NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case cell.CELL_TYPE_BLANK:
                    cellValue = String.valueOf(evaluator.evaluate(cell));
                    break;
                default :
                    break;
            }
        }

        if (cellValue != null) { cellValue = cellValue.trim(); }

        return cellValue;
    };

    def cellContainsInformation = { colVal ->
        def returnFlag	= true;

        if ((colVal == null) || colVal.equals("") || colVal.matches("(?ui)null")) {
            returnFlag = false;
        }

        return returnFlag;
    };

    def setCellValue = { cell, val ->
        cell.setCellValue(val.trim());
    };
}

// Creates a ztable and finds the z value based on the confidence
class ZTable{
    double z;
    Table<Double,Double,Double> table;
    double[] headings;

    void arrayToTable(Table t, double[][] table){
         headings = table[0];

        for(int i = 1; i < table.length; i++){
            for(int j = 1; j < headings.length; j++){
                t.put(table[i][0],headings[j],table[i][j]);
            }

        }
    }

    ZTable(double confidence){

        table = HashBasedTable.create();

        double[][] array = [
                [0,0,0.01,0.02,0.03,0.04,0.05,0.06,0.07,0.08,0.09],
                [0,0,0.004,0.008,0.012,0.016,0.0199,0.0239,0.0279,0.0319,0.0359],
                [0.1,0.0398,0.0438,0.0478,0.0517,0.0557,0.0596,0.0636,0.0675,0.0714,0.0753],
                [0.2,0.0793,0.0832,0.0871,0.091,0.0948,0.0987,0.1026,0.1064,0.1103,0.1141],
                [0.3,0.1179,0.1217,0.1255,0.1293,0.1331,0.1368,0.1406,0.1443,0.148,0.1517],
                [0.4,0.1554,0.1591,0.1628,0.1664,0.17,0.1736,0.1772,0.1808,0.1844,0.1879],
                [0.5,0.1915,0.195,0.1985,0.2019,0.2054,0.2088,0.2123,0.2157,0.219,0.2224],
                [0.6,0.2257,0.2291,0.2324,0.2357,0.2389,0.2422,0.2454,0.2486,0.2517,0.2549],
                [0.7,0.258,0.2611,0.2642,0.2673,0.2704,0.2734,0.2764,0.2794,0.2823,0.2852],
                [0.8,0.2881,0.291,0.2939,0.2967,0.2995,0.3023,0.3051,0.3078,0.3106,0.3133],
                [0.9,0.3159,0.3186,0.3212,0.3238,0.3264,0.3289,0.3315,0.334,0.3365,0.3389],
                [1,0.3413,0.3438,0.3461,0.3485,0.3508,0.3531,0.3554,0.3577,0.3599,0.3621],
                [1.1,0.3643,0.3665,0.3686,0.3708,0.3729,0.3749,0.377,0.379,0.381,0.383],
                [1.2,0.3849,0.3869,0.3888,0.3907,0.3925,0.3944,0.3962,0.398,0.3997,0.4015],
                [1.3,0.4032,0.4049,0.4066,0.4082,0.4099,0.4115,0.4131,0.4147,0.4162,0.4177],
                [1.4,0.4192,0.4207,0.4222,0.4236,0.4251,0.4265,0.4279,0.4292,0.4306,0.4319],
                [1.5,0.4332,0.4345,0.4357,0.437,0.4382,0.4394,0.4406,0.4418,0.4429,0.4441],
                [1.6,0.4452,0.4463,0.4474,0.4484,0.4495,0.4505,0.4515,0.4525,0.4535,0.4545],
                [1.7,0.4554,0.4564,0.4573,0.4582,0.4591,0.4599,0.4608,0.4616,0.4625,0.4633],
                [1.8,0.4641,0.4649,0.4656,0.4664,0.4671,0.4678,0.4686,0.4693,0.4699,0.4706],
                [1.9,0.4713,0.4719,0.4726,0.4732,0.4738,0.4744,0.475,0.4756,0.4761,0.4767],
                [2,0.4772,0.4778,0.4783,0.4788,0.4793,0.4798,0.4803,0.4808,0.4812,0.4817],
                [2.1,0.4821,0.4826,0.483,0.4834,0.4838,0.4842,0.4846,0.485,0.4854,0.4857],
                [2.2,0.4861,0.4864,0.4868,0.4871,0.4875,0.4878,0.4881,0.4884,0.4887,0.489],
                [2.3,0.4893,0.4896,0.4898,0.4901,0.4904,0.4906,0.4909,0.4911,0.4913,0.4916],
                [2.4,0.4918,0.492,0.4922,0.4925,0.4927,0.4929,0.4931,0.4932,0.4934,0.4936],
                [2.5,0.4938,0.494,0.4941,0.4943,0.4945,0.4946,0.4948,0.4949,0.4951,0.4952],
                [2.6,0.4953,0.4955,0.4956,0.4957,0.4959,0.496,0.4961,0.4962,0.4963,0.4964],
                [2.7,0.4965,0.4966,0.4967,0.4968,0.4969,0.497,0.4971,0.4972,0.4973,0.4974],
                [2.8,0.4974,0.4975,0.4976,0.4977,0.4977,0.4978,0.4979,0.4979,0.498,0.4981],
                [2.9,0.4981,0.4982,0.4982,0.4983,0.4984,0.4984,0.4985,0.4985,0.4986,0.4986],
                [3,0.4987,0.4987,0.4987,0.4988,0.4988,0.4989,0.4989,0.4989,0.499,0.499]
        ];

        arrayToTable(table,array);

        z = getValue(confidence);
    }

    double getValue(double confidence){
        //XSSFRow row;
        //XSSFCell cell;
        double nConfidence = confidence/2;

        Set<Double> rows = table.rowKeySet();
        Set<Double> columns = table.columnKeySet();

        for(int i = 0; i < rows.size(); i++){

            for(int j = 0; j < columns.size(); j++){
                double row = rows[i];
                double column = columns[j];
                double value = table.get(row,column);

                if(value >= nConfidence){
                    return  row + column;
                }
            }
        }
    }
}

// add 2 columns, QA and assigned to
// heading row = first row + QA, and assigned To

// calculate sample size

// z table manually create

// cell = ztable(confidence index / 2)
// z value =  ztable(cell.x,0) + ztable(0,cell.y)
// p = .5
// sample size =( ( pow(z,2) * p(1-p) ) / pow(marginOfError,2) ) / (1 + ( ( pow(z,2) * p*(1-p))/ pow(marginOfError,2) * pop))
class Calculator{
    int sampleSize;

    Calculator(){

    }

    Calculator(int pop){
        this(0.1,0.95,0.5,pop);
    }

    Calculator(int sitesPerPerson, String[] names){
        sampleSize = names.size() * sitesPerPerson;
    }

    // e = margin of error, c = confidence, p = normal distribution, S = population
    Calculator(double e, double c, double p, int S){


        //initialize the table with employee details

        ZTable zTable = new ZTable(c);
        double z = zTable.z;
        double s1 = Math.pow(z,2) * p*(1-p);
        double s2 = s1/ Math.pow(e,2);
        double s3 = Math.pow(z,2) * p*(1-p);
        double s4 = Math.pow(e,2) * S;
        double s5 =  1 +  s3 / s4;
        sampleSize = s2 / s5;

    } // google guava - access mvnrepo google guava
}

class NameSystem{
    List names;
    int numSitesAssigned;
    int selectedName;
    int sitesPerPerson;

    public NameSystem(int sampleSize, List aNames){
        names = aNames;
        numSitesAssigned = 0;
        selectedName = 0;
        sitesPerPerson = Math.ceil(sampleSize/names.size()); // sitesPerPerson = sample size / numOfPeople
    }

    String getName(){
        ++numSitesAssigned;
        String name = names.get(selectedName);

        if(numSitesAssigned >= sitesPerPerson){
            ++selectedName;
            numSitesAssigned = 0;
        }

        return name;
    }
}

// create a set of unique numbers within pop range, and the size of the sample size
// create sorted set
class SampleSystem extends Excel{
    int pop;
    List range;
    // assign
    int QAIdx;
    int assignIdx;
    int headingRow;
    NameSystem namesSystem;
    Calculator calc;

    SampleSystem(File file, ConfigSystem config){
        super(file);

        List rows = getSelectedRows(config,false);
        range = genSelectedRange(rows,config.sampleSize);

        /* for representative samples
        pop = sheet.size(); // population = numOfRows - headers
        calc = new Calculator(error, confidence, normal, sheet.size());
        */

        headingRow = config.heading;

        QAIdx = sheet.getRow(headingRow).size();
        assignIdx = sheet.getRow(headingRow).size()+1;

        calc = new Calculator();
        calc.sampleSize = config.sampleSize;

        List names = genNames(config.numOfUsers);
        namesSystem = new NameSystem(config.sampleSize,names);
    }

    List genSelectedRange(List rows, int size){
        List range;

        Collections.shuffle(rows);
        range = rows.subList(0,size);
        range.sort();

        return range;
    }

    List getAllRows(def sheet){
        List range = IntStream.range(1, sheet.size()).boxed().collect(Collectors.toList());
        return range;
    }

    // gets a list of row numbers of sites that are archived
    List getSelectedRows(ConfigSystem config, boolean isArchivedCondition){
        XSSFRow row;
        XSSFCell cell;

        int lastRow = sheet.getLastRowNum();

        List rows = new ArrayList<Integer>();

        for(int i = config.heading+1; i < lastRow; i++) {
            row = sheet.getRow(i)
            cell = row.getCell(config.archiveCol)

            boolean isArchived = cell != null
            if(isArchived != isArchivedCondition )continue;

            rows.add(i)
        }

        return rows;
    }



    List genNames(int numOfUsers){
        List names = new ArrayList<String>();

        for(int i = 0; i < numOfUsers; i++){
            names.add("user"+(i+1));
        }

        return names;
    }

    void assign(){
        int rowIdx;
        XSSFRow row;
        XSSFCell cell;

        for(int i = 0; i < calc.sampleSize; i++){
            rowIdx = range.get(i);
            row = sheet.getRow(rowIdx);

            cell = row.createCell(QAIdx);
            cell.setCellValue("True");
            cell = row.createCell(assignIdx);
            cell.setCellValue(namesSystem.getName());
      }

        row = sheet.getRow(headingRow);

        cell = row.createCell(QAIdx);
        cell.setCellValue("QA");
        cell = row.createCell(assignIdx);
        cell.setCellValue("Assigned");

        save();
        close();
    }
}

class LabelPanel extends JPanel{
    JLabel label;
    JTextField text;

    LabelPanel(String name){
        this(name,"");
    }

    LabelPanel(String name, String value){
        label = new JLabel(name);
        text = new JTextField(value);

        setLayout(new GridBagLayout());
        GridBagConstraints c =  new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(5,5,5,5);
        add(label, c);

        c.gridx = 1;
        c.weightx = 2;
        c.gridwidth = 5;
        add(text, c);
    }
}

class Interface extends JFrame{
    JButton assign;
    JButton config;
    LabelPanel error;
    LabelPanel confidence;
    LabelPanel normal;
    LabelPanel names;

    Interface(){
        error = new LabelPanel("error(%)","0");
        confidence = new LabelPanel("confidence(%)","0");
        normal = new LabelPanel("normal(%)","50");
        names = new LabelPanel("names","name1,name2,name3");
        assign = new JButton("SELECT FILE AND ASSIGN");
        config = new JButton("CONFIG AND ASSIGN");

        setLayout(new GridBagLayout());
        GridBagConstraints c =  new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1
        c.gridwidth = 3;
        c.gridheight = 1;
        add(error, c);

        c.gridy = 1;
        add(confidence, c);

        c.gridy = 2;
        add(normal, c);

        c.gridy = 3;
        add(names, c);

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.insets = new Insets(20,20,10,20);
        add(assign,c);

        c.insets = new Insets(10,20,20,20);
        c.gridy = 2;
        add(config,c);
    }
}

class ConfigSystem{
    def files, heading, names;
    boolean valid;
    double error, confidence, normal;
    int sampleSize,numOfUsers,archiveCol


    boolean isValid(){
        return valid;
    }


    ConfigSystem(String path){
        //File configFile = new File(path);

        //def config = new ConfigSlurper().parse(configFile.toURL());

       // extractConfigVar(config);
    }

    ConfigSystem(Interface io) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int result = fileChooser.showOpenDialog(io);

        if (result != JFileChooser.APPROVE_OPTION) {
            valid = false;
            return;
        }

        valid = true;

        File configurationFile = fileChooser.getSelectedFile();


        def config = new ConfigSlurper().parse(configurationFile.toURL());
        extractConfigVar(config)

    }

    void extractConfigVar(def config){
        files = [];
        if (config.ciFiles) {
            files = config.ciFiles;
        }

// The name of the sheet within the Excel file which contains the content inventory information.
        archiveCol = 0;
        if (config.archiveCol) {
            archiveCol = Integer.valueOf(config.archiveCol);
        }

        heading = 1;
        if (config.ciHeaderRowIndex) {
            heading = Integer.valueOf(config.ciHeaderRowIndex);
        }

        normal = 0;
        if (config.normal) {
            normal = Double.valueOf(config.normal);
        }

        error = 0;
        if (config.error) {
            error = Double.valueOf(config.error);
        }

        confidence = 0;
        if (config.confidence) {
            confidence = Double.valueOf(config.confidence);
        }

        sampleSize = 0;
        if (config.sampleSize) {
            sampleSize = Integer.valueOf(config.sampleSize);
        }

        names = [];
        if (config.names) {
            names = config.names;
        }

        numOfUsers = 0;
        if (config.numOfUsers) {
            numOfUsers = Integer.valueOf(config.numOfUsers);
        }
    }
}

class Controller{
    SampleSystem sampleSystem;
    Interface io;
    int headingIdx;
    String[] names;
    String sheetName;
    File[] files;
    double confidence, error, normal;
    ConfigSystem configuration;

    Controller(boolean cond){
        io =new Interface();

        configuration = new ConfigSystem(io);

        if(!configuration.isValid()){
            return;
        }

        configuration.files.each {
                File file = new File(it);

            sampleSystem = new SampleSystem(file, configuration);

                    sampleSystem.assign();
        }

        System.exit(0);
    }

    Controller(){
        io = new Interface();
        io.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        io.setSize(700,200);
        io.setVisible(true);

        io.assign.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                if(!validInputFields()){
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(io);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();

                    headingIdx = 1;
                    for(int i = 0; i < files.size(); i++) {
                        File file = files[i];

                        sampleSystem = new SampleSystem(file, names, headingIdx, error, confidence, normal);
                        sampleSystem.assign();
                    }
                    System.exit(0);
                }
            }
        });

    }

    boolean validInputFields(){
        normal = validateInput(io.normal.text.getText());
        if(normal == -1){
            invalidResponse("normal");
            return false;
        }

        confidence = validateInput(io.confidence.text.getText());
        if(confidence == -1){
            invalidResponse("confidence");
            return false;
        }

        error = validateInput(io.error.text.getText());
        if(error == -1){
            invalidResponse("error");
            return false;
        }

        names = io.names.text.getText().split(",");
        if(names.size()==0){
            invalidResponse("names");
            return false;
        }

        return true;
    }

    double validateInput(String input){
        try{
            return Double.parseDouble(input)/100;

        } catch (Exception e){
            return -1;
        }
    }

    void invalidResponse(String name){
        JOptionPane.showMessageDialog(io,
                "Error in field: " + name,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }


}

void testGetArchiveRows() {
    String path = "C:/Users/Khama/Documents/GitHub/Sample-And-Assign/src/PHAC-test-ci.xlsx"
    String configPath = "C:/Users/Khama/Documents/GitHub/Sample-And-Assign/src/config.groovy"
    ConfigSystem config = new ConfigSystem(configPath);

    File file = new File(path);

    SampleSystem sampleSystem = new SampleSystem(file, config);

    List archives = sampleSystem.getArchiveRows(config);
   println(archives.size());
}



Controller controller = new Controller(true);
// save excel