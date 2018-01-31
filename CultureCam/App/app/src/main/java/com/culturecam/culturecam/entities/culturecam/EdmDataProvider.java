
package com.culturecam.culturecam.entities.culturecam;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdmDataProvider implements Serializable
{

    @SerializedName("def")
    @Expose
    private List<String> def = null;
    private final static long serialVersionUID = 7681010222613042963L;

    public List<String> getDef() {
        return def;
    }

    public void setDef(List<String> def) {
        this.def = def;
    }

}
