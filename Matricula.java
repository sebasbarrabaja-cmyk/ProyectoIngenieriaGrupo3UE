package fp_CRUD;

public class Matricula {
	private int ID_Matricula;
	private String Nombre_Matricula;
	private String Modulo;
    private String Convocatoria ;
	private String Calificación ;
	private int Fecha_Evaluación;
	
    public Matricula (int ID_Matricula,String Nombre_Matricula, String Modulo,String Convocatoria,String Calificación,int Fecha_Evaluación){
    	this.ID_Matricula = ID_Matricula;
    	this.Nombre_Matricula = Nombre_Matricula;
    	this.Modulo = Modulo;
    	this.Convocatoria = Convocatoria;
    	this.Calificación = Calificación;
    	this.Fecha_Evaluación = Fecha_Evaluación;
	    }

	public int getID_Matricula() {
		return ID_Matricula;
	}

	public void setID_Matricula(int iD_Matricula) {
		ID_Matricula = iD_Matricula;
	}

	public String getNombre_Matricula() {
		return Nombre_Matricula;
	}

	public void setNombre_Matricula(String nombre_Matricula) {
		Nombre_Matricula = nombre_Matricula;
	}

	public String getModulo() {
		return Modulo;
	}

	public void setModulo(String modulo) {
		Modulo = modulo;
	}

	public String getConvocatoria() {
		return Convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		Convocatoria = convocatoria;
	}

	public String getCalificación() {
		return Calificación;
	}

	public void setCalificación(String calificación) {
		Calificación = calificación;
	}

	public int getFecha_Evaluación() {
		return Fecha_Evaluación;
	}

	public void setFecha_Evaluación(int fecha_Evaluación) {
		Fecha_Evaluación = fecha_Evaluación;
	}
    
    public String toString() {
        return  "Información de la Matricula :"+
        		"\nID : " + ID_Matricula +
        		"\nNombre : " + Nombre_Matricula +
        		"\nModulo : " + Modulo +
        		"\nConvocatoria : " + Convocatoria +
        		"\nCalificación : " + Calificación +
        		"\nFecha de evaluación : " +Fecha_Evaluación;
    }
    

}
