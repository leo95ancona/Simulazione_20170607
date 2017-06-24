package it.polito.tdp.seriea.model;

public class TeamPair {
	
	String teamH;
	String teamA;
	String ftr;
	
	public TeamPair(String teamH, String teamA, String ftr) {
		super();
		this.teamH = teamH;
		this.teamA = teamA;
		this.ftr = ftr;
	}
	public String getTeamH() {
		return teamH;
	}
	public void setTeamH(String teamH) {
		this.teamH = teamH;
	}
	public String getTeamA() {
		return teamA;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public String getFtr() {
		return ftr;
	}
	public void setFtr(String ftr) {
		this.ftr = ftr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamA == null) ? 0 : teamA.hashCode());
		result = prime * result + ((teamH == null) ? 0 : teamH.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamPair other = (TeamPair) obj;
		if (teamA == null) {
			if (other.teamA != null)
				return false;
		} else if (!teamA.equals(other.teamA))
			return false;
		if (teamH == null) {
			if (other.teamH != null)
				return false;
		} else if (!teamH.equals(other.teamH))
			return false;
		return true;
	}
	
	
	

}
