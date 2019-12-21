package data;

public class Countries {
	
	private String countryId;
	private String countryName;
	private int regionID;
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountry_id(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getRegionID() {
		return regionID;
	}
	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}
	public Countries(String countryId) {
		this.countryId= countryId;
	}

}