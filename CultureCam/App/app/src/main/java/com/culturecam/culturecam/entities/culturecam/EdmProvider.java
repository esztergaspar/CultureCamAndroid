
package com.culturecam.culturecam.entities.culturecam;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdmProvider implements Serializable
{

    @SerializedName("en")
    @Expose
    private List<String> en = null;
    private final static long serialVersionUID = -5498146274454099404L;

    public List<String> getEn() {
        return en;
    }

    public void setEn(List<String> en) {
        this.en = en;
    }

}
