package model;

public class Geolocalizacion {
		private Double latitud; 
		private Double longitud;
		private Double varianza;
		private String ciudad;
		
		public Double getLatitud() {
			return latitud;
		}
		public void setLatitud(Double latitud) {
			this.latitud = latitud;
		}
		public Double getLongitud() {
			return longitud;
		}
		public void setLongitud(Double longitud) {
			this.longitud = longitud;
		}
		public Double getVarianza() {
			return varianza;
		}
		public void setVarianza(Double varianza) {
			this.varianza = varianza;
		}
		public String getCiudad() {
			return ciudad;
		}
		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}
}
