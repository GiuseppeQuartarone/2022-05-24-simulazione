package it.polito.tdp.itunes.db;

import java.util.Objects;

import it.polito.tdp.itunes.model.Track;

public class Adiacenza {
		 Track t1;
		 Track t2;
		 double differenza;
		
		 public Adiacenza(Track t1, Track t2, double differenza) {
			super();
			this.t1 = t1;
			this.t2 = t2;
			this.differenza = differenza;
		}

		public Track getT1() {
			return t1;
		}

		public void setT1(Track t1) {
			this.t1 = t1;
		}

		public Track getT2() {
			return t2;
		}

		public void setT2(Track t2) {
			this.t2 = t2;
		}

		public double getDifferenza() {
			return differenza;
		}

		public void setDifferenza(double differenza) {
			this.differenza = differenza;
		}

		@Override
		public int hashCode() {
			return Objects.hash(differenza, t1, t2);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Adiacenza other = (Adiacenza) obj;
			return Double.doubleToLongBits(differenza) == Double.doubleToLongBits(other.differenza)
					&& Objects.equals(t1, other.t1) && Objects.equals(t2, other.t2);
		}

		@Override
		public String toString() {
			return "[Prima canzone=" + t1 + ", Seconda canzone=" + t2 + ", differenza=" + differenza + "]";
		}
		 
}
