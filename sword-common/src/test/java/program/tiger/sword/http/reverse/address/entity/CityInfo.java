package program.tiger.sword.http.reverse.address.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CityInfo {

    @JsonProperty(value = "ProvinceName")
    private String provinceName;
    @JsonProperty(value = "ProvinceID")
    private String provinceId;
    @JsonProperty(value = "CityId")
    private String cityId;
    @JsonProperty(value = "CityNameEn")
    private String cityNameEn;
    @JsonProperty(value = "ProvinceNameEn")
    private String provinceNameEn;
    @JsonProperty(value = "CityName")
    private String cityName;
    @JsonProperty(value = "CitySeoNameEn")
    private String citySeoNameEn;
    @JsonProperty(value = "CityIdV4")
    private String cityIdV4;
    @JsonProperty(value = "isGAT")
    private String isGAT;

    public CityInfo() {
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getProvinceNameEn() {
        return provinceNameEn;
    }

    public void setProvinceNameEn(String provinceNameEn) {
        this.provinceNameEn = provinceNameEn;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCitySeoNameEn() {
        return citySeoNameEn;
    }

    public void setCitySeoNameEn(String citySeoNameEn) {
        this.citySeoNameEn = citySeoNameEn;
    }

    public String getCityIdV4() {
        return cityIdV4;
    }

    public void setCityIdV4(String cityIdV4) {
        this.cityIdV4 = cityIdV4;
    }

    public String getIsGAT() {
        return isGAT;
    }

    public void setIsGAT(String isGAT) {
        this.isGAT = isGAT;
    }
}
