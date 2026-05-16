package fp_CRUD;

public class Alumno {
	private int ID;
	private String Nombre;
	private String Apellido;
    private String DNI;
	private String Email;
	private int Teléfono;
    private int Fecha_Nacimiento;
    private String Ciclo_formativo;	
    private String Curso;	
    private String Estado;	

		
    public Alumno (int ID,String Nombre, String Apellido,String DNI,String Email,int Teléfono,int Fecha_Nacimiento,String Ciclo_formativo,String Curso,String Estado){
    	this.ID = ID;
    	this.Nombre = Nombre;
    	this.Apellido = Apellido;
    	this.DNI = DNI;
    	this.Email = Email;
    	this.Teléfono = Teléfono;
    	this.Fecha_Nacimiento = Fecha_Nacimiento;
    	this.Ciclo_formativo = Ciclo_formativo;
    	this.Curso = Curso;
    	this.Estado = Estado;
	    }


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public String getApellido() {
		return Apellido;
	}


	public void setApellido(String apellido) {
		Apellido = apellido;
	}


	public String getDNI() {
		return DNI;
	}


	public void setDNI(String dNI) {
		DNI = dNI;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public int getTeléfono() {
		return Teléfono;
	}


	public void setTeléfono(int teléfono) {
		Teléfono = teléfono;
	}


	public int getFecha_Nacimiento() {
		return Fecha_Nacimiento;
	}


	public void setFecha_Nacimiento(int fecha_Nacimiento) {
		Fecha_Nacimiento = fecha_Nacimiento;
	}


	public String getCiclo_formativo() {
		return Ciclo_formativo;
	}


	public void setCiclo_formativo(String ciclo_formativo) {
		Ciclo_formativo = ciclo_formativo;
	}


	public String getCurso() {
		return Curso;
	}


	public void setCurso(String curso) {
		Curso = curso;
	}


	public String getEstado() {
		return Estado;
	}


	public void setEstado(String estado) {
		Estado = estado;
	}
    
    @Override
    public String toString() {
        return  "Información del Alumno :"+
        		"\nID : " + ID +
        		"\nNombre : " + Nombre +
        		"\nApellido : " + Apellido +
        		"\nDNI : " + DNI +
        		"\nEmail : " + Email +
        		"\nTeléfono : " + Teléfono +
        		"\nFecha_Nacimiento : " + Fecha_Nacimiento +
        		"\nCiclo_formativo : " + Ciclo_formativo +
        		"\nCurso : " + Curso +
        		"\nEstado : " + Estado;
    }


}
