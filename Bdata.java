import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;

class lineInt implements Serializable {
	int Id; // номер по списку
	boolean Flr; // Пол
	int School; // номер школы
	boolean Medal; // наличие медали
	int Math; // оценка по математике
	int Physic; // оценка по физике
	int Info; // оценка по информатике
	boolean Offset; // наличие зачета за сочинение

	public lineInt() {

	}

}

class lineStr implements Serializable {
	String Surname;// Фамилия
	String Name; // Имя
	String Patronymic; // Отчество

	public lineStr() {

	}

}

class lineBD implements Serializable { // класс одной строки
	lineInt Int;
	lineStr Str;

	public lineBD(lineInt i, lineStr str) {
		Int = i;
		Str = str;
	}
	
	public lineBD() {
		Int = new lineInt();
		Str = new lineStr();
	}
}

class Line {
	int id;
	int math; 
	int physic; 
	int info;
	
	public Line(int id, int math, int physic, int info) {
		this.id = id;
		this.math = math;
		this.physic = physic;
		this.info = info;
	}
	
	public Line(lineInt line) {
		this(line.Id, line.Math, line.Physic, line.Info);
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		return ((math == other.math) && (physic == other.physic)) ||
				((math == other.math) && (info == other.info)) || 
				((physic == other.physic) && (info == other.info));
	}	
}

public class Bdata {
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		InputStream r = new FileInputStream("read");
		Scanner s = new Scanner(r);
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("out"));
		while(s.hasNextInt()) {
			lineBD p = new lineBD();
			p.Int.Id = s.nextInt();
			p.Str.Surname = s.next();
			p.Str.Name = s.next();
			p.Str.Patronymic = s.next();
			p.Int.Flr = s.nextBoolean();
			p.Int.School = s.nextInt();
			p.Int.Medal = s.nextBoolean();
			p.Int.Math = s.nextInt();
			p.Int.Physic = s.nextInt();
			p.Int.Info = s.nextInt();
			p.Int.Offset = s.nextBoolean();
			out.writeObject(p);
		}
		if (s.hasNext())
			System.out.println("Некорректный ввод");
		out.close();
		

		ObjectInputStream in = new ObjectInputStream(new FileInputStream("out"));
		ArrayList<Line> lines = new ArrayList<>();
		while(true)	{
			lineBD r1;
			try {
				r1 = (lineBD)in.readObject();
			} catch (EOFException e) {
				break;
			}
			if (r1.Int.Flr)
				lines.add(new Line(r1.Int));
		//	System.out.println(r1.Int.Id + " " + r1.Str.Surname + " "
		//			+ r1.Str.Name + " " + r1.Str.Patronymic + " " + r1.Int.Flr
		//			+ " " + r1.Int.School + " " + r1.Int.Medal + " "
		//			+ r1.Int.Math + " " + r1.Int.Physic + " " + r1.Int.Info
		//			+ " " + r1.Int.Offset);
		}
		in.close();
		LinkedHashSet<Integer> ids = new LinkedHashSet<>();
		for(int i = 0; i < lines.size(); i++) {
			Line l1 = lines.get(i);
			for(int j = i + 1; j < lines.size(); j++) {
				Line l2 = lines.get(j);
				if (l1.equals(l2)) {
					ids.add(l1.id);
					ids.add(l2.id);
				}
			}
		}
		for(Integer id : ids)
			System.out.println(id);
	}

}
