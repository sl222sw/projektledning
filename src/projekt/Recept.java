package projekt;

import java.util.Objects;
import java.util.*;

public class Recept implements Comparable<Recept> {

    private String bildUrl;
    private String beskrivning;
    private String namn;
    private String hemsida;
    private Integer betyg;
    private String lank;
    private Integer receptId;
    private String svgrad;

    public Recept(){}

    public Recept(String namn, int receptId) {
        this.namn = namn;
        this.receptId = receptId;
    }

    public Recept(Integer receptId){
        this.receptId = receptId;
    }

    public Recept(String bildUrl, String beskrivning, String namn, String hemsida, Integer betyg, String lank, Integer receptId, String svgrad) {
        this.bildUrl = bildUrl;
        this.beskrivning = beskrivning;
        this.namn = namn;
        this.hemsida = hemsida;
        this.betyg = betyg;
        this.lank = lank;
        this.receptId = receptId;
        this.svgrad = svgrad;
    }

    public Recept(String namn, Integer betyg, String beskrivning, String bildUrl, String lank) {
        this.bildUrl = bildUrl;
        this.beskrivning = beskrivning;
        this.namn = namn;
        this.betyg = betyg;
        this.lank = lank;
    }

    public Recept(String namn, Integer betyg){
        this.namn = namn;
        this.betyg = betyg;
    }

    public Recept(String namn) {
        this.namn = namn;
    }

    public String getBildUrl() {
        return bildUrl;
    }

    public void setBildUrl(String bildUrl) {
        this.bildUrl = bildUrl;
    }


    public String getBeskrivning() {
        return beskrivning;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }


    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }


    public String getHemsida() {
        return hemsida;
    }

    public void setHemsida(String hemsida) {
        this.hemsida = hemsida;
    }


    public Integer getBetyg() {
        return betyg;
    }

    public void setBetyg(Integer betyg) {
        this.betyg = betyg;
    }


    public String getLank() {
        return lank;
    }

    public void setLank(String lank) {
        this.lank = lank;
    }


    public Integer getReceptId() {
        return receptId;
    }

    public void setReceptId(Integer receptId) {
        this.receptId = receptId;
    }


    public String getSvgrad() {
        return svgrad;
    }

    public void setSvgrad(String svgrad) {
        this.svgrad = svgrad;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Recept recept = (Recept) o;
        return Objects.equals(receptId, recept.receptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receptId);
    }

    @Override
    public int compareTo(Recept o) {
        return  this.getReceptId().compareTo(o.getReceptId());
    }



    /*
    @Override
    public String toString() {
        return receptId;
    }*/
}
