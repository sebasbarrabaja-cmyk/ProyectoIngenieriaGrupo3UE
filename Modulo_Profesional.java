package fp_CRUD;

public class Modulo_Profesional {
	private int ID_Modulo;
	private String Nombre_Modulo;
	private String Ciclo_Perteneciente;
    private int Horas_t;
	private String Nombre_Docente;
	private String Curso_Imp;

    public Modulo_Profesional (int ID_Modulo,String Nombre_Modulo, String Ciclo_Perteneciente,int Horas_t,String Nombre_Docente,int Teléfono,int Fecha_Nacimiento,String Ciclo_formativo,String Curso_Imp){
    	this.ID_Modulo = ID_Modulo;
    	this.Nombre_Modulo = Nombre_Modulo;
    	this.Ciclo_Perteneciente = Ciclo_Perteneciente;
    	this.Horas_t = Horas_t;
    	this.Nombre_Docente = Nombre_Docente;
    	this.Curso_Imp = Curso_Imp;
	    }

	public int getID_Modulo() {
		return ID_Modulo;
	}

	public void setID_Modulo(int iD_Modulo) {
		ID_Modulo = iD_Modulo;
	}

	public String getNombre_Modulo() {
		return Nombre_Modulo;
	}

	public void setNombre_Modulo(String nombre_Modulo) {
		Nombre_Modulo = nombre_Modulo;
	}

	public String getCiclo_Perteneciente() {
		return Ciclo_Perteneciente;
	}

	public void setCiclo_Perteneciente(String ciclo_Perteneciente) {
		Ciclo_Perteneciente = ciclo_Perteneciente;
	}

	public int getHoras_t() {
		return Horas_t;
	}

	public void setHoras_t(int horas_t) {
		Horas_t = horas_t;
	}

	public String getNombre_Docente() {
		return Nombre_Docente;
	}

	public void setNombre_Docente(String nombre_Docente) {
		Nombre_Docente = nombre_Docente;
	}

	public String getCurso_Imp() {
		return Curso_Imp;
	}

	public void setCurso_Imp(String curso_Imp) {
		Curso_Imp = curso_Imp;
	}
    
    public String toString() {
        return  "Información del Modulo :"+
        		"\nID : " + ID_Modulo +
        		"\nNombre : " + Nombre_Modulo +
        		"\nCiclo : " + Ciclo_Perteneciente +
        		"\nHoras Totales : " + Horas_t +
        		"\nNombre del Docente : " + Nombre_Docente +
        		"\nCurso impartido : " + Curso_Imp;
    }


}
