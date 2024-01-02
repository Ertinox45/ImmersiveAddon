package fr.dynamx.addons.immersive.common.helpers;

public class RadioFrequency {

    private float frequency;
    private String name;
    private String url;

    public RadioFrequency(float frequency, String name, String url) {
        this.frequency = frequency;
        this.name = name;
        this.url = url;
    }

    public RadioFrequency() {
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
