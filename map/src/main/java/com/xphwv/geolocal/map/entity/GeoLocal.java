package com.xphwv.geolocal.map.entity;


@Table(name="geolocal_error")
public class GeoLocal {
	
    @Id
	@Column(name = "id")
	private int id;
	@Column(name = "localid")
	private int localId;
	@Column(name = "localname")
	private String localName;
	@Column(name = "lonlat")
	private String lonlat;
	@Column(name = "location")
	private String location;
	@Column(name = "level")
	private int level;
	@Column(name = "type")
	private int type;
	@Column(name = "path")
	private String path;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getLonlat() {
		return lonlat;
	}

	public void setLonlat(String lonlat) {
		this.lonlat = lonlat;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "GeoLocal [id=" + id + ", localId=" + localId + ", localName=" + localName + ", level=" + level + ", type=" + type + "]";
	}

}
