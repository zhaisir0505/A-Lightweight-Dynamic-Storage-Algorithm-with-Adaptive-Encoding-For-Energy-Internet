package GEP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Fitness extends Encode {
    public List<List<String>> dates;
    public double[] targets;

    public void setTargets() {
        Datesets a = new Datesets();
        try {
            dates = a.reader("C:\\Users\\Chenfulin\\Desktop\\1.txt");
        } catch (IOException e) {

            e.printStackTrace();
        }

        targets = new double[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            List<String> date = dates.get(i);
            Double b = Double.parseDouble(date.get(date.size() - 1));
            targets[i] = b;

        }

    }

    public ArrayList geteachValue(String strs) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String str = strs;
        ArrayList eachvalue = new ArrayList();
        char[] ver = { 'b', 'd', 'f', 'j', 'k', 'u' };// �ڽ����޸ı��������ͱ�������ʱ�˴���Ҫ�޸�
        for (int i = 0; i < dates.size(); i++) {
            List<String> date = dates.get(i);
            for (int j = 0; j < ver.length; j++) {
                String dest = String.valueOf(ver[j]);
                strs = strs.replace(dest, date.get(j));

            }
            Object result = engine.eval(strs);
            double Mathmatical = Double.parseDouble(result.toString());
            if (isNaN(Mathmatical))
                Mathmatical = 0.0;
            if (Mathmatical > 1000000000)
                Mathmatical = 0.0;
            if (Mathmatical <0)
                Mathmatical = 0.0;
            eachvalue.add(Mathmatical);
            strs = str;

        }
        return eachvalue;
    }

    public double getfitness(String str) {
        Fitness t = new Fitness();
        t.setTargets();
        ArrayList value = null;
        try {
            value = t.geteachValue(str);
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        double fitness = t.Relativeerror(value);
        return fitness;
    }

    public double Absoluteerror(ArrayList value) {
        double fitness = 0.0;
        for (int i = 0; i < targets.length; i++) {
            fitness += 100 - Math.abs((double) value.get(i) - targets[i]);
        }
        if (fitness < 0)
            fitness = 0;
        return fitness;
    }

    public double Relativeerror(ArrayList value) {
        double fitness = 0.0;
        for (int i = 0; i < targets.length; i++) {
            fitness += 1000 - Math.abs((((double) value.get(i) - targets[i]) / targets[i])*100);
        }
        if (fitness < 0)
            fitness = 0;
        return fitness;
    }

    private boolean isNaN(double mathmatical) {
        return (mathmatical != mathmatical);
    }

	/*		public static void main(String[] args) {
				Fitness t = new Fitness();
				t.setTargets();
				try {
					t.geteachValue("b+u");
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(t.getfitness("b"));
			}*/
}