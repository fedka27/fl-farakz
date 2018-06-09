package kz.terah.projectfarakzm.models;

import java.io.Serializable;

public class CarPart implements Serializable {
    private String part;
    private String carModel;
    private String carMark;

    public CarPart(String part, String carModel, String carMark) {
        this.part = part;
        this.carModel = carModel;
        this.carMark = carMark;
    }

    public String getPart() {
        return part;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarMark() {
        return carMark;
    }
}
