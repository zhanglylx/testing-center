package com.testing.center.entity;

import java.util.Objects;

public class ToolBox {
    private String tool_box_name;
    private Integer id;
    private String tool_box_colour;

    public String getTool_box_colour() {
        return tool_box_colour;
    }

    public void setTool_box_colour(String tool_box_colour) {
        this.tool_box_colour = tool_box_colour;
    }

    public String getTool_box_name() {
        return tool_box_name;
    }

    public void setTool_box_name(String tool_box_name) {
        this.tool_box_name = tool_box_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer tool_box_status) {
        this.id = tool_box_status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolBox toolBox = (ToolBox) o;
        return Objects.equals(tool_box_name, toolBox.tool_box_name) &&
                Objects.equals(id, toolBox.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tool_box_name, id);
    }
}
