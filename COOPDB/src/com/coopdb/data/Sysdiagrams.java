
package com.coopdb.data;

import java.sql.Blob;


/**
 *  COOPDB.Sysdiagrams
 *  12/17/2022 17:25:49
 * 
 */
public class Sysdiagrams {

    private Integer diagramId;
    private String name;
    private Integer principalId;
    private Integer version;
    private Blob definition;

    public Integer getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(Integer diagramId) {
        this.diagramId = diagramId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Integer principalId) {
        this.principalId = principalId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Blob getDefinition() {
        return definition;
    }

    public void setDefinition(Blob definition) {
        this.definition = definition;
    }

}
